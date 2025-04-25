package com.example.CloudBalance.repository;

import com.example.CloudBalance.model.CEGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CEGroupRepository extends JpaRepository<CEGroups, Long> {

}
