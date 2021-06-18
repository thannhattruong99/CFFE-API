package com.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class GCPHelper {
//    upload image
    private static String projectId = "capstone-project-sm21\n";
    private static String bucketName = "capstone_storeage";
    private static String objectName = "aaa.txt";
    private static String filePath = "messages/3_text.txt";

//    Send notification
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + projectId + "/messages:send";
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = { MESSAGING_SCOPE };


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

    public static String uploadImage(String relativeFilePath) throws IOException {
        Bucket bucket  = getBucket(bucketName);

        InputStream inputStream = new FileInputStream(FileHelper.getResourcePath() + relativeFilePath);

        Blob blob = bucket.create("detected_video/test.mp4", inputStream, "video/mp4");
        System.out.println("blob.getMediaLink(): " + blob.getMediaLink());
        return blob.getSelfLink();
    }

    public static String uploadImage(String relativeFilePath, String fileCloudPath) throws IOException {
        Bucket bucket  = getBucket(bucketName);
        InputStream inputStream = new FileInputStream(FileHelper.getResourcePath() + relativeFilePath);
        Blob blob = bucket.create(fileCloudPath, inputStream, "");
        return blob.getMediaLink();
    }

    private static Bucket getBucket(String bucketName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new FileInputStream(ResourceUtils.getFile(FileHelper.getResourcePath() +"capstone-project-sm21-78b453757e26.json")))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException("Bucket not found:" + bucketName);
        }
        return bucket;
    }

    private static String getAccessToken() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("service-account.json"))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    private static HttpURLConnection getConnection() throws IOException {
        // [START use_access_token]
        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        return httpURLConnection;
        // [END use_access_token]
    }

}
