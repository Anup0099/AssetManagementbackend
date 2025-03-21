package com.hexaware.assetmanagement.dto;



public class RegisterRequest {
    private String email;
    private String password;
    private String role; // ADMIN or EMPLOYEE
    private String name;
    private String address;
    private String phoneNumber;
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterRequest(String email, String password, String role, String name, String address,
			String phoneNumber) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "RegisterRequest [email=" + email + ", password=" + password + ", role=" + role + ", name=" + name
				+ ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}

    // Getters and Setters
    
}
