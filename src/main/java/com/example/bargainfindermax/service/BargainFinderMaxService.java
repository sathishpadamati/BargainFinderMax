package com.example.bargainfindermax.service;

import com.example.bargainfindermax.dto.BargainFinderMaxRequest;
import com.example.bargainfindermax.dto.BargainFinderMaxResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Map;

@Service
@Data
public class BargainFinderMaxService {

    private static final Logger logger = LogManager.getLogger(BargainFinderMaxService.class);
    private final WebClient webClient;
    private static final String SABRE_URL = "https://api.cert.platform.sabre.com/v2/auth/token";
    private static final String CLIENT_ID = "V1:vg1lstdtxatfbq86:DEVCENTER:EXT";
    private static final String CLIENT_SECRET = "Plx8LjY2";


    @Autowired
    public BargainFinderMaxService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(SABRE_URL).build();
    }


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
            postRequest.setHeader("Authorization", "Bearer " + getAuthToken());
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

    public String getAccessToken() {
        // Implement token retrieval logic here
        logger.info("Fetching access token...");
        return "your_access_token"; // Replace with actual token retrieval logic
    }

    public String getAuthToken() {
        String encodedCredentials = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        return webClient.post()
                .uri("")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + "VmpFNmRtY3hiSE4wWkhSNFlYUm1ZbkU0TmpwRVJWWkRSVTVVUlZJNlJWaFU6VUd4NE9FeHFXVEk9")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")) // ✅ Use form-data
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> "Bearer " + response.get("access_token"))
                .block(); // Blocking for simplicity
    }
}
