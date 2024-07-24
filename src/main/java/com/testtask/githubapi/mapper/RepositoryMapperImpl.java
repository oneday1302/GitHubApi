package com.testtask.githubapi.mapper;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.response.Branch;
import com.testtask.githubapi.response.Repository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapperImpl implements RepositoryMapper {

    @Override
    public Repository dtoToRepository(RepositoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        Repository repository = new Repository();
        repository.setName(dto.getName());
        repository.setOwner_login(dto.getOwner().getLogin());
        for (BranchDTO branch : dto.getBranches()) {
            repository.addBranch(new Branch(branch.getName(), branch.getCommit().getSha()));
        }
        return repository;
    }
}
