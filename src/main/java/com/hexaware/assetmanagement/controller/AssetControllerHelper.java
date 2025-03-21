package com.hexaware.assetmanagement.controller;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.assetmanagement.model.Asset;

import io.jsonwebtoken.io.IOException;

@Component
public class AssetControllerHelper {

    private final ObjectMapper objectMapper;

    public AssetControllerHelper() {
        this.objectMapper = new ObjectMapper(); // Create an ObjectMapper instance
    }

    public Asset convertJsonToAsset(String assetJson) throws IOException, JsonMappingException, JsonProcessingException {
        return objectMapper.readValue(assetJson, Asset.class); // Convert JSON string to Asset object
    }
}
