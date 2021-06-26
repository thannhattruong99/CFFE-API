package com.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class GCPHelper {
//    upload image
    private static String bucketName = "capstone_storeage";

    public static String uploadFile(String relativeFilePath, String fileCloudPath) throws IOException {
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

}
