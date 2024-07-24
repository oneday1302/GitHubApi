package com.testtask.githubapi.service;


import com.testtask.githubapi.response.Repository;

import java.util.Set;

public interface RepositoryService {

    Set<Repository> getRepositoriesByUsername(String username);
}
