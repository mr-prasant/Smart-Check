package com.smartcheck.controller;

import com.smartcheck.entity.Problem;
import com.smartcheck.service.ProblemService;
import com.smartcheck.util.AuthDetailUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("/c/problem/all")
    public ResponseEntity<?> getAllProblemsByTestid(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.problemService.getAllProblems(testid));
    }

    @GetMapping("/c/problem")
    public ResponseEntity<?> getProblemById(@RequestParam("id") String id) {
        return ResponseEntity.ok().body(this.problemService.getProblem(id));
    }

    @PostMapping("/a/problem/create")
    public ResponseEntity<?> createProblem(@RequestBody Problem problem) {
        return ResponseEntity.ok().body(this.problemService.createProblem(problem, AuthDetailUtil.getEmail()));
    }
}
