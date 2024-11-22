package com.consertreservation.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {
    
    @Test
    void stringToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(1L);
        System.out.println("s = " + s);
    }
}
