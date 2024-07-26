package com.testtask.githubapi.service;


import com.testtask.githubapi.response.Repository;

import java.util.List;

public interface RepositoryService {

    List<Repository> getRepositoriesByUsername(String username);
}
