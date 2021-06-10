package com.util;

import org.springframework.core.io.ClassPathResource;
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
    private final static String RESOURCE_PATH = "/src/main/resources/";

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

    public static void deleteFile(String relativeFilePath) throws IOException {
        File file = new File(getResourcePath() + relativeFilePath);
        file.delete();
    }

    public static boolean checkExistFile(String relativeFilePath) throws FileNotFoundException {
        return ResourceUtils.getFile(getResourcePath() + relativeFilePath).exists();
    }

    public static String getResourcePath(){
        return Paths.get("").toAbsolutePath().toString() + RESOURCE_PATH;
    }
}
