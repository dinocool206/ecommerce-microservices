package com.example.order.integrationTest;

import com.example.order.client.InventoryClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderIntegrationTest {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderIntegrationTest.class);
    @MockBean
    private InventoryClient inventoryClient;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldPlaceOrderSuccessfully() {

        InventoryClient.InventoryBatchDTO batch =
                new InventoryClient.InventoryBatchDTO();
        batch.batchId = 1L;
        batch.quantity = 10;

        when(inventoryClient.getInventory(1002L))
                .thenReturn(List.of(batch));

        String url = "http://localhost:" + port + "/order";

        String requestBody = """
                {
                  "productId": 1002,
                  "quantity": 3
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);
        logger.info("Order Placed Response: {}",response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("PLACED");
    }
}
