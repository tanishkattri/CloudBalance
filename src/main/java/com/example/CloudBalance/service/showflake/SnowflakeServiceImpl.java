package com.example.CloudBalance.service.showflake;


import com.example.CloudBalance.DTO.snowflake.CostExplorerRequest;
import com.example.CloudBalance.repository.SnowFlakeRepository;
import com.example.CloudBalance.utils.SqlQueryGenerator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

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

    public Object getTotalCosting(@Valid CostExplorerRequest request) {
        String req = SqlQueryGenerator.generateCostExplorerQuery(request);
        return snowflakeRepository.getCostExplorerData(req);
    }
}
