package com.hexaware.assetmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexaware.assetmanagement.role.AssetCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetId;

	private String assetName;
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private AssetCategory category; // Enum: ELECTRONICS, FURNITURE, VEHICLES

	private Double assetValue;

	private String status; // Available, Borrowed, In Service

	private String description;
	
	@Column(name="image_url")
	private String imageURlString;

	public Asset() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Asset(Long assetId, String assetName, AssetCategory category, Double assetValue, String status,
			String description,String imageURlString) {
		super();
		this.assetId = assetId;
		this.assetName = assetName;
		this.category = category;
		this.assetValue = assetValue;
		this.status = status;
		this.description = description;
		this.imageURlString=imageURlString;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

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

	public Double getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(Double assetValue) {
		this.assetValue = assetValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURlString() {
		return imageURlString;
	}

	public void setImageURlString(String imageURlString) {
		this.imageURlString = imageURlString;
	}

	@Override
	public String toString() {
		return "Asset [assetId=" + assetId + ", assetName=" + assetName + ", category=" + category + ", assetValue="
				+ assetValue + ", status=" + status + ", description=" + description + ", imageURlString="
				+ imageURlString + "]";
	}

	
	
	

}
