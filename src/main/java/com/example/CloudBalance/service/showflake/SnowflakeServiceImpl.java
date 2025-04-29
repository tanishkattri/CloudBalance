package com.example.CloudBalance.service.showflake;


import com.example.CloudBalance.DTO.snowflake.CostExplorerRequest;
import com.example.CloudBalance.DTO.snowflake.UserCostExplorerRequest;
import com.example.CloudBalance.repository.SnowFlakeRepository;
import com.example.CloudBalance.utils.SqlQueryGenerator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SnowflakeServiceImpl {

    private final SnowFlakeRepository snowflakeRepository;

    public SnowflakeServiceImpl(SnowFlakeRepository snowflakeRepository) {
        this.snowflakeRepository = snowflakeRepository;
    }


    public List<Map<String, Object>> getData() {
        return snowflakeRepository.getData();
    }

    public Object getTotalCosting(UserCostExplorerRequest userRequest) {
        CostExplorerRequest request = new CostExplorerRequest();

        if (userRequest.getStartMonth() != null && !userRequest.getStartMonth().isEmpty()) {
            YearMonth startMonth = YearMonth.parse(userRequest.getStartMonth());
            request.setStartDate(startMonth.atDay(1));
            request.setEndDate(startMonth.atEndOfMonth());  // ✨ default endDate to same month end
        }

        if (userRequest.getEndMonth() != null && !userRequest.getEndMonth().isEmpty()) {
            YearMonth endMonth = YearMonth.parse(userRequest.getEndMonth());
            if (request.getStartDate() == null) {
                request.setStartDate(endMonth.atDay(1));  // ✨ if startDate not set, set it now
            }
            request.setEndDate(endMonth.atEndOfMonth());
        }

        request.setGroupBy(userRequest.getGroupBy());
        request.setFilters(userRequest.getFilters());
        request.setAccountNumber(userRequest.getAccountNumber());

        String sql = SqlQueryGenerator.generateCostExplorerQuery(request);
        return snowflakeRepository.getCostExplorerData(sql);
    }


    public Object getTop5AndOthersCosting(UserCostExplorerRequest userRequest) {
        CostExplorerRequest request = new CostExplorerRequest();

        if (userRequest.getStartMonth() != null && !userRequest.getStartMonth().isEmpty()) {
            YearMonth startMonth = YearMonth.parse(userRequest.getStartMonth());
            request.setStartDate(startMonth.atDay(1));
            request.setEndDate(startMonth.atEndOfMonth());
        }

        if (userRequest.getEndMonth() != null && !userRequest.getEndMonth().isEmpty()) {
            YearMonth endMonth = YearMonth.parse(userRequest.getEndMonth());
            if (request.getStartDate() == null) {
                request.setStartDate(endMonth.atDay(1));
            }
            request.setEndDate(endMonth.atEndOfMonth());
        }

        request.setGroupBy(userRequest.getGroupBy());
        request.setFilters(userRequest.getFilters());
        request.setAccountNumber(userRequest.getAccountNumber());

        String sql = SqlQueryGenerator.generateCostExplorerQuery(request);
        List<Map<String, Object>> rawData = snowflakeRepository.getCostExplorerData(sql);

        if (rawData == null || rawData.isEmpty()) {
            return rawData;
        }

        String groupByField = userRequest.getGroupBy(); // e.g., "SERVICE" or "LINKED_ACCOUNT"

        // 🔥 Step 1: Group by USAGE_MONTH
        Map<String, List<Map<String, Object>>> dataByMonth = rawData.stream()
                .collect(Collectors.groupingBy(entry -> String.valueOf(entry.get("USAGE_MONTH"))));

        List<Map<String, Object>> finalResult = new ArrayList<>();

        // 🔥 Step 2: For each month, do Top 5 + Others
        for (Map.Entry<String, List<Map<String, Object>>> monthEntry : dataByMonth.entrySet()) {
            String month = monthEntry.getKey();
            List<Map<String, Object>> entries = monthEntry.getValue();

            // Sort descending by TOTAL_USAGE_COST
            List<Map<String, Object>> sortedEntries = entries.stream()
                    .sorted((a, b) -> {
                        Double costA = ((Number) a.getOrDefault("TOTAL_USAGE_COST", 0)).doubleValue();
                        Double costB = ((Number) b.getOrDefault("TOTAL_USAGE_COST", 0)).doubleValue();
                        return Double.compare(costB, costA);
                    })
                    .toList();

            List<Map<String, Object>> top5 = sortedEntries.stream().limit(5).toList();
            List<Map<String, Object>> others = sortedEntries.stream().skip(5).toList();

            double othersTotalCost = others.stream()
                    .mapToDouble(entry -> ((Number) entry.getOrDefault("TOTAL_USAGE_COST", 0)).doubleValue())
                    .sum();

            // Add Top 5 into finalResult
            finalResult.addAll(top5);

            // Add "Others" if exists
            if (othersTotalCost > 0) {
                Map<String, Object> othersEntry = new HashMap<>();
                othersEntry.put("USAGE_MONTH", month);
                othersEntry.put(groupByField, "Others");
                othersEntry.put("TOTAL_USAGE_COST", othersTotalCost);
                finalResult.add(othersEntry);
            }
        }

        return finalResult;
    }

    public List<String> getFilterOptions(String groupByField) {
        return snowflakeRepository.fetchDistinctValues(groupByField);
    }
}
