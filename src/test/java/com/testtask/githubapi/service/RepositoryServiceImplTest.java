package com.testtask.githubapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.githubapi.config.RepositoryServiceConf;
import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.CommitDTO;
import com.testtask.githubapi.dto.OwnerDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.response.Branch;
import com.testtask.githubapi.response.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RepositoryServiceConf.class)
public class RepositoryServiceImplTest {


    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RepositoryService service;

    @Autowired
    RepositoryMapper mapper;


    @Test
    void getRepositoriesByUsername_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getRepositoriesByUsername(null);
        });
    }

    @Test
    void getRepositoriesByUsername_shouldReturnIllegalArgumentException_whenInputParamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getRepositoriesByUsername("");
        });
    }

    @Test
    void getRepositoriesByUsername_shouldReturnListOfRepositoriesWithoutForkRepository() throws JsonProcessingException {
        RepositoryDTO repository = new RepositoryDTO("", new OwnerDTO(""), true, "", new ArrayList<>());
        RepositoryDTO[] repositories = {repository};
        server.expect(requestTo("https://api.github.com/users/test/repos"))
                        .andRespond(withSuccess(objectMapper.writeValueAsString(repositories), MediaType.APPLICATION_JSON));

        assertEquals(List.of(), service.getRepositoriesByUsername("test"));
    }

    @Test
    void getRepositoriesByUsername_shouldReturnListOfRepositories() throws JsonProcessingException {
        RepositoryDTO repository = new RepositoryDTO("", new OwnerDTO(""), false, "", new ArrayList<>());
        RepositoryDTO[] repositories = {repository};
        server.expect(requestTo("https://api.github.com/users/test/repos"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(repositories), MediaType.APPLICATION_JSON));

        BranchDTO branch = new BranchDTO("", new CommitDTO(""));
        BranchDTO[] branches = {branch};
        server.expect(requestTo(""))
                .andRespond(withSuccess(objectMapper.writeValueAsString(branches), MediaType.APPLICATION_JSON));

        List<Repository> expected = List.of(mapper.dtoToRepository(repository));
        expected.getFirst().branches().add(new Branch(branch.name(), branch.commit().sha()));
        assertEquals(expected, service.getRepositoriesByUsername("test"));
    }
}
