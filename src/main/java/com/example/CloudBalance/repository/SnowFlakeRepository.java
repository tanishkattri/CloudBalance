package com.example.CloudBalance.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SnowFlakeRepository {
    private final JdbcTemplate snowFlakeJdbcTemplate;

    public SnowFlakeRepository(@Qualifier("snowflakeJdbcTemplate") JdbcTemplate snowflakeJdbcTemplate) {
        this.snowFlakeJdbcTemplate = snowflakeJdbcTemplate;
    }


    public List<Map<String, Object>> getData() {
        String sql = "SELECT * FROM cost_explorer LIMIT 1000";
        return snowFlakeJdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> getCostExplorerData(String req) {
        return snowFlakeJdbcTemplate.queryForList(req);
    }

    public List<String> fetchDistinctValues(String groupByField) {
        String sql = String.format(
                "SELECT DISTINCT %s FROM COST_EXPLORER WHERE %s IS NOT NULL ORDER BY %s",
                groupByField, groupByField, groupByField
        );

        return snowFlakeJdbcTemplate.queryForList(sql, String.class);
    }
}
