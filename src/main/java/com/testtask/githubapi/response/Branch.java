package com.testtask.githubapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    private String name;

    private String last_commit_sha;
}
