package com.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GCPHelper {

    private static final String BUCKET_NAME = "capstone_storeage";

    public static String uploadFile(String relativeFilePath, String fileCloudPath, String contentType) throws IOException, StorageException {
        Bucket bucket  = getBucket(BUCKET_NAME);
        InputStream inputStream = new FileInputStream(FileHelper.getResourcePath() + relativeFilePath);
        Blob blob = null;
        try {
            blob = bucket.create(fileCloudPath, inputStream, contentType);
        } catch (StorageException e) {
            throw e;
        }
        return blob.getMediaLink();
    }
    private static Bucket getBucket(String bucketName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new FileInputStream(ResourceUtils.getFile(FileHelper.getResourcePath() + "capstone-project-sm21-78b453757e26.json")))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException("Bucket not found:" + bucketName);
        }
        return bucket;
    }

}
