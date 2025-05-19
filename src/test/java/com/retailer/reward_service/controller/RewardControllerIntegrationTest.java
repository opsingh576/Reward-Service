package com.retailer.reward_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.reward_service.dto.RewardResponse;
import com.retailer.reward_service.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCustomersRewards_AllCustomers() throws Exception {
        List<RewardResponse> mockList = List.of(
                new RewardResponse(1, "Alice", Map.of("June", 120), 120),
                new RewardResponse(2, "Bob", Map.of("June", 90), 90)
        );
        Mockito.when(rewardService.calculateAllCustomersRewards()).thenReturn(mockList);

        mockMvc.perform(get("/api/rewards/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[1].customerName").value("Bob"));
    }

    @Test
    void getCustomersRewards_SingleCustomer() throws Exception {
        RewardResponse mockResponse = new RewardResponse(1, "Alice", Map.of("June", 120), 120);
        Mockito.when(rewardService.calculateCustomerRewardsById(anyInt())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards/customers?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].customerName").value("Alice"));
    }
}
