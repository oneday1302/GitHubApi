package com.testtask.githubapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class RepositoryDTO {

    private String name;

    private OwnerDTO owner;

    private boolean fork;

    private String branches_url;

    private final Set<BranchDTO> branches = new HashSet<>();

    public void addBranch(BranchDTO branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        branches.add(branch);
    }
}
