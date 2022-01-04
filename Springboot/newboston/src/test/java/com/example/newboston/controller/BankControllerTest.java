package com.example.newboston.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest //this is more for integration tests
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnAllBanks() throws Exception
    {
        //when/then
        this.mockMvc.perform(get("/api/banks"))
                    .andDo(print())
                    .andExpect(status().isOk())   //checks status of request 200/404 etc
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //checks the type of response
                    .andExpect(jsonPath("$[0].accountNumber").value("1234")); //checks if the data in the json response matches back
    }

    @Test
    public void returnSpecificBank() throws Exception //returns bank with gven account number
    {
        //given
        int accountNumber = 1234;

        //when/then
        this.mockMvc.perform(get("/api/banks/{accountNumber}", accountNumber))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.trust").value("3.14"))
        .andExpect(jsonPath("$.transactionFee").value("18"));
    }
}
