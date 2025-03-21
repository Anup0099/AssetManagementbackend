package com.hexaware.assetmanagement.role;

public enum Role {
	ADMIN,
    USER;

	public boolean equalsIgnoreCase(String string) {
        if (string == null) {
            return false; // Null safety check
        }
        return this.name().equalsIgnoreCase(string); // Compare ignoring case
    }

	public String toUpperCase() {
        return this.name(); // The name() method already returns the name in uppercase.
    }

}
