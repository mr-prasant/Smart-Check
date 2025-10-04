package com.smartcheck.controller;

import com.smartcheck.dto.TestCreationRequestDTO;
import com.smartcheck.dto.TestCreationResponseDTO;
import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.service.TestService;
import com.smartcheck.util.AuthDetailUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/a/test/create") // for admin
    public ResponseEntity<TestCreationResponseDTO> createTest(@RequestBody TestCreationRequestDTO testRequest) {
        testRequest.setCreatedBy(AuthDetailUtil.getEmail());
        return ResponseEntity.ok().body(this.testService.createTest(testRequest));
    }

    @GetMapping("/p/test")
    public ResponseEntity<TestResponseDTO> getTest(@RequestParam("id") String testid) {
        return ResponseEntity.ok().body(this.testService.getTest(testid));
    }

    @GetMapping("/p/test/all")
    public ResponseEntity<List<TestResponseDTO>> getAllTestByEmail(@RequestParam("email") String createdBy) {
        return ResponseEntity.ok().body(this.testService.getAllTestOf(createdBy));
    }
}
