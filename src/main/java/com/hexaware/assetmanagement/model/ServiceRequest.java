package com.hexaware.assetmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private String issueType; // Malfunction, Repair
    private String description;
    private String status; // Open, Resolved
	public ServiceRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceRequest(Long serviceId, User user, Asset asset, String issueType, String description, String status) {
		super();
		this.serviceId = serviceId;
		this.user = user;
		this.asset = asset;
		this.issueType = issueType;
		this.description = description;
		this.status = status;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
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
	@Override
	public String toString() {
		return "ServiceRequest [serviceId=" + serviceId + ", user=" + user + ", asset=" + asset + ", issueType="
				+ issueType + ", description=" + description + ", status=" + status + "]";
	}

    
}

