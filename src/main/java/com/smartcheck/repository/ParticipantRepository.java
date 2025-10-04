package com.smartcheck.repository;

import com.smartcheck.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByPid(String pid);

    List<Participant> findByTestid(String testid);

    List<Participant> findByTestGiven(Boolean testGiven);

    Optional<Participant> findByTestidAndPid(String testid, String pid);

    Optional<Participant> findByTestidAndPidAndChecked(String testid, String pid, Boolean checked);

    List<Participant> findByTestidAndChecked(String testid, Boolean checked);
}
