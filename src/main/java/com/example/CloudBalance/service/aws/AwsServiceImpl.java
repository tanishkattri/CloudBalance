package com.example.CloudBalance.service.aws;

import com.example.CloudBalance.DTO.aws.AsgResourceDTO;
import com.example.CloudBalance.DTO.aws.Ec2ResourceDTO;
import com.example.CloudBalance.DTO.aws.RdsResourceDTO;
import com.example.CloudBalance.exception.AWSServiceException;
import com.example.CloudBalance.model.Account;
import com.example.CloudBalance.model.awsBuilder.AsgClientBuilder;
import com.example.CloudBalance.model.awsBuilder.Ec2ClientBuilder;
import com.example.CloudBalance.model.awsBuilder.RdsClientBuilder;
import com.example.CloudBalance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.autoscaling.model.AutoScalingGroup;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.rds.model.DBInstance;

import java.util.ArrayList;
import java.util.List;


@Service
public class AwsServiceImpl implements AwsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Ec2ResourceDTO> fetchEC2Instances(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        try{
            var ec2Client = Ec2ClientBuilder.buildEc2Client(account.getArnNumber());
            var response = ec2Client.describeInstances();
            List<Ec2ResourceDTO> result = new ArrayList<>();
            for (Reservation reservation : response.reservations()) {
                reservation.instances().forEach(instance -> {
                    Ec2ResourceDTO dto = new Ec2ResourceDTO();
                    dto.setResourceId(instance.instanceId());
                    dto.setResourceName(instance.tags().stream()
                            .filter(t -> t.key().equalsIgnoreCase("Name"))
                            .findFirst()
                            .map(Tag::value)
                            .orElse("N/A"));
                    dto.setRegion(instance.placement().availabilityZone());
                    dto.setStatus(instance.state().nameAsString());
                    result.add(dto);
                });
            }
            return result;
        }catch (SdkException sdkEx) {
            throw new AWSServiceException("Failed to fetch EC2 instances: AWS Service Error", sdkEx);
        } catch (Exception ex) {
            throw new AWSServiceException("Unexpected error occurred while fetching EC2 instances.", ex);
        }
    }

    @Override
    public List<RdsResourceDTO> fetchRDSInstances(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        try{
            var rdsClient = RdsClientBuilder.buildRdsClient(account.getArnNumber());
            var response = rdsClient.describeDBInstances();
            List<RdsResourceDTO> result = new ArrayList<>();
            for(DBInstance dbInstance : response.dbInstances()) {
                RdsResourceDTO dto = new RdsResourceDTO();
                dto.setResourceId(dbInstance.dbInstanceArn());
                dto.setResourceName(dbInstance.dbInstanceIdentifier());
                dto.setRegion(dbInstance.availabilityZone());
                dto.setEngine(dbInstance.engine());
                dto.setStatus(dbInstance.dbInstanceStatus());
                result.add(dto);
            }
            return result;
        }catch (SdkException sdkEx) {
            throw new AWSServiceException("Failed to fetch RDS instances: AWS Service Error", sdkEx);
        } catch (Exception ex) {
            throw new AWSServiceException("Unexpected error occurred while fetching RDS instances.", ex);
        }
    }



    @Override
    public List<AsgResourceDTO> fetchASGDetails(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        try{
            var asgClient = AsgClientBuilder.buildAsgClient(account.getArnNumber());
            var response = asgClient.describeAutoScalingGroups();
            List<AsgResourceDTO> result = new ArrayList<>();
            for(AutoScalingGroup asg : response.autoScalingGroups()) {
                AsgResourceDTO dto = new AsgResourceDTO();
                dto.setResourceId(asg.autoScalingGroupARN());
                dto.setResourceName(asg.autoScalingGroupName());
                dto.setRegion(asg.availabilityZones().get(0));
                dto.setDesiredCapacity(asg.desiredCapacity());
                dto.setMinSize(asg.minSize());
                dto.setMaxSize(asg.maxSize());
                dto.setStatus(asg.status());
                result.add(dto);
            }
            return result;
        }catch (SdkException sdkEx) {
            throw new AWSServiceException("Failed to fetch ASG instances: AWS Service Error", sdkEx);
        } catch (Exception ex) {
            throw new AWSServiceException("Unexpected error occurred while fetching ASG instances.", ex);
        }
    }
}