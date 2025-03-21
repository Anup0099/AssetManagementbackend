package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

public class AssetRequestDTO {
    private Long requestId;
    private String assetName;
    private String userName;
    private LocalDate requestDate;
    

//    public AssetRequestDTO(Long requestId, String assetName, String userName, LocalDate requestDate) {
//        this.requestId = requestId;
//        this.assetName = assetName;
//        this.userName = userName;
//        this.requestDate = requestDate;
//    }

	public AssetRequestDTO(Long requestId, String assetName, String userName, LocalDate requestDate) {
		super();
		this.requestId = requestId;
		this.assetName = assetName;
		this.userName = userName;
		this.requestDate = requestDate;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}
    

    // Getters and setters
}

    

