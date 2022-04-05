package com.example.shopspringboot.util;

import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final UserService userService;
    private final LocaleResolver localeResolver;
    private final MessageSource messageSource;
    private final int maxFailedSignInCount;

    public LoginFailureHandler(UserService userService,
                               LocaleResolver localeResolver,
                               MessageSource messageSource,
                               @Value("${max_failed_sign_in_count}") int maxFailedSignInCount) {
        this.userService = userService;
        this.localeResolver = localeResolver;
        this.messageSource = messageSource;
        this.maxFailedSignInCount = maxFailedSignInCount;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        Locale locale = localeResolver.resolveLocale(req);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.getUser(email);
        if (user == null || !UserUtil.isPasswordValid(user, password)) {
            exception = new BadCredentialsException(messageSource.getMessage("login.error.incorrect_email_or_password", null, locale));
        }

        if (user != null) {
            if (UserUtil.isBanned(user)) {
                exception = new LockedException(
                        messageSource.getMessage("login.error.account_banned", null, locale) + ' ' +
                                UserUtil.banExpiresIn(user));
            } else {
                userService.increaseSignInCount(user);
                if (user.getFailedSignInCount() >= maxFailedSignInCount) {
                    userService.banUser(user);
                    exception = new LockedException(
                            messageSource.getMessage("login.error.account_banned", null, locale) + ' ' +
                                    UserUtil.banExpiresIn(user));
                }
            }
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(req, resp, exception);
    }
}