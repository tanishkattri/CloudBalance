package com.example.CloudBalance.DTO.aws;

import lombok.Data;

@Data
public class RdsResourceDTO {
    private String resourceId;
    private String resourceName;
    private String engine;
    private String region;
    private String status;
}
