package com.example.newboston.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.newboston.model.Bank;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest //this is more for integration tests
@AutoConfigureMockMvc
public class BankControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BankControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) 
    {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }    

    private String baseUrl = "/api/banks";

    @Nested //helps to nest generalized tests together under one class e.g testing for all posts request etc
    @DisplayName("GET /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    public class GetBanks{
        @Test
        public void returnAllBanks() throws Exception
        {
            //when/then
            mockMvc.perform(get(baseUrl))
                        .andDo(print())
                        .andExpect(status().isOk())   //checks status of request 200/404 etc
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //checks the type of response
                        .andExpect(jsonPath("$[0].accountNumber").value("1234")); //checks if the data in the json response matches back
        }
    }

    @Nested
    @DisplayName("GET /api/bank/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    public class GetBank{
        @Test
        public void returnSpecificBank() throws Exception //returns bank with gven account number
        {
            //given
            int accountNumber = 1234;

            //when/then
            mockMvc.perform(get(baseUrl + "/{accountNumber}", accountNumber))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.trust").value("3.14"))
            .andExpect(jsonPath("$.transactionFee").value("18"));
        }

        @Test
        public void missingAccountNumber() throws Exception //returns Not Found if the account # does not exist
        {
            //given
            String accountNumber = "does_not_exist";

            //when/then
            mockMvc.perform(get(baseUrl + "/" + accountNumber))
                   .andDo(print())
                   .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    public class PostNewBanks
    {
        @Test  //this test is not working will come back to it
        public void addNewBank() throws Exception
        {
            //given
            Bank newBank = new Bank("acc123", 31.415, 2);

            //when
            var performPost = mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBank))
                    );
        
            //then
            performPost.andDo(print())
                       .andExpect(status().isCreated())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                       .andExpect(jsonPath("$.accountNumber").value("acc123"))
                       .andExpect(jsonPath("$.trust").value("31.415"))
                       .andExpect(jsonPath("$.transactionFee").value("2"));
            
            mockMvc.perform(get(baseUrl + newBank.getAccountNumber()))
                   .andExpect(content().json(objectMapper.writeValueAsString(newBank)));
        }

        @Test
        //this checks if a bank with a given account number already exists
        public void existingBank() throws Exception
        {
            //given
            Bank invalidBank = new Bank("1234", 12, 23);

            //when
            var performPost = mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBank))
                    );
            
            //then
            performPost.andDo(print())
                       .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    public class PatchingExisitngBank
    {
        @Test
        public void updateExisitingBank() throws Exception
        {
            //given
            var updatedBank = new Bank("1234", 12, 23);

            //when
            var performPatch = mockMvc.perform(patch(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedBank))
                        );

            //then
            performPatch.andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(objectMapper.writeValueAsString(updatedBank)));

            mockMvc.perform(get(baseUrl + updatedBank.getAccountNumber()))
                           .andExpect(content().json(objectMapper.writeValueAsString(updatedBank)));
        }

        @Test
        public void exisitingAccountNumber() throws Exception
        {
            //given
            Bank invalidBank = new Bank("does_not_exist", 1.0, 1);

            //when
            var performPatch = mockMvc.perform(patch(baseUrl)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(invalidBank))
                                );
            
            //then
            performPatch.andDo(print())
                        .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    public class DeleteExistingBank 
    {
        @Test
        //@DirtiesContext  <-- this just creates a fresh instance since the test could alter the needed data
        public void deleteBank() throws Exception //deletes bank based on given account number
        {
            //given
            var accountNumber = 1234;

            //when/then
            mockMvc.perform(delete(baseUrl + "/{accountNumber}", accountNumber))
                   .andDo(print())
                   .andExpect(status().isNoContent());
            
            mockMvc.perform(get(baseUrl + "/{accountNumber}", accountNumber))
                   .andExpect(status().isNotFound());
        }

        @Test
        public void deleteInvalidBank() throws Exception
        {
            //given
            String invalidAccountNumber = "does_not_exist";

            //when
            mockMvc.perform(delete(baseUrl + "/{invalidAccountNumber}", invalidAccountNumber))
                   .andDo(print())
                   .andExpect(status().isNotFound());
        }
    }
}
