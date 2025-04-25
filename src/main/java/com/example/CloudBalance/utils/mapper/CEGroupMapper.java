package com.example.CloudBalance.utils.mapper;

import com.example.CloudBalance.DTO.snowflake.CEGroupDTO;
import com.example.CloudBalance.model.CEGroups;

public class CEGroupMapper {
    public static CEGroupDTO toDTO(CEGroups group) {
        CEGroupDTO dto = new CEGroupDTO();
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        dto.setDisplayName(group.getDisplayName());
        return dto;
    }

    public static CEGroups toEntity(CEGroupDTO dto) {
        CEGroups group = new CEGroups();
        group.setId(dto.getId());
        group.setGroupName(dto.getGroupName());
        group.setDisplayName(dto.getDisplayName());
        return group;
    }
}