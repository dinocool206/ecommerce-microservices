package com.example.inventory.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryIntegrationTest {
    private static final Logger logger =
            LoggerFactory.getLogger(InventoryIntegrationTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnInventorySortedByExpiry() {

        String url = "http://localhost:" + port + "/inventory/1005";

        ResponseEntity<String> response =
                restTemplate.getForEntity(url, String.class);

        logger.info("Response is {}",response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Smartwatch");
    }

    @Test
    void shouldUpdateInventory() {

        String url = "http://localhost:" + port + "/inventory/update";

        String requestBody = """
                {
                  "productId": 1005,
                  "quantity": 5
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        logger.info("Updated Response is {}",response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Inventory updated");
    }
}
