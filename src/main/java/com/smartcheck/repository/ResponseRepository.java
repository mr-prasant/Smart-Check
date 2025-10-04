package com.smartcheck.repository;

import com.smartcheck.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllByPidAndTestid(String pid, String testid);

    Optional<Response> findByQidAndPidAndTestid(Long qid, String pid, String testid);
}
