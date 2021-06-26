package com.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GCPHelper {
//    upload image
    private static String bucketName = "capstone_storeage";


    public static void uploadObject() {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        try {
            File file =  ResourceUtils.getFile(FileHelper.getResourcePath() + filePath);
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

    public static String uploadFile(String relativeFilePath, String fileCloudPath, String contentType) throws IOException {
        Bucket bucket  = getBucket(bucketName);
        InputStream inputStream = new FileInputStream(FileHelper.getResourcePath() + relativeFilePath);
        Blob blob = bucket.create(fileCloudPath, inputStream, contentType);
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

}
