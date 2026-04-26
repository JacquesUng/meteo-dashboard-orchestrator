package com.example.meteoapporchestrator;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FullIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_retrieveDtoList() throws Exception {
        CollectConfigurationDto dto1 = new CollectConfigurationDto(null,"Config name 1", Instant.parse("2026-02-01T00:00:00.000Z"), 2);
        String dtoString1 = objectMapper.writeValueAsString(dto1);
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString1))
                .andExpect(status().isOk());

        CollectConfigurationDto dto2= new CollectConfigurationDto(null,"Config name 2", Instant.parse("2026-02-02T00:00:00.000Z"), 1);
        String dtoString2 = objectMapper.writeValueAsString(dto2);
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString2))
                .andExpect(status().isOk());

        mockMvc.perform(get("/collect-configuration/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Config name 1"))
                .andExpect(jsonPath("$[1].name").value("Config name 2"));
    }

    @Test
    public void should_retrieveOneDto() throws Exception {
        CollectConfigurationDto dto = new CollectConfigurationDto(null,"Config name", Instant.parse("2026-02-01T00:00:00.000Z"), 2);
        String dtoString = objectMapper.writeValueAsString(dto);
        MvcResult result = mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String createdId = objectMapper.readTree(responseBody).get("Id").asString();

        mockMvc.perform(get( String.format("/collect-configuration/%s", createdId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Config name"));

        mockMvc.perform(get("/collect-configuration/944e5a4f-fc2f-419c-9613-7bc8636e92ad"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_createEntity() throws Exception {
        CollectConfigurationDto dto = new CollectConfigurationDto(null,"Config name", Instant.parse("2026-02-01T00:00:00.000Z"), 2);
        String dtoString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").exists())
                .andExpect(jsonPath("$.name").value("Config name"))
                .andExpect(jsonPath("$.startDate").value("2026-02-01T00:00:00Z"))
                .andExpect(jsonPath("$.timespan").value(2));
    }

    @Test
    public void should_throwException_when_creatingWithNotNullId() throws Exception {
        String dtoString = "{ \"Id\": \"732f8381-9783-4c46-9978-56ab0d8f7c8b\", \"name\": \"Config name\", \"startDate\": null, \"timeSpan\": null  }";
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throwException_when_updatingWithNullId() throws Exception {
        String dtoString = "{ \"Id\": null, \"name\": \"Config name\", \"startDate\": null, \"timeSpan\": null  }";
        mockMvc.perform(put("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throwException_when_nameIsNull() throws Exception {
        CollectConfigurationDto dto = new CollectConfigurationDto(null,null, Instant.parse("2026-02-01T00:00:00.000Z"), 2);
        String dtoString = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throwException_when_timeSpanIsNull() throws Exception {
        String dtoString = "{ \"Id\": null, \"name\": \"Config name\", \"startDate\": null, \"timeSpan\": null  }";
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throwException_when_timeSpanIsZero() throws Exception {
        String dtoString = "{ \"Id\": null, \"name\": \"Config name\", \"startDate\": null, \"timeSpan\": 0  }";
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throwException_when_timeSpanIsNegative() throws Exception {
        String dtoString = "{ \"Id\": null, \"name\": \"Config name\", \"startDate\": null, \"timeSpan\": -1  }";
        mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_deleteEntity() throws Exception {
        CollectConfigurationDto dto = new CollectConfigurationDto(null, "Config name", Instant.parse("2026-02-01T00:00:00.000Z"), 2);
        String dtoString = objectMapper.writeValueAsString(dto);
        MvcResult result = mockMvc.perform(post("/collect-configuration").contentType(MediaType.APPLICATION_JSON.toString()).content(dtoString))
                .andExpect(status().isOk()).andReturn();
        String createdId = objectMapper.readTree(result.getResponse().getContentAsString()).get("Id").asString();

        mockMvc.perform(delete(String.format("/collect-configuration/%s", createdId)))
                .andExpect(status().isOk());

        mockMvc.perform(get(String.format("/collect-configuration/%s", createdId)))
                .andExpect(status().isNotFound());
    }
}
