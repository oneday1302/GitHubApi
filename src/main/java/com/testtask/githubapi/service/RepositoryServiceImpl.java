package com.testtask.githubapi.service;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.response.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    public Set<Repository> getRepositoriesByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Param cannot be null or empty.");
        }
        Set<Repository> repositories = new HashSet<>();
        for (RepositoryDTO repository : getRepositories(username)) {
            if (repository.isFork()) {
                continue;
            }
            for (BranchDTO branch : getBranches(repository.getBranches_url().replace("{/branch}", ""))) {
                repository.addBranch(branch);
            }
            repositories.add(mapper.dtoToRepository(repository));
        }
        return repositories;
    }

    private Set<RepositoryDTO> getRepositories(String username) {
        return Set.of(
                Objects.requireNonNull(
                        restTemplate.getForEntity(URL, RepositoryDTO[].class, username).getBody()));
    }

    private Set<BranchDTO> getBranches(String url) {
        return Set.of(
                Objects.requireNonNull(
                        restTemplate.getForEntity(url, BranchDTO[].class).getBody()));
    }
}
