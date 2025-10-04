package com.smartcheck.util;

import com.smartcheck.dto.TestResponseDTO;
import com.smartcheck.entity.Test;

public class TestToTestResponse {
    public static TestResponseDTO toTestResponse(Test test) {
        TestResponseDTO testResponseDTO = new TestResponseDTO();
        testResponseDTO.setId(test.getId());
        testResponseDTO.setTestid(test.getTestid());
        testResponseDTO.setCreatedBy(test.getCreatedBy());
        testResponseDTO.setTitle(test.getTitle());
        testResponseDTO.setDescription(test.getDescription());
        testResponseDTO.setCreatedAt(DateTimeUtil.localDateTimeToString(test.getCreatedAt()));
        testResponseDTO.setStartsAt(DateTimeUtil.localDateTimeToString(test.getStartsAt()));
        testResponseDTO.setExpiredAt(DateTimeUtil.localDateTimeToString(test.getExpiredAt()));
        testResponseDTO.setRegisteredBy(DateTimeUtil.localDateTimeToString(test.getRegisteredBy()));
        testResponseDTO.setDuration(test.getDuration());

        return testResponseDTO;
    }
}
