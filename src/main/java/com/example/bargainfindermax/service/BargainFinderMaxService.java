package com.example.bargainfindermax.service;

import com.example.bargainfindermax.dto.BargainFinderMaxRequest;
import com.example.bargainfindermax.dto.BargainFinderMaxResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BargainFinderMaxService {

    private static final Logger logger = LogManager.getLogger(BargainFinderMaxService.class);

    @Value("${sabre.api.url}")
    private String apiUrl;

    @Value("${sabre.api.client_id}")
    private String clientId;

    @Value("${sabre.api.client_secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BargainFinderMaxResponse searchFlights(BargainFinderMaxRequest request) {
        logger.info("Initiating flight search...");
        logger.debug("Request payload: {}", request);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create HTTP POST request
            HttpPost postRequest = new HttpPost(apiUrl);
            postRequest.setHeader("Authorization", "Bearer " + getAccessToken());
            postRequest.setHeader("Content-Type", "application/json");

            // Convert request object to JSON string
            String requestBody = objectMapper.writeValueAsString(request);
            logger.debug("Serialized request body: {}", requestBody);

            // Set JSON body
            StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
            postRequest.setEntity(entity);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getCode();
                logger.info("Received response with status code: {}", statusCode);

                if (statusCode >= 200 && statusCode < 300) {
                    // Parse the response body
                    String responseBody = new String(response.getEntity().getContent().readAllBytes());
                    logger.debug("Response body: {}", responseBody);

                    BargainFinderMaxResponse bargainFinderMaxResponse = objectMapper.readValue(responseBody, BargainFinderMaxResponse.class);
                    logger.info("Flight search completed successfully.");
                    return bargainFinderMaxResponse;
                } else {
                    logger.error("Failed with HTTP error code: {}", statusCode);
                    throw new RuntimeException("Failed with HTTP error code: " + statusCode);
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while calling Bargain Finder Max API", e);
            throw new RuntimeException("Failed to call Bargain Finder Max API", e);
        }
    }

    private String getAccessToken() {
        // Implement token retrieval logic here
        logger.info("Fetching access token...");
        return "your_access_token"; // Replace with actual token retrieval logic
    }
}
