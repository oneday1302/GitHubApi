package com.testtask.githubapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.testtask.githubapi.service")
@ComponentScan("package com.testtask.githubapi.client")
@ComponentScan("package com.testtask.githubapi.mapper")
public class RepositoryServiceConf {

}
