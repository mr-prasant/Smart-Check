package com.smartcheck.controller;

import com.smartcheck.entity.Response;
import com.smartcheck.service.ResponseService;
import com.smartcheck.util.AuthDetailUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping("/c/response/create")
    public ResponseEntity<?> createResponse(@RequestBody Response response) {
        response.setPid(AuthDetailUtil.getEmail());
        return ResponseEntity.ok().body(this.responseService.create(response));
    }

    @GetMapping("/c/response/all/{pid}")
    public ResponseEntity<?> getAllResponseOf(@PathVariable String pid, @RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.responseService.getAllResponseOf(testid, pid));
    }

    @GetMapping("/a/response/check/{pid}")
    public ResponseEntity<?> checkAllResponsesOf(@PathVariable String pid, @RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.responseService.checkResponsesOf(pid, testid, AuthDetailUtil.getEmail()));
    }

    @GetMapping("/a/response/check/all")
    public ResponseEntity<?> checkAllParticipantsResponsesOf(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.responseService.checkAllResponsesFor(testid, AuthDetailUtil.getEmail()));
    }

}