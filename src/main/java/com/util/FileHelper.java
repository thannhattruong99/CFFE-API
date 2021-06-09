package com.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelper {
    private final static String CLASS_PATH = "classpath:";

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile)
    throws  IOException{
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static void deleteFile(String relativeFilePath) throws FileNotFoundException {
//        File file = ResourceUtils.getFile(CLASS_PATH + relativeFilePath);
        File file = ResourceUtils.getFile("/Users/truongtn/Desktop/Desktop/HocTap/Capstone/CapstoneAPI/src/main/resources/videos/example_01.avi");
        file.delete();
        file = ResourceUtils.getFile("/Users/truongtn/Desktop/Desktop/HocTap/Capstone/CapstoneAPI/src/main/resources/videos/example_01.mp4");
        file.delete();
    }

    public static boolean checkExistFile(String relativeFilePath) throws FileNotFoundException {
        return ResourceUtils.getFile(CLASS_PATH + relativeFilePath).exists();
    }
}
