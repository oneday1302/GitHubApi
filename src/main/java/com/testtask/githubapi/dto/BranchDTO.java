package com.testtask.githubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BranchDTO {

    private String name;

    private CommitDTO commit;
}
