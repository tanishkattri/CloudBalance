package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.aws.AsgResourceDTO;
import com.example.CloudBalance.DTO.aws.Ec2ResourceDTO;
import com.example.CloudBalance.DTO.aws.RdsResourceDTO;
import com.example.CloudBalance.service.aws.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/aws")
public class AWSController {

    @Autowired
    private AwsService awsService;

    @GetMapping("/ec2")
    public ResponseEntity<List<Ec2ResourceDTO>> getEC2Instances(
            @RequestParam Long accountNumber) {
        var result = awsService.fetchEC2Instances(accountNumber);
        log.info(result.toString());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rds")
    public ResponseEntity<List<RdsResourceDTO>> getRDSInstances(
            @RequestParam Long accountNumber) {
        var result = awsService.fetchRDSInstances(accountNumber);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/asg")
    public ResponseEntity<List<AsgResourceDTO>> getASGInstances(
            @RequestParam Long accountNumber) {
        var result = awsService.fetchASGDetails(accountNumber);
        return ResponseEntity.ok(result);
    }
}
