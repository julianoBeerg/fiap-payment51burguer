package com.fiap.burguer.config;

import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
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
        MongoClient mongoClient = mongoConfig.mongoClient();
        assertNotNull(mongoClient, "MongoClient should not be null.");
    }
}
