package com.testtask.githubapi.service;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.response.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final String URL;

    private final RestTemplate restTemplate;

    private final RepositoryMapper mapper;

    public RepositoryServiceImpl(@Value("${api.github.url}") String url, RestTemplate restTemplate, RepositoryMapper mapper) {
        this.URL = url;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Repository> getRepositoriesByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Param cannot be null or empty.");
        }
        List<Repository> repositories = new ArrayList<>();
        for (RepositoryDTO repository : getRepositories(username)) {
            if (repository.fork()) {
                continue;
            }
            for (BranchDTO branch : getBranches(repository.branches_url().replace("{/branch}", ""))) {
                repository.branches().add(branch);
            }
            repositories.add(mapper.dtoToRepository(repository));
        }
        return repositories;
    }

    private List<RepositoryDTO> getRepositories(String username) {
        return List.of(
                Objects.requireNonNull(
                        restTemplate.getForEntity(URL, RepositoryDTO[].class, username).getBody()));
    }

    private List<BranchDTO> getBranches(String url) {
        return List.of(
                Objects.requireNonNull(
                        restTemplate.getForEntity(url, BranchDTO[].class).getBody()));
    }
}
