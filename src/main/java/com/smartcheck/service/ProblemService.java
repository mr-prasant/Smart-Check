package com.smartcheck.service;

import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.entity.Problem;
import com.smartcheck.exception.ResourceNotAuthorizedException;
import com.smartcheck.exception.ResourceNotFoundException;
import com.smartcheck.repository.ProblemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProblemService {

    private final TestService testService;
    private final UserService userService;
    private final ProblemRepository problemRepository;

    public ProblemService(TestService testService, UserService userService, ProblemRepository problemRepository) {
        this.testService = testService;
        this.userService = userService;
        this.problemRepository = problemRepository;
    }

    public List<Problem> getAllProblems(String testid) {
        if (testid == null) {
            throw new ResourceNotFoundException("ProblemService.getAllProblem(): Not able to fetch testid!");
        }

        return this.problemRepository.findAllByTestid(testid);
    }

    public Problem getProblem(String strid) {
        if (strid == null) {
            throw new ResourceNotFoundException("ProblemService.getProblem(): Not able to fetch problem id!");
        }

        long id = 0L;
        try {
            id = Long.parseLong(strid);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("ProblemService.getProblem(): Invalid id provided!");
        }

        return this.problemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ProblemService.getProblem(): Problem not found!")
        );
    }

    public Problem createProblem(Problem problem, String email) {
        if (problem == null) {
            throw new ResourceNotFoundException("ProblemService.createProblem(): Not able to fetch problem data!");
        }

        TestResponseDTO test = this.testService.getTest(problem.getTestid()); // check the test

        if (!test.getCreatedBy().equalsIgnoreCase(email)) {
            throw new ResourceNotAuthorizedException("ProblemService.createProblem(): User not Authenticated!");
        }

        return this.problemRepository.save(problem);
    }
}
