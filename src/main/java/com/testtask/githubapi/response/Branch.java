package com.testtask.githubapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Branch {

    private String name;

    private String last_commit_sha;
}
