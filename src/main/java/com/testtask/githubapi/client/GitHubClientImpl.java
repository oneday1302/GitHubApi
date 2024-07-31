package com.testtask.githubapi.client;

import com.testtask.githubapi.dto.BranchDTO;
import com.testtask.githubapi.dto.RepositoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final RestClient client;
    private final String url;

    public GitHubClientImpl(RestClient.Builder builder, @Value("${api.github.url}") String url) {
        this.client = builder.build();
        this.url = url;
    }

    @Override
    public Stream<RepositoryDTO> getRepositoriesByUsername(String username) {
        return Stream.of(Objects.requireNonNull(client.get().uri(url, username).retrieve().body(RepositoryDTO[].class)));
    }

    @Override
    public Stream<BranchDTO> getBranches(String url) {
        return Stream.of(Objects.requireNonNull(client.get().uri(url).retrieve().body(BranchDTO[].class)));
    }
}
