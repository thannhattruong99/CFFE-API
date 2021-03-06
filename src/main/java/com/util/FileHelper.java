package com.util;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.util.PathConstant.*;

public class FileHelper implements Serializable {

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

    /**
     * Get image user upload to store on server.
     * @param file image user upload
     * @param suffixPath path on server
     * @return fileName of image on server
     */
    public static String storeFileOnServer(MultipartFile file, String suffixPath) throws Exception{
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
            // System.out.println("Could not create the directory where the uploaded files will be stored.");
            throw ex;
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
            // System.out.println("Could not store file. Please try again!"+ ex);
            throw ex;
        }
        return fileName;
    }

    public static List<String> loadResource(String path){
        List<String> result = new ArrayList<>();
        File file = null;
        Scanner myReader = null;
        try {
            file = ResourceUtils.getFile(FileHelper.getResourcePath() + path);
            myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                result.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(myReader != null){
                myReader.close();
            }
        }
        return result;
    }

    public static List<String> getResourceList(String path) {
        List<String> list = new ArrayList<>();
        ResourceBundle rb = ResourceBundle.getBundle(path);
        Enumeration<String> key = rb.getKeys();
        while (key.hasMoreElements()) {
            list.add(key.nextElement());
        }
        return list;
    }

    //get out of project path
    public static String getOutProjectPath(){
        String str = Paths.get("").toAbsolutePath().toString();
        if(str.contains(PROJECT_SERVER_PATH)){
            str = str.replaceAll(PROJECT_SERVER_PATH, "");
        }else{
            str = str.replaceAll(PROJECT_LOCAL_PATH, "");
        }
        return str;
    }
}
