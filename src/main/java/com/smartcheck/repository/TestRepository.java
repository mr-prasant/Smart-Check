package com.smartcheck.repository;

import com.smartcheck.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByTestid(String testid);

    List<Test> findAllByCreatedBy(String createdBy);

    Boolean existsByTestidAndCreatedBy(String testid, String createdBy);
}
