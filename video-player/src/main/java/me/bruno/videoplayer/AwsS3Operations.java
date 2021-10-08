package me.bruno.videoplayer;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwsS3Operations {

    @Autowired
    AwsClientService client;

    public List<String> listBuckets() {
        return client.getClient()
                .listBuckets()
                .stream()
                .map(bucket -> bucket.getName())
                .collect(Collectors.toList());
    }

    public List<String> listFoldersFromBucketName(String bucketName) {
        boolean isTopLevel = false;
        String delimiter = "/";
        String prefix = "/";
        if (prefix.equals("") || prefix.equals(delimiter)) {
            isTopLevel = true;
        }
        if (!prefix.endsWith(delimiter)) {
            prefix += delimiter;
        }

        ListObjectsRequest listObjectsRequest = null;
        if (isTopLevel) {
            listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withDelimiter(delimiter);
        } else {
            listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(prefix)
                    .withDelimiter(delimiter);
        }

        ObjectListing objects = client.getClient()
                .listObjects(listObjectsRequest);
        return objects.getCommonPrefixes();
    }
}

