package com.example.shopspringboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AvatarUtil {
    private static final String TARGET_AVATARS_DIR = "target/classes/static/img/avatars/";
    private static final String AVATARS_DIR_SAVE = "src/main/resources/static/img/avatars/";
    private static final String AVATARS_DIR_GET = "/img/avatars/";
    private static final String DEFAULT_AVATAR = "default.png";
    private static final Logger log = LoggerFactory.getLogger(AvatarUtil.class);

    private AvatarUtil() {
    }

    public static void saveAvatar(MultipartFile avatar, int userId) {
        if (avatar.getSize() == 0) {
            return;
        }

        String fileName = avatar.getOriginalFilename();
        String avatarName = userId + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            Files.write(Path.of(AVATARS_DIR_SAVE + avatarName), avatar.getBytes());
            Files.write(Path.of(TARGET_AVATARS_DIR + avatarName), avatar.getBytes());
        } catch (IOException e) {
            log.error("Save avatar fail", e);
        }
    }

    public static String getAvatarPath(int userId) {
        File dir = new File(AVATARS_DIR_SAVE);
        File[] files = dir.listFiles((dir1, name) -> name.startsWith(String.valueOf(userId)));
        if (files.length != 0) {
            return AVATARS_DIR_GET + files[0].getName();
        }
        return AVATARS_DIR_GET + DEFAULT_AVATAR;
    }
}