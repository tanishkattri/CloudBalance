package com.example.CloudBalance.model.awsBuilder;
import com.example.CloudBalance.utils.awsCredentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;

public class Ec2ClientBuilder {

    public static Ec2Client buildEc2Client(String roleArn) {
        return Ec2Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(AwsCredentials.createCredentials(roleArn))
                .build();
    }
}
