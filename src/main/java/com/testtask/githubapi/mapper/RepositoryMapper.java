package com.testtask.githubapi.mapper;

import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.response.Repository;

public interface RepositoryMapper {

    Repository dtoToRepository(RepositoryDTO dto);
}
