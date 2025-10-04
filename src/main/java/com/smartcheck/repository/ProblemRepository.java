package com.smartcheck.repository;

import com.smartcheck.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByTestid(String testid);
}
