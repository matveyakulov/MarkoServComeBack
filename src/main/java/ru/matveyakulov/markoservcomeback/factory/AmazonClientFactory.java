package ru.matveyakulov.markoservcomeback.factory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonClientFactory {

    private static final String accessStaticKeyId = "YCAJEWu4rdSQiRA4krxNmRih0";
    private static final String accessStaticKey = "YCO4nl1Nqp4XoqUIsumxrN9PQPHp20GsdeoEzu3h";

    public static AmazonS3 getClient(){
        AWSCredentials credentials = new BasicAWSCredentials(accessStaticKeyId, accessStaticKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net", "ru-central1"
                        )
                )
                .build();
    }
}
