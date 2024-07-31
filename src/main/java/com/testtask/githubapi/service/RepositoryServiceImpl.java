package com.testtask.githubapi.service;

import com.testtask.githubapi.client.GitHubClient;
import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.response.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final GitHubClient client;
    private final RepositoryMapper mapper;

    @Override
    public List<Repository> getRepositoriesByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Param cannot be null or empty.");
        }
        return client
                .getRepositoriesByUsername(username)
                .filter(r -> !r.fork())
                .peek(r -> client.getBranches(r.branches_url().replace("{/branch}", "")).forEach(b -> r.branches().add(b)))
                .map(mapper::dtoToRepository)
                .toList();
    }
}
