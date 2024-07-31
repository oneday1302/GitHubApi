package com.testtask.githubapi.response;

import java.util.ArrayList;
import java.util.List;

public record Repository(String name, String owner_login, List<Branch> branches) {

    public Repository {
        if (branches == null) {
            branches = new ArrayList<>();
        }
    }
}
