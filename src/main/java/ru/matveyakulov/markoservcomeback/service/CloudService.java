package ru.matveyakulov.markoservcomeback.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import ru.matveyakulov.markoservcomeback.factory.AmazonClientFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CloudService {

    private static final String bucketName = "holidaybucket";
    private static final String fileName = "holidays";

    public static void uploadFile(String path) {
        try {
            AmazonS3 s3Client = AmazonClientFactory.getClient();
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
            AmazonS3 s3Client = AmazonClientFactory.getClient();

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
