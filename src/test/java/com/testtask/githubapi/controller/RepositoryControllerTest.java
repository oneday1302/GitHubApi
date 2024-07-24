package com.testtask.githubapi.controller;

import com.testtask.githubapi.service.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoryController.class)
public class RepositoryControllerTest {

    @MockBean
    RepositoryService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void getRepositoriesByUsername_shouldReturnSetOfRepositories() throws Exception {
        when(service.getRepositoriesByUsername("test")).thenReturn(Set.of());
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/github/{username}", "test"))
                .andExpect(status().isOk());
    }
}
