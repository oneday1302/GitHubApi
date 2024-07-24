package com.testtask.githubapi.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Repository {

    private String name;

    private String owner_login;

    private Set<Branch> branches = new HashSet<>();

    public void addBranch(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        branches.add(branch);
    }
}
