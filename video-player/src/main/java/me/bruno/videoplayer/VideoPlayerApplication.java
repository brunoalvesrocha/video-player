package me.bruno.videoplayer;

import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VideoPlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoPlayerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AwsS3Operations s3) {
        return args -> {
            s3.listFoldersFromBucketName("ftcourse")
            .stream()
            .forEach(System.out::println);
        };
    }
}