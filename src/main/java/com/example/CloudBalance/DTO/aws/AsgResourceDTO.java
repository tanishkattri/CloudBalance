package com.example.CloudBalance.DTO.aws;

import lombok.Data;

@Data
public class AsgResourceDTO {
    private String resourceId;
    private String resourceName;
    private String region;
    private Integer desiredCapacity;
    private Integer minSize;
    private Integer maxSize;
    private String status;
}
