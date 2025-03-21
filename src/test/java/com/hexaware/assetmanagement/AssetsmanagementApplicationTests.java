//package com.hexaware.assetmanagement;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.longThat;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.hexaware.assetmanagement.model.Asset;
//import com.hexaware.assetmanagement.service.IAssetService;
//
//@SpringBootTest
//class AssetsmanagementApplicationTests {
//
//	@Autowired
//	private IAssetService assetService;
//
//	@Test
//	void a_addAssetTest() {
//		// Create a new asset
//		Asset asset = new Asset(null, "Laptop", null, null, "Dell Inspiron 15", null);
//		Asset savedAsset = assetService.addAsset(asset);
//
//		// Check if the asset is saved and not null
//		assertNotNull(savedAsset);
//		assertNotNull(savedAsset.getAssetId());
//		System.out.println("Created Asset ID: " + savedAsset.getAssetId() + " Name: " + savedAsset.getAssetName());
//	}
//
//	@Test
//	void b_findAssetByIdTest() {
//		// Add a sample asset
//		Asset asset = assetService.addAsset(new Asset(null, "Laptop", null, null, "Dell Inspiron 15", null));
//
//		// Find the asset by ID
//		Asset foundAsset = assetService.findAssetById(asset.getAssetId());
//
//		// Assert that the found asset matches the saved asset
//		assertNotNull(foundAsset);
//		assertEquals(asset.getAssetId(), foundAsset.getAssetId());
//		assertEquals("Laptop", foundAsset.getAssetName());
//	}
//
//	@Test
//	void c_getAllAssetsTest() {
//		// Add a sample asset
//		List<Asset> assetList = assetService.getAllAssets();
//		long size = assetList.size();
//		Asset asset1 = assetService.addAsset(new Asset(null, "Laptop", null, null, "Dell Inspiron 15", null));
//		Asset asset2 = assetService.addAsset(new Asset(null, "Tablet", null, null, "Samsung Galaxy Tab", null));
//
//		// Retrieve all assets
//
//		// Assert that there are at least two assets in the list
//		assertNotNull(assetList);
//		assertEquals(size, assetList.size());
//	}
//
//	@Test
//	void d_updateAssetStatusTest() {
//		// Add a sample asset
//		Asset asset = assetService.addAsset(new Asset(null, "Laptop", null, null, "Dell Inspiron 15", null));
//
//		// Update asset status
//		assetService.updateAssetStatus(asset.getAssetId(), "Active");
//
//		// Retrieve the asset again and check status (assuming status is part of Asset)
//		Asset updatedAsset = assetService.findAssetById(asset.getAssetId());
//
//		// Assert that the status was updated
//		assertEquals("Active", updatedAsset.getStatus());
//	}
//
////    @Test
////    void e_deleteAssetTest() {
////        // Add a sample asset
////        Asset asset = assetService.addAsset(new Asset(null, "Laptop", null, null, "Dell Inspiron 15", null));
////        
////        // Delete the asset by ID
////        assetService.deleteAsset(asset.getAssetId());
////        
////        // Check if the asset exists in the database (should return null or throw an exception)
////        Asset deletedAsset = assetService.findAssetById(asset.getAssetId());
////        
////        // Assert that the asset is not found
////        assertEquals(null, deletedAsset);
////    }
//
////    @Test
////    void f_findAssetNotFoundTest() {
////        // Try to find an asset that doesn't exist
////        Asset asset = assetService.findAssetById(9999L);
////        
////        // Assert that the asset is null since it doesn't exist
////        assertEquals(null, asset);
////    }
//
////    @Test
////    void g_deleteAssetFailureTest() {
////        // Try to delete an asset that doesn't exist
////        try {
////            assetService.deleteAsset(9999L); // Asset ID 9999 doesn't exist
////        } catch (Exception e) {
////            assertEquals("Asset not found", e.getMessage());
////        }
////    }
//
////    @Test
////    void i_deleteAllAssetsTest() {
////        // Delete all assets
////        assetService.deleteAsset(null);
////        
////        // Verify that all assets are deleted (assuming count is 0 after delete)
////        long assetCountAfterDelete = 0;
////        
////        // Assert that the count of assets is 0
////        assertEquals(0, assetCountAfterDelete);
////    }
//
//}
