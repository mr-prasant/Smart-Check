package com.smartcheck.service;

import com.smartcheck.dto.TestCreationRequestDTO;
import com.smartcheck.dto.TestCreationResponseDTO;
import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.entity.Test;
import com.smartcheck.entity.User;
import com.smartcheck.exception.ResourceNotAuthorizedException;
import com.smartcheck.exception.ResourceNotFoundException;
import com.smartcheck.repository.TestRepository;
import com.smartcheck.repository.UserRepository;
import com.smartcheck.util.DateTimeUtil;
import com.smartcheck.util.TestToTestResponse;
import com.smartcheck.util.UniqueID;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final UserRepository userRepository;

    public TestService(TestRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    public TestCreationResponseDTO createTest(TestCreationRequestDTO testRequest) {
        if (testRequest == null) {
            throw new ResourceNotFoundException("TestService.createTest(): Not able to fetch data!");
        }

        User user = userRepository.findByEmail(testRequest.getCreatedBy()).orElseThrow(
                () -> new ResourceNotFoundException("TestService.createTest(): Invalid email!")
        );

        if (testRequest.getDuration() <= 0) {
            throw new ResourceNotAuthorizedException("TestService.createTest(): Invalid Duration!");
        }

        Test test = new Test();
        test.setTestid(UniqueID.getIdWithSuffix("T"));
        test.setCreatedBy(testRequest.getCreatedBy().toLowerCase());
        test.setTitle(testRequest.getTitle());
        test.setDescription(testRequest.getDescription());
        test.setCreatedAt(LocalDateTime.now());
        LocalDateTime startsAtLDT, registeredByLDT;

        try {
            startsAtLDT = DateTimeUtil.stringToLocalDateTime(testRequest.getStartsAt());
            registeredByLDT = DateTimeUtil.stringToLocalDateTime(testRequest.getRegisteredBy());
        } catch (ParseException e) {
            throw new RuntimeException("TestService.createTest(): Started At or Registered By time is not in format (dd-MM-yyyy hh:mm:ss a).");
        }

        test.setStartsAt(startsAtLDT);
        test.setExpiredAt(startsAtLDT.plusHours(testRequest.getExpiredAfter()));

        if (registeredByLDT.isAfter(test.getExpiredAt())) {
            throw new RuntimeException("TestService.createTest(): Registration time should not greater than expired time");
        }

        test.setRegisteredBy(registeredByLDT);
        test.setDuration(testRequest.getDuration());

        testRepository.save(test);
        return new TestCreationResponseDTO(test.getTestid(), test.getCreatedBy());
    }

    public TestResponseDTO getTest(String testid) {
        if (testid == null) {
            throw new ResourceNotFoundException("TestService.getTest(): Not able to fetch id!");
        }

        Test test = testRepository.findByTestid(testid).orElseThrow(
                () -> new ResourceNotFoundException("TestService.getTest(): Test not found!")
        );

        return TestToTestResponse.toTestResponse(test);
    }

    public List<TestResponseDTO> getAllTestOf(String createdBy) {
        if (createdBy == null) {
            throw new ResourceNotFoundException("TestService.getAllTestOf(): Not able to fetch email!");
        }

        User user = userRepository.findByEmail(createdBy).orElseThrow(
                () -> new ResourceNotFoundException("TestService.getAllTestOf(): Email not found!")
        );

        List<Test> tests = testRepository.findAllByCreatedBy(createdBy);
        List<TestResponseDTO> testdtos = new ArrayList<>();

        for (Test test : tests) {
            testdtos.add(TestToTestResponse.toTestResponse(test));
        }

        return testdtos;
    }

    public Boolean isTestCreatedBy(String testid, String email) {
        return this.testRepository.existsByTestidAndCreatedBy(testid, email);
    }

}
