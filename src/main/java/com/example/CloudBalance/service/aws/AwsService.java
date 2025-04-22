package com.example.CloudBalance.service.aws;

import com.example.CloudBalance.DTO.aws.AsgResourceDTO;
import com.example.CloudBalance.DTO.aws.Ec2ResourceDTO;
import com.example.CloudBalance.DTO.aws.RdsResourceDTO;

import java.util.List;

public interface AwsService {

    List<Ec2ResourceDTO> fetchEC2Instances(Long accountNumber);
    List<RdsResourceDTO> fetchRDSInstances(Long accountNumber);
    List<AsgResourceDTO> fetchASGDetails(Long accountNumber);
}
