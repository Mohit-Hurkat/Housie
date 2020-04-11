package com.housie.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.housie.repository",
        mongoTemplateRef = "primaryMongoTemplate")
public class PrimaryMongoConfig {
}
