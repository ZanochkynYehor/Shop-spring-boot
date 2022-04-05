package com.example.shopspringboot.controller;

import com.example.shopspringboot.bean.SignUpFormBean;
import com.example.shopspringboot.captcha.CaptchaGenerator;
import com.example.shopspringboot.captcha.CaptchaStorage;
import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.service.UserService;
import com.example.shopspringboot.util.AvatarUtil;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private static final String SIGN_UP_BEAN = "signUpBean";

    private final UserService userService;
    private final LocaleResolver localeResolver;
    private final MessageSource messageSource;

    public SignUpController(UserService userService, LocaleResolver localeResolver,
                            MessageSource messageSource) {
        this.userService = userService;
        this.localeResolver = localeResolver;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getSignUpPage(Model model, HttpServletResponse resp) {
        CaptchaGenerator.generateCaptcha(resp);
        if (!model.containsAttribute(SIGN_UP_BEAN)) {
            model.addAttribute(SIGN_UP_BEAN, new SignUpFormBean());
        }
        return "signup";
    }

    @PostMapping
    public String signUpUser(@ModelAttribute(SIGN_UP_BEAN) @Valid SignUpFormBean bean,
                             BindingResult bindingResult, RedirectAttributes attr,
                             HttpServletRequest req,
                             @CookieValue(value = "captchaId") String captchaId) {
        Locale locale = localeResolver.resolveLocale(req);

        if (!CaptchaStorage.checkCaptcha(captchaId, bean.getCaptcha())) {
            bindingResult.addError(new FieldError("captcha", "captcha",
                    messageSource.getMessage("signup.error.captcha", null, locale)));
        }
        if (!bindingResult.hasErrors()) {
            try {
                User user = userService.createUser(bean.getUser());
                AvatarUtil.saveAvatar(bean.getAvatar(), user.getId());
            } catch (DataAccessException e) {
                bindingResult.addError(new FieldError("email", "email",
                        messageSource.getMessage("signup.error.existing_email", null, locale)));
            }
        }

        bean.setPassword(null);
        bean.setConfirmPassword(null);

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.signUpBean",
                    bindingResult);
            attr.addFlashAttribute(SIGN_UP_BEAN, bean);
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/captcha")
    public void getCaptcha(@CookieValue(value = "captchaId") String captchaId,
                           HttpServletResponse resp) throws IOException {
        resp.setContentType("image/png");
        ImageIO.write(CaptchaGenerator.createCaptchaImage(CaptchaStorage.getCaptcha(captchaId)),
                "png", resp.getOutputStream());
    }
}