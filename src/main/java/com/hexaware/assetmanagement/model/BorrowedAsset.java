package com.hexaware.assetmanagement.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrowed_assets")
public class BorrowedAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Asset asset;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    @Column(name = "status")
    private String status; // Pending, Approved, Returned

	public BorrowedAsset() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BorrowedAsset(Long borrowId, User user, Asset asset, LocalDate borrowDate, LocalDate returnDate,
			String status) {
		super();
		this.borrowId = borrowId;
		this.user = user;
		this.asset = asset;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.status = status;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
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

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BorrowedAsset [borrowId=" + borrowId + ", user=" + user + ", asset=" + asset + ", borrowDate="
				+ borrowDate + ", returnDate=" + returnDate + ", status=" + status + "]";
	}
    

    
}
