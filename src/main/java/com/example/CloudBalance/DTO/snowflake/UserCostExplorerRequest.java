package com.example.CloudBalance.DTO.snowflake;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserCostExplorerRequest {
    private String startMonth; // format: "YYYY-MM"
    private String endMonth;
    private String groupBy;
    private Map<String, List<String>> filters;
    private String accountNumber;
}
