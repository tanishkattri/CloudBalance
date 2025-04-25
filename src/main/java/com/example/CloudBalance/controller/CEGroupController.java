package com.example.CloudBalance.controller;


import com.example.CloudBalance.DTO.ApiResponse;
import com.example.CloudBalance.DTO.snowflake.CEGroupDTO;
import com.example.CloudBalance.service.CEGroup.CEGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/snowflake")
public class CEGroupController {

    @Autowired
    private CEGroupServiceImpl ceGroupService;

    @GetMapping("/group-by-options")
    public ResponseEntity<ApiResponse<List<CEGroupDTO>>> getAllGroupByOptions() {
        List<CEGroupDTO> groups = ceGroupService.getAllCEGroup();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", groups));
    }
}
