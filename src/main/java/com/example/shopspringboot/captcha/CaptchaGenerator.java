package com.example.shopspringboot.captcha;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Component
public class CaptchaGenerator {

    private static final int WIDTH = 150;
    private static final int HEIGHT = 50;
    private static final int FONT_SIZE = 24;

    private static final Random random = new Random();
    private static final Character[] CAPTCHA_SYMBOLS =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                    'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                    'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int CAPTCHA_LENGTH = 6;

    private CaptchaGenerator() {
    }

    public static void generateCaptcha(HttpServletResponse resp) {
        String value = generateValue();
        CaptchaStorage.addCaptcha(value, resp);
    }

    private static String generateValue() {
        StringBuilder captchaBuffer = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(62);
            captchaBuffer.append(CAPTCHA_SYMBOLS[index]);
        }
        return captchaBuffer.toString();
    }

    public static BufferedImage createCaptchaImage(String value) {
        BufferedImage captcha = new BufferedImage(WIDTH, HEIGHT, BufferedImage.OPAQUE);
        Graphics graphics = captcha.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        graphics.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        int textWidth = graphics.getFontMetrics().stringWidth(value);
        int textHeight = graphics.getFontMetrics().getHeight();
        int x = (WIDTH - textWidth) / 2;
        int y = HEIGHT - textHeight + 10;
        graphics.drawString(value, x, y);
        return captcha;
    }
}