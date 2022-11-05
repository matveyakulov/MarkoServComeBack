package ru.matveyakulov.markoservcomeback.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.checkerframework.common.value.qual.BottomVal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.matveyakulov.markoservcomeback.factory.AmazonClientFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudService {

    private final String bucketName = "holidaybucket";
    private final String fileName = "holidays";

    @Value("${yandex.speechkit.apikey}")
    private String apikey;

    public void uploadFile(String path) {
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

    public void getFile() {
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

    public void getSoundAnswer(String value) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Api-key " + apikey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> vars = new HashMap<>();
        vars.put("text", value);

        ResponseEntity<byte[]> bytes = restTemplate.exchange(
          "https://tts.api.cloud.yandex.net/speech/v1/tts:synthesize?text={text}&format=mp3",
          HttpMethod.GET,
          entity,
          byte[].class,
          vars);
        File outputFile = Paths.get("D:\\VisualStudio\\SliderWitchJson\\answerSound.mp3").toFile();
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(Objects.requireNonNull(bytes.getBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
