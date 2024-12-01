package com.fiap.burguer.config;

import com.fiap.burguer.core.application.exception.RequestException;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.bson.Document;

@Configuration
public class MongoConfig {

    @Value("${base.database}")
    private static String databaseConnection;

    public static void main(String[] args) {


        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(databaseConnection))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
            } catch (MongoException e) {
                throw new RequestException("Erro ao criar o database");
            }
        } catch (MongoClientException e) {
            throw new RequestException("Erro ao criar o database");
        }
    }
}