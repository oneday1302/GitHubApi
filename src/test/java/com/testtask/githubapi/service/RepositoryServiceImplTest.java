package com.testtask.githubapi.service;

import com.testtask.githubapi.config.ServiceTestConfig;
import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.CommitDTO;
import com.testtask.githubapi.dto.OwnerDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.response.Branch;
import com.testtask.githubapi.response.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceTestConfig.class)
public class RepositoryServiceImplTest {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RepositoryService service;

    @Autowired
    RepositoryMapper mapper;

    @BeforeEach
    public void mockReset() {
        reset(restTemplate);
    }

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
    void getRepositoriesByUsername_shouldReturnListOfRepositoriesWithoutForkRepository() {
        RepositoryDTO repository = RepositoryDTO
                .builder()
                .name("")
                .owner(new OwnerDTO(""))
                .fork(true)
                .branches_url("")
                .build();

        RepositoryDTO[] repositories = {repository};
        when(restTemplate.getForEntity("test", RepositoryDTO[].class, "test")).thenReturn(new ResponseEntity<>(repositories, HttpStatusCode.valueOf(200)));

        assertEquals(List.of(), service.getRepositoriesByUsername("test"));
        verify(restTemplate, times(1)).getForEntity("test", RepositoryDTO[].class, "test");
    }

    @Test
    void getRepositoriesByUsername_shouldReturnListOfRepositories() {
        BranchDTO branch = new BranchDTO("", new CommitDTO(""));
        RepositoryDTO repository = RepositoryDTO
                .builder()
                .name("")
                .owner(new OwnerDTO(""))
                .fork(false)
                .branches_url("")
                .build();

        RepositoryDTO[] repositories = {repository};
        when(restTemplate.getForEntity("test", RepositoryDTO[].class, "test")).thenReturn(new ResponseEntity<>(repositories, HttpStatusCode.valueOf(200)));

        BranchDTO[] branches = {branch};
        when(restTemplate.getForEntity("", BranchDTO[].class)).thenReturn(new ResponseEntity<>(branches, HttpStatusCode.valueOf(200)));

        List<Repository> expected = List.of(mapper.dtoToRepository(repository));
        expected.getFirst().addBranch(new Branch(branch.getName(), branch.getCommit().getSha()));
        assertEquals(expected, service.getRepositoriesByUsername("test"));
        verify(restTemplate, times(1)).getForEntity("test", RepositoryDTO[].class, "test");
        verify(restTemplate, times(1)).getForEntity("", BranchDTO[].class);
    }
}
