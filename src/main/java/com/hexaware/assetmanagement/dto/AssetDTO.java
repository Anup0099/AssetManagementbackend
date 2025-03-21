package com.hexaware.assetmanagement.dto;

import com.hexaware.assetmanagement.role.AssetCategory;

public class AssetDTO {

    private String assetName;
    private AssetCategory category;
    private String description;
    private String status;

    // Getters and Setters
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
