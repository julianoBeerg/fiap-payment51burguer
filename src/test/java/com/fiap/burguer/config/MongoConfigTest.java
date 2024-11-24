package com.fiap.burguer.config;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MongoConfigTest {

    @Mock
    private MongoConfig mongoConfig;

    @BeforeEach
    public void setUp() {
        mongoConfig = new MongoConfig();
    }

    @Test
    public void testGetDatabaseName() {
        String databaseName = mongoConfig.getDatabaseName();
        assertEquals("51burguer", databaseName);
    }

    @Test
    public void testMongoClient() {
        String connectionUri = "mongodb://mongo:mongo@172.17.0.1:27017";
        ConnectionString connectionString = new ConnectionString(connectionUri);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        assertNotNull( MongoClients.create(mongoClientSettings), "MongoClient should not be null.");
    }
}
