package com.testtask.githubapi.config;

import com.testtask.githubapi.mapper.RepositoryMapper;
import com.testtask.githubapi.service.RepositoryService;
import com.testtask.githubapi.service.RepositoryServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.testtask.githubapi.mapper")
public class ServiceTestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }

    @Bean
    public RepositoryService repositoryService(RestTemplate restTemplate, RepositoryMapper mapper) {
        return new RepositoryServiceImpl("test", restTemplate, mapper);
    }
}
