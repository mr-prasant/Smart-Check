package com.smartcheck.controller;

import com.smartcheck.dto.ParticipantRequestDTO;
import com.smartcheck.entity.Participant;
import com.smartcheck.service.ParticipantService;
import com.smartcheck.util.AuthDetailUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/c/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ParticipantRequestDTO participantRequest) {
        participantRequest.setPid(AuthDetailUtil.getEmail());
        return ResponseEntity.ok().body(this.participantService.register(participantRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Participant>> getAllParticipantOfTestId(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getAllByTestid(testid));
    }

    @GetMapping("/get")
    public ResponseEntity<Participant> getParticipantByTestIdAndPid(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getByTestidAndPid(testid, AuthDetailUtil.getEmail()));
    }

    @GetMapping("/submit/{pid}")
    public ResponseEntity<?> submitTest(@RequestParam("testid") String testid, @PathVariable String pid) {
        return ResponseEntity.ok().body(this.participantService.submitTest(testid, pid));
    }

    @GetMapping("/unchecked")
    public ResponseEntity<?> getAllUncheckedParticipants(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getAllChecked(testid, false));
    }

    @GetMapping("/checked")
    public ResponseEntity<?> getAllCheckedParticipants(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getAllChecked(testid, true));
    }

    @GetMapping("/result/{pid}")
    public ResponseEntity<?> getResultOf(@PathVariable String pid, @RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getResultOf(testid, pid));
    }

    @GetMapping("/result/all")
    public ResponseEntity<?> getResultOf(@RequestParam("testid") String testid) {
        return ResponseEntity.ok().body(this.participantService.getAllChecked(testid, true));
    }

}
