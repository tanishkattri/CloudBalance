package com.example.CloudBalance.utils;

import com.example.CloudBalance.DTO.snowflake.CostExplorerRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SqlQueryGenerator {

    public static String generateCostExplorerQuery(CostExplorerRequest request){
        StringBuilder query = new StringBuilder();

        // Validate group by field
        String groupByField = request.getGroupBy();
        if (groupByField == null || groupByField.trim().isEmpty()) {
            throw new IllegalArgumentException("GroupBy field cannot be null or empty");
        }

        // SELECT clause with daily grouping
        query.append("SELECT TO_CHAR(USAGESTARTDATE, 'YYYY-MM-DD') AS USAGE_DATE, ")
                .append(groupByField).append(", ")
                .append("SUM(LINEITEM_UNBLENDEDCOST) AS TOTAL_USAGE_COST ")
                .append("FROM COST_EXPLORER WHERE ");

        // Validate and apply date filter
        if (request.getStartDate() != null && request.getEndDate() != null) {
            query.append("USAGESTARTDATE BETWEEN TO_DATE('")
                    .append(request.getStartDate()).append("') AND TO_DATE('")
                    .append(request.getEndDate()).append("') ");
        } else {
            throw new IllegalArgumentException("Both startDate and endDate must be provided");
        }

        // Apply dynamic filters
        if (request.getFilters() != null && !request.getFilters().isEmpty()) {
            for (Map.Entry<String, List<String>> entry : request.getFilters().entrySet()) {
                String column = entry.getKey();
                List<String> values = entry.getValue();

                if (values != null && !values.isEmpty()) {
                    String inClause = values.stream()
                            .map(val -> "'" + val.replace("'", "''") + "'")
                            .collect(Collectors.joining(", "));
                    query.append("AND ").append(column).append(" IN (").append(inClause).append(") ");
                }
            }
        }

        // GROUP BY and ORDER BY clause
        query.append("GROUP BY TO_CHAR(USAGESTARTDATE, 'YYYY-MM-DD'), ")
                .append(groupByField).append(" ")
                .append("ORDER BY USAGE_DATE, TOTAL_USAGE_COST DESC");

        return query.toString();

    }
}
