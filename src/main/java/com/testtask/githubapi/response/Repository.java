package com.testtask.githubapi.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Repository {

    private String name;

    private String owner_login;

    private List<Branch> branches = new ArrayList<>();

    public void addBranch(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        branches.add(branch);
    }
}
