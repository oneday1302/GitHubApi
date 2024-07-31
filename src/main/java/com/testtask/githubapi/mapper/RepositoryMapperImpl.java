package com.testtask.githubapi.mapper;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import com.testtask.githubapi.response.Branch;
import com.testtask.githubapi.response.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepositoryMapperImpl implements RepositoryMapper {

    @Override
    public Repository dtoToRepository(RepositoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        List<Branch> branches = new ArrayList<>();
        for (BranchDTO branch : dto.branches()) {
            branches.add(new Branch(branch.name(), branch.commit().sha()));
        }
        return new Repository(dto.name(), dto.owner().login(), branches);
    }
}
