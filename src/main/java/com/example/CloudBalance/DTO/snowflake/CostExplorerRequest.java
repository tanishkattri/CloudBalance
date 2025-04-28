package com.example.CloudBalance.DTO.snowflake;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class CostExplorerRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String groupBy;
    private Map<String, List<String>> filters;
    private String accountNumber;
}
