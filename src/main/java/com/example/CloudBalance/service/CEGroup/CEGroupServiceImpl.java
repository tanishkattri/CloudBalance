package com.example.CloudBalance.service.CEGroup;


import com.example.CloudBalance.DTO.snowflake.CEGroupDTO;
import com.example.CloudBalance.repository.CEGroupRepository;
import com.example.CloudBalance.utils.mapper.CEGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CEGroupServiceImpl {

    @Autowired
    private CEGroupRepository ceGroupRepository;

    public List<CEGroupDTO> getAllCEGroup() {
        return ceGroupRepository.findAll()
                .stream()
                .map(CEGroupMapper::toDTO)
                .collect(Collectors.toList());
    }



}
