package com.testtask.githubapi.service;

import com.testtask.githubapi.config.ServiceTestConfig;
import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.CommitDTO;
import com.testtask.githubapi.dto.OwnerDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.mapper.RepositoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

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
    void getRepositoriesByUsername_shouldReturnSetOfRepositoriesWithoutForkRepository() {
        RepositoryDTO repository = RepositoryDTO
                .builder()
                .name("")
                .owner(new OwnerDTO(""))
                .fork(true)
                .branches_url("")
                .build();

        RepositoryDTO[] repositories = {repository};
        when(restTemplate.getForEntity("test", RepositoryDTO[].class, "test")).thenReturn(new ResponseEntity<>(repositories, HttpStatusCode.valueOf(200)));

        assertEquals(Set.of(), service.getRepositoriesByUsername("test"));
        verify(restTemplate, times(1)).getForEntity("test", RepositoryDTO[].class, "test");
    }

    @Test
    void getRepositoriesByUsername_shouldReturnSetOfRepositories() {
        BranchDTO branch = new BranchDTO("", new CommitDTO(""));
        RepositoryDTO repository = RepositoryDTO
                .builder()
                .name("")
                .owner(new OwnerDTO(""))
                .fork(false)
                .branches_url("")
                .build();
        repository.addBranch(branch);

        RepositoryDTO[] repositories = {repository};
        when(restTemplate.getForEntity("test", RepositoryDTO[].class, "test")).thenReturn(new ResponseEntity<>(repositories, HttpStatusCode.valueOf(200)));

        BranchDTO[] branches = {branch};
        when(restTemplate.getForEntity("", BranchDTO[].class)).thenReturn(new ResponseEntity<>(branches, HttpStatusCode.valueOf(200)));

        assertEquals(Set.of(mapper.dtoToRepository(repository)), service.getRepositoriesByUsername("test"));
        verify(restTemplate, times(1)).getForEntity("test", RepositoryDTO[].class, "test");
        verify(restTemplate, times(1)).getForEntity("", BranchDTO[].class);
    }
}
