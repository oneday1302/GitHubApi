package com.testtask.githubapi.client;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;

import java.util.stream.Stream;

public interface GitHubClient {

    Stream<RepositoryDTO> getRepositoriesByUsername(String username);

    Stream<BranchDTO> getBranches(String url);
}
