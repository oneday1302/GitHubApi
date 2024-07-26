package com.testtask.githubapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RepositoryDTO {

    private String name;

    private OwnerDTO owner;

    private boolean fork;

    private String branches_url;

    private final List<BranchDTO> branches = new ArrayList<>();

    public void addBranch(BranchDTO branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        branches.add(branch);
    }
}
