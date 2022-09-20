package ru.matveyakulov.markoservcomeback;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.matveyakulov.markoservcomeback.factory.DriverFactory;
import ru.matveyakulov.markoservcomeback.parser.HolidayParser;

import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MarkoServComeBackApplication extends SpringBootServletInitializer {

    private static String accessStaticKeyId = "YCAJED_deFyws_MMGvjdvnaaL";
    private static String accessStaticKey = "YCPjn7stkzBSnOfN9StDJRIjl-5lb6GEQ-h1TqHD";

    public static void main(String[] args) {
//        try {
//            AWSCredentials credentials = new BasicAWSCredentials(accessStaticKeyId, accessStaticKey);
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                    .withEndpointConfiguration(
//                            new AmazonS3ClientBuilder.EndpointConfiguration(
//                                    "storage.yandexcloud.net", "ru-central1"
//                            )
//                    )
//                    .build();
//
//            String bucketName = "fallsd";
//
//            // Upload a file as a new object with ContentType and title specified.
//            PutObjectRequest request = new PutObjectRequest(bucketName, "audio" + Instant.now(),
//                    new File("D:\\work\\sip\\src\\main\\resources\\mp33.mp3"));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("audio/mp3");
//            request.setMetadata(metadata);
//            s3Client.putObject(request);
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        }
        //SpringApplication.run(MarkoServComeBackApplication.class, args);

        List<String> mouths = List.of("sentyabr");
        List<Integer> days = List.of(20, 21);
        for (Integer day : days) {
            List<String> holidaysByMouthAndDay = HolidayParser.getHolidaysByMouthAndDay(mouths.get(0), day);
            System.out.println();
            System.out.println();
        }

    }

}
