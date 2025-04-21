package com.example.CloudBalance.service.aws;

import com.example.CloudBalance.DTO.aws.AsgResourceDTO;
import com.example.CloudBalance.DTO.aws.Ec2ResourceDTO;
import com.example.CloudBalance.DTO.aws.RdsResourceDTO;

import java.util.List;

public interface AwsService {

    List<Ec2ResourceDTO> fetchEC2Instances(String roleArn);
    List<RdsResourceDTO> fetchRDSInstances(String roleArn);
    List<AsgResourceDTO> fetchASGDetails(String roleArn);
}
