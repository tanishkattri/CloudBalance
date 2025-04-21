package com.example.CloudBalance.DTO.aws;

import lombok.Data;

@Data
public class Ec2ResourceDTO {
    private String resourceId;
    private String resourceName;
    private String region;
    private String status;
}
