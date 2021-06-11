package com.util;


import com.batches.events.EventPublisher;
import com.common.dto.DocumnentStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageHelper {
//    private final Path fileStorageLocation;

//    @Autowired
//    DocumentStoragePropertiesRepo docStorageRepo;

//    @Autowired
//    public DocumentStorageService(DocumnentStorageProperties fileStorageProperties) {
//        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                .toAbsolutePath().normalize();
//        try {
//            Files.createDirectories(fileStorageLocation);
//        } catch (Exception ex) {
//            System.out.println("Could not create the directory where the uploaded files will be stored.");
//        }
//    }

    public String storeFile(MultipartFile file, Integer userId, String docType) {
        System.out.println("Toi day roi neeeeeeeeeeeeeeeeee");
//        Path fileStorageLocation = Paths.get("\\src\\main\\resources\\messages");

        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        System.out.println("========================== "+userDirectory);

        Path fileStorageLocation = Paths.get(userDirectory+ "\\src\\main\\resources\\files");
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
                fileExtension = "ahihih do nngoc";
            }
            String fileNameZero;
            try {
                fileNameZero = originalFileName.substring(0,originalFileName.lastIndexOf(".")-1);
            } catch(Exception e) {
                fileNameZero = "ahihih do nngoc";
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

//    public Resource loadFileAsResource(String fileName) throws Exception {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new FileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new FileNotFoundException("File not found " + fileName);
//        }
//    }

    public String getDocumentName(Integer userId, String docType) {
        return "test";
//        return docStorageRepo.getUploadDocumnetPath(userId, docType);
    }

}
