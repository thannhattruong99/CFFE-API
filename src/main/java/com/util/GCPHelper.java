package com.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class GCPHelper {
    private static String projectId = "capstone-project-sm21\n";
    private static String bucketName = "capstone_storeage";
    private static String objectName = "aaa.txt";
    private static String filePath = "messages/3_text.txt";

    public static void uploadObject() {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        try {
            File file =  ResourceUtils.getFile("classpath:" + filePath);
            storage.create(blobInfo, Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }

    public static void uploadImage() throws IOException {
        Bucket bucket = getBucket(bucketName);
        InputStream inputStream = new FileInputStream(new File("/Users/truongtn/Desktop/Desktop/HocTap/Capstone/CapstoneAPI/src/main/resources/messages/3_text.txt"));
        Blob blob = bucket.create("avt_images/test", inputStream, "Text file");
        System.out.println("blob.getMediaLink(): " + blob.getMediaLink());
    }

    private static Bucket getBucket(String bucketName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/truongtn/Desktop/Desktop/HocTap/Capstone/CapstoneAPI/src/main/resources/capstone-project-sm21-78b453757e26.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException("Bucket not found:"+bucketName);
        }
        return bucket;
    }
}
