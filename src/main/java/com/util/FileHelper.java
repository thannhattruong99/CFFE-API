package com.util;

import com.common.dto.DocumnentStorageProperties;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    public static String storeVideo(MultipartFile file, Integer userId, String docType) {
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();

        Path fileStorageLocation = Paths.get(userDirectory+ "/src/main/resources/videos/input");
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            System.out.println("Could not create the directory where the uploaded files will be stored.");
        }
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";
        try {
            // Check if the file's name contains invalid characters
            if(originalFileName.contains("..")) {
                System.out.println("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch(Exception e) {
                System.out.println("Error at DocumentStorageHelper: " + e.getMessage());
            }
            String fileNameZero = "";
            try {
                fileNameZero = originalFileName.substring(0,originalFileName.lastIndexOf(".")-1);
            } catch(Exception e) {
                System.out.println("Error at DocumentStorageHelper: " + e.getMessage());
            }
            fileName = userId +"_" +  fileNameZero+"_" + docType + fileExtension;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

//            DocumnentStorageProperties doc = docStorageRepo.checkDocumentByUserId(userId, docType);
            DocumnentStorageProperties doc = new DocumnentStorageProperties();
            if(doc != null) {
                doc.setDocumentFormat(file.getContentType());
                doc.setFileName(fileName);
//                docStorageRepo.save(doc);

            } else {
                DocumnentStorageProperties newDoc = new DocumnentStorageProperties();
                newDoc.setUserId(userId);
                newDoc.setDocumentFormat(file.getContentType());
                newDoc.setFileName(fileName);
                newDoc.setDocumentType(docType);
//                docStorageRepo.save(newDoc);
            }
            return fileName;
        } catch (IOException ex) {
            System.out.println("Could not store file " + fileName + ". Please try again!"+ ex);
        }
        return fileName;
    }

    /**
     * Get image user upload to store on server.
     * @param file image user upload
     * @param suffixPath path on server
     * @return fileName of image on server
     */
    public static String storeImageOnServer(MultipartFile file, String suffixPath) {
        String fileName = "";
        // Get full path directory on server
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        Path fileStorageLocation = Paths.get(userDirectory + suffixPath);

        // create Directories
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            System.out.println("Could not create the directory where the uploaded files will be stored.");
            return fileName;
        }

        // change file name by UUID
        // Copy file to the target location (Replacing existing file with the same name)
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
            LocalDateTime timeNow = LocalDateTime.now();
            fileName = UUID.randomUUID().toString() + '-' + dtf.format(timeNow) + fileExtension;
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println("Could not store file. Please try again!"+ ex);
        }
        return fileName;
    }
}
