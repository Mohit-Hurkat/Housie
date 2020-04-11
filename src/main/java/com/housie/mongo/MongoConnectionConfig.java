package com.housie.mongo;


import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
public class MongoConnectionConfig {

    private MongoProperties primary = new MongoProperties();

    public void setPrimary(MongoProperties primary) {
        this.primary = primary;
    }
    public MongoProperties getPrimary() {
        return primary;
    }


}
