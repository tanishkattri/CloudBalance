package com.example.CloudBalance.model.awsBuilder;

import com.example.CloudBalance.utils.awsCredentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;

public class RdsClientBuilder {

    public static RdsClient buildRdsClient(String roleArn) {
        return RdsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(AwsCredentials.createCredentials(roleArn))
                .build();
    }
}
