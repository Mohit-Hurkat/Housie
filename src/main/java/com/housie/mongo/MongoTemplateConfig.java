package com.housie.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
@EnableConfigurationProperties(MongoConnectionConfig.class)
public class MongoTemplateConfig {

    @Autowired
    private MongoConnectionConfig mongoConnectionConfig;

    @Value("${mongo.connectionsPerHost:50}")
    private Integer mongoConnectionPerHost;

    @Value("${mongo.connectTimeout:1000}")
    private Integer mongoConnectionTimeout;

    @Value("${mongo.maxWaitTime:1500}")
    private Integer mongoMaxWaitTime;

    @Value("${mongo.socketKeepAlive:true}")
    private Boolean mongoSocketKeepAlive;

    @Value("${mongo.socketTimeout:1500}")
    private Integer mongoSocketTimeout;

    @Value("${mongo.serverSelectionTimeout:10000}")
    private Integer serverSelectionTimeout;

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() throws Exception {

        MongoDbFactory mongoDbFactory = primaryFactory(this.mongoConnectionConfig.getPrimary());
        MongoTemplate mongoTemplate = removeClassName(mongoDbFactory);
        System.out.println("mongoTemplatePrimary "+ mongoTemplate + " "+mongoTemplate);
        return mongoTemplate;
    }

    @Bean
    @Primary
    public MongoDbFactory primaryFactory(final MongoProperties mongo) throws Exception {
        MongoClient mongoClient = configureMongoClient(mongo.getHost(), mongo.getPort());
        return new SimpleMongoDbFactory(mongoClient, mongo.getDatabase());
    }


    private MongoTemplate removeClassName(MongoDbFactory mongoDbFactory){

        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(mongoDbFactory, converter);
    }

    private MongoClient configureMongoClient(String host, Integer port){

        return new MongoClient(new ServerAddress(host, port), MongoClientOptions.builder()
                .connectionsPerHost(mongoConnectionPerHost)
                .connectTimeout(mongoConnectionTimeout)
                .maxWaitTime(mongoMaxWaitTime)
                .socketKeepAlive(mongoSocketKeepAlive)
                .socketTimeout(mongoSocketTimeout)
                .serverSelectionTimeout(serverSelectionTimeout)
                .build());
    }
}
