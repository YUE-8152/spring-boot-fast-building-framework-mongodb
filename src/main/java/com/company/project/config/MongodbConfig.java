package com.company.project.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongodbConfig {
    @Value("${spring.data.mongodb.database}")
    private String mongoDB;

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient client){
        MongoDatabase database = client.getDatabase(mongoDB);
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        return gridFSBucket;
    }
}
