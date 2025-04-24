package com.example.CloudBalance.controller;

import com.example.CloudBalance.DTO.ApiResponse;
import com.example.CloudBalance.DTO.snowflake.UserCostExplorerRequest;
import com.example.CloudBalance.service.showflake.SnowflakeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snowflake")
public class SnowFlakeController {

    @Autowired
    private SnowflakeServiceImpl snowflakeService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<?>> getData(){
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", snowflakeService.getData()));
    }

    @PostMapping("/cost")
    public ResponseEntity<ApiResponse<?>> getTotalCosting(@Valid @RequestBody UserCostExplorerRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", snowflakeService.getTotalCosting(request))
        );
    }
}
