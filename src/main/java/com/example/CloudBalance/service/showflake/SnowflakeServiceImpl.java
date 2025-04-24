package com.example.CloudBalance.service.showflake;


import com.example.CloudBalance.DTO.snowflake.CostExplorerRequest;
import com.example.CloudBalance.DTO.snowflake.UserCostExplorerRequest;
import com.example.CloudBalance.repository.SnowFlakeRepository;
import com.example.CloudBalance.utils.SqlQueryGenerator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

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
        // Convert month strings to LocalDate range
        YearMonth startMonth = YearMonth.parse(userRequest.getStartMonth());
        YearMonth endMonth = YearMonth.parse(userRequest.getEndMonth());

        // Create internal request
        CostExplorerRequest request = new CostExplorerRequest();
        request.setStartDate(startMonth.atDay(1)); // e.g., 2024-11-01
        request.setEndDate(endMonth.atEndOfMonth()); // e.g., 2025-04-30
        request.setGroupBy(userRequest.getGroupBy());
        request.setFilters(userRequest.getFilters());

        // Generate SQL and return results
        String sql = SqlQueryGenerator.generateCostExplorerQuery(request);
        return snowflakeRepository.getCostExplorerData(sql);
    }
}
