package com.testtask.githubapi.dto;

import java.util.ArrayList;
import java.util.List;

public record RepositoryDTO(String name, OwnerDTO owner, boolean fork, String branches_url, List<BranchDTO> branches) { ;

    public RepositoryDTO {
        if (branches == null) {
            branches = new ArrayList<>();
        }
    }
}
