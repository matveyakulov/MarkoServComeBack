package ru.matveyakulov.markoservcomeback.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CloudService {

    private static final String accessStaticKeyId = "YCAJEWu4rdSQiRA4krxNmRih0";
    private static final String accessStaticKey = "YCO4nl1Nqp4XoqUIsumxrN9PQPHp20GsdeoEzu3h";

    private static final String bucketName = "holidaybucket";
    private static final String fileName = "holidays";

    public static void uploadFile(String path) {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessStaticKeyId, accessStaticKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(
                            new AmazonS3ClientBuilder.EndpointConfiguration(
                                    "storage.yandexcloud.net", "ru-central1"
                            )
                    )
                    .build();

            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, new File(path));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public static void getFile() {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessStaticKeyId, accessStaticKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(
                            new AmazonS3ClientBuilder.EndpointConfiguration(
                                    "storage.yandexcloud.net", "ru-central1"
                            )
                    )
                    .build();

            GetObjectRequest request = new GetObjectRequest(bucketName, fileName);

            S3Object o = s3Client.getObject(request);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream("holidaysFromCloud.xlsx");
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
