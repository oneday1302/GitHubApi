package com.testtask.githubapi.controller;

import com.testtask.githubapi.response.Repository;
import com.testtask.githubapi.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/github")
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryService service;

    @GetMapping("/{username}")
    public ResponseEntity<Set<Repository>> getRepositoriesByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getRepositoriesByUsername(username));
    }
}
