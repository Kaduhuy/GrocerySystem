package com.example.grocery_system.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PixabayImageSearch {

    private static final String PIXABAY_API_KEY = "49502739-596c0a55f416654e702eb4614";

    public static String getPixabayImageUrl(String query) {
        // throw exception if query is empty or null
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Invalid null or empty query.");
        }

        try {
            // encode query for URL with API key
            String encodedQuery = java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
            String url = "https://pixabay.com/api/?key=" + PIXABAY_API_KEY + "&q=" + encodedQuery + "&image_type=photo&per_page=3";

            // create HTTP GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            // create HTTP request to send request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Pixabay API request failed with status code: " + response.statusCode());
            }

            // parses the first image to JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode firstHit = root.path("hits").get(0);

            // gets image URL from JSON response
            if (firstHit != null) {
                String imageUrl = firstHit.path("webformatURL").asText();
                if (imageUrl != null && imageUrl.startsWith("http")) {
                    return imageUrl;
                }
            }

            throw new RuntimeException("No valid image found in Pixabay results");

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch image from Pixabay", e);
        }
    }
}
