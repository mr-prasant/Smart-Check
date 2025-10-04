package com.smartcheck.service;

import com.smartcheck.dto.ParticipantRequestDTO;
import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.entity.Participant;
import com.smartcheck.exception.NoUniqueResourceException;
import com.smartcheck.exception.ResourceExpiredException;
import com.smartcheck.exception.ResourceNotFoundException;
import com.smartcheck.repository.ParticipantRepository;
import com.smartcheck.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ParticipantService {

    private final TestService testService;
    private final UserService userService;
    private final ParticipantRepository participantRepository;

    public ParticipantService(TestService testService, UserService userService, ParticipantRepository participantRepository) {
        this.testService = testService;
        this.userService = userService;
        this.participantRepository = participantRepository;
    }

    // register
    public Participant register(ParticipantRequestDTO participantRequest) {
        if (participantRequest == null || participantRequest.getPid() == null || participantRequest.getTestid() == null) {
            throw new ResourceNotFoundException("ParticipantService.register(): Not able to fetch data!");
        }

        // checking the email
        TestResponseDTO test = this.testService.getTest(participantRequest.getTestid());

        // existing participant of the test
        if (this.participantRepository.findByTestidAndPid(participantRequest.getTestid(), participantRequest.getPid()).orElse(null) != null) {
            throw new NoUniqueResourceException("ParticipantService.register(): User already registered!");
        }

        LocalDateTime now = LocalDateTime.now();

        try {
            if (now.isAfter(DateTimeUtil.stringToLocalDateTime(test.getRegisteredBy()))) {
                throw new ResourceExpiredException("ParticipantService.register(): Registration time out!");
            }
        } catch (ParseException ex) {
            throw new RuntimeException("ParticipantService.register(): Problem in test registration, please contact the test organiser!");
        }

        Participant participant = new Participant();
        participant.setPid(participantRequest.getPid());
        participant.setTestid(participantRequest.getTestid());
        participant.setTestGiven(false);
        participant.setChecked(false);
        participant.setTotalMarks(0);
        participant.setGivenAt(null);
        participant.setRegisteredAt(now);

        return this.participantRepository.save(participant);
    }

    // get all participants with testid
    public List<Participant> getAllByTestid(String testid) {
        if (testid == null) {
            throw new ResourceNotFoundException("ParticipantService.getAllByTestid(): Not able to fetch testid!");
        }

        return this.participantRepository.findByTestid(testid);
    }

    public Participant getByTestidAndPid(String testid, String pid) {
        if (testid == null || pid == null) {
            throw new ResourceNotFoundException("ParticipantService.getAllByTestidAndPid(): Not able to fetch testid or pid!");
        }

        return this.participantRepository.findByTestidAndPid(testid, pid).orElseThrow(
                () -> new ResourceNotFoundException("ParticipantService.getAllByTestidAndPid(): Not registered!")
        );
    }

    public List<Participant> getAllChecked(String testid, Boolean checked) {
        if (testid == null) {
            throw new ResourceNotFoundException("ParticipantService.getAllUnchecked(): Not able to fetch testid or createdBy!");
        }

        TestResponseDTO test = this.testService.getTest(testid); // check the test

        return this.participantRepository.findByTestidAndChecked(testid, checked);
    }

    public Participant updateParticipant(Participant participant, String pid) {
        if (participant == null || pid == null) {
            throw new ResourceNotFoundException("ParticipantService.updateParticipant(): Not able to fetch participant data!");
        }

        Participant dbParticipant = this.participantRepository.findByTestidAndPid(participant.getTestid(), pid).orElseThrow(
                () -> new ResourceNotFoundException("ParticipantService.updateParticipant(): Participant not found")
        );

        if (participant.getTestGiven() != null) {
            dbParticipant.setTestGiven(participant.getTestGiven());
        }

        if (participant.getTotalMarks() > 0) {
            dbParticipant.setTotalMarks(participant.getTotalMarks());
        }

        if (participant.getGivenAt() != null) {
            dbParticipant.setGivenAt(participant.getGivenAt());
        }

        if (participant.getChecked() != null) {
            dbParticipant.setChecked(participant.getChecked());
        }

        return this.participantRepository.save(dbParticipant);
    }

    public Participant getResultOf(String testid, String pid) {
        if (testid == null || pid == null) {
            throw new ResourceNotFoundException("ParticipantService.getResultOf(): Not able to fetch testid or pid!");
        }

        Participant participant = this.getByTestidAndPid(testid, pid);

        if (participant.getChecked() != null && participant.getChecked() == true) {
            return participant;
        }

        throw new ResourceNotFoundException("ParticipantService.getResultOf(): This response is not checked!");
    }

    public List<Participant> getAllResultOf(String testid) {
        if (testid == null) {
            throw new ResourceNotFoundException("ParticipantService.getAllResultOf(): Not able to fetch testid!");
        }

        return this.getAllChecked(testid, true);
    }

    public Participant submitTest(String testid, String pid) {
        if (testid == null || pid == null) {
            throw new ResourceNotFoundException("ParticipantService.submitTest(): Not able to fetch testid or pid!");
        }

        this.userService.getUser(pid);

        Participant participant = this.participantRepository.findByTestidAndPid(testid, pid).orElseThrow(
                () -> new ResourceNotFoundException("ParticipantService.submitTest(): Not Registered")
        );

        participant.setTestGiven(true);
        participant.setGivenAt(LocalDateTime.now());

        return this.participantRepository.save(participant);
    }

}
