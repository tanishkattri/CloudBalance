package com.example.CloudBalance.repository;

import com.example.CloudBalance.model.CEGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<CEGroups, Long> {

}
