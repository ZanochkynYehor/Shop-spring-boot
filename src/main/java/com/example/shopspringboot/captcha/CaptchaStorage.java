package com.example.shopspringboot.captcha;

import com.example.shopspringboot.domain.Captcha;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CaptchaStorage {
    private static final Map<String, Captcha> storage = new HashMap<>();
    private static final int CAPTCHA_TIMEOUT = 180;

    private CaptchaStorage() {
    }

    public static void addCaptcha(String value, HttpServletResponse resp) {
        String id = UUID.randomUUID().toString();
        storage.put(id, new Captcha(id, value, LocalDateTime.now()));
        setCaptchaId(id, resp);
    }

    private static void setCaptchaId(String id, HttpServletResponse resp) {
        Cookie cookie = new Cookie("captchaId", id);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public static String getCaptcha(String captchaId) {
        return storage.get(captchaId).getValue();
    }

    public static boolean checkCaptcha(String captchaId, String value) {
        Captcha captcha = storage.get(captchaId);
        storage.remove(captchaId);
        if (Duration.between(captcha.getCreationTime(), LocalDateTime.now()).getSeconds() <
                CAPTCHA_TIMEOUT) {
            return captcha.getValue().equals(value);
        }
        return false;
    }
}