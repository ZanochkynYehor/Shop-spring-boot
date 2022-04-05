package com.example.shopspringboot.util;

import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;

    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
                                        Authentication auth) throws IOException, ServletException {
        User user = (User) auth.getPrincipal();
        if (user.getFailedSignInCount() > 0) {
            userService.unbanUser(user);
        }

        HttpSession session = req.getSession();
        session.setAttribute("userAvatar", AvatarUtil.getAvatarPath(user.getId()));

        if (session.getAttribute("SPRING_SECURITY_SAVED_REQUEST") == null) {
            setDefaultTargetUrl("/products");
        }

        super.onAuthenticationSuccess(req, resp, auth);
    }
}