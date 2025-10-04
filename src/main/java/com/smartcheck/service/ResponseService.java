package com.smartcheck.service;

import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.entity.Participant;
import com.smartcheck.entity.Problem;
import com.smartcheck.entity.Response;
import com.smartcheck.exception.ResourceExpiredException;
import com.smartcheck.exception.ResourceNotAuthorizedException;
import com.smartcheck.exception.ResourceNotFoundException;
import com.smartcheck.repository.ResponseRepository;
import com.smartcheck.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResponseService {

    private final TestService testService;
    private final ProblemService problemService;
    private final ParticipantService participantService;
    private final ResponseRepository responseRepository;

    public ResponseService(TestService testService, ProblemService problemService, ParticipantService participantService, ResponseRepository responseRepository) {
        this.testService = testService;
        this.problemService = problemService;
        this.participantService = participantService;
        this.responseRepository = responseRepository;
    }

    public Response create(Response response) {
        if (response == null) {
            throw new ResourceNotFoundException("ResponseService.create(): Not able to fetch response!");
        }

        TestResponseDTO test = this.testService.getTest(response.getTestid());
        Problem problem = this.problemService.getProblem(String.valueOf(response.getQid()));
        Participant participant = this.participantService.getByTestidAndPid(response.getTestid(), response.getPid());

        // same testid
        if (!problem.getTestid().equalsIgnoreCase(test.getTestid())) {
            throw new ResourceNotAuthorizedException("ResponseService.create(): This question is not from the given testid!");
        }

        // response is delayed
        LocalDateTime expired;
        try {
            expired = DateTimeUtil.stringToLocalDateTime(test.getExpiredAt());
        } catch (ParseException ex) {
            throw new ResourceNotAuthorizedException("ResponseService.create(): something wrong, please contact organiser!");
        }

        if (LocalDateTime.now().isAfter(expired)) {
            throw new ResourceNotAuthorizedException("ResponseService.create(): Not submitted, test is closed!");
        }

        if (response.getSelectedAnswer() == null) {
            response.setSelectedAnswer("");
        }

        Response dbResponse = this.responseRepository.findByQidAndPidAndTestid(response.getQid(), response.getPid(), response.getTestid()).orElse(null);

        participant.setTestGiven(true);
        this.participantService.updateParticipant(participant, participant.getPid());

        if (dbResponse == null) {
            return this.responseRepository.save(response);
        } else {
            // update the response
            dbResponse.setSelectedAnswer(response.getSelectedAnswer());
            return this.responseRepository.save(dbResponse);
        }
    }

    public List<Response> getAllResponseOf(String testid, String pid) {
        if (pid == null || testid == null) {
            throw new ResourceNotFoundException("ResponseService.getAllResponseOf(): Not able to fetch pid or testid!");
        }

        Participant participant = this.participantService.getByTestidAndPid(testid, pid);
        TestResponseDTO test = this.testService.getTest(testid);

        return this.responseRepository.findAllByPidAndTestid(pid, testid);
    }

    public Participant checkResponsesOf(String pid, String testid, String email) {
        if (pid == null || testid == null || email == null) {
            throw new ResourceNotFoundException("ResponseService.checkResponseFor(): Not able to fetch pid or testid!");
        }

        if (!this.testService.isTestCreatedBy(testid, email)) { // same admin can access
            throw new ResourceNotAuthorizedException("TestService.checkResponsesOf(): Not Permitted!");
        }

        return this.checkResponsesWithoutAuthOf(pid, testid);
    }

    public List<Participant> checkAllResponsesFor(String testid, String email) {
        if (email == null || testid == null) {
            throw new ResourceNotFoundException("ResponseService.checkResponseFor(): Not able to fetch pid or testid!");
        }

        if (!this.testService.isTestCreatedBy(testid, email)) { // same admin can access
            throw new ResourceNotAuthorizedException("TestService.checkResponsesOf(): Not Permitted!");
        }

        List<Participant> participants = this.participantService.getAllChecked(testid, false);
        List<Participant> checkedParticipants = new ArrayList<>();

        for (Participant participant : participants) {
            checkedParticipants.add(
                    this.checkResponsesWithoutAuthOf(participant.getPid(), testid)
            );
        }

        return checkedParticipants;
    }

    private Participant checkResponsesWithoutAuthOf(String pid, String testid) {

        Participant participant = this.participantService.getByTestidAndPid(testid, pid);
        if (participant.getChecked() != null && participant.getChecked() == true) {
            throw new ResourceExpiredException("TestService.checkResponsesOf(): This response is already checked");
        }

        List<Response> responses = this.getAllResponseOf(testid, pid);

        for (Response response : responses) {
            Problem problem = this.problemService.getProblem(String.valueOf(response.getQid()));
            participant.setTotalMarks(
                    participant.getTotalMarks()
                            + (response.getSelectedAnswer().equals(problem.getCorrectAnswer()) ? problem.getMarks() : problem.getNegativeMarks())
            );
        }

        participant.setChecked(true);
        return this.participantService.updateParticipant(participant, participant.getPid());
    }

}
