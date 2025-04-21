package com.example.CloudBalance.model.awsBuilder;

import com.example.CloudBalance.utils.awsCredentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;

public class AsgClientBuilder {

    public static AutoScalingClient buildAsgClient(String roleArn) {
        return AutoScalingClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(AwsCredentials.createCredentials(roleArn))
                .build();
    }
}
