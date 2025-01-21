package com.hll.hyperlightlogistics.service;

import com.hll.hyperlightlogistics.model.Inventory;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.model.Warehouse;
import com.hll.hyperlightlogistics.repository.InventoryRepository;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import com.hll.hyperlightlogistics.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void addProductToInventory_ShouldAddNewProduct_WhenNotPresentInInventory() {

        Long warehouseId = 1L;
        Long productId = 1L;
        int quantity = 1;

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        Product product = new Product();
        product.setId(productId);

        when(warehouseRepository.findById(warehouseId)).thenReturn(java.util.Optional.of(warehouse));
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        when(inventoryRepository.findByWarehouseAndProduct(warehouse, product)).thenReturn(null);

        Inventory savedInventory = new Inventory();
        savedInventory.setWarehouse(warehouse);
        savedInventory.setProduct(product);
        savedInventory.setQuantity(quantity);

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);

        String response = inventoryService.addProductToInventory(warehouseId, productId, quantity);

        assertEquals("Product added to inventory successfully", response);
        verify(warehouseRepository, times(1)).findById(warehouseId);
        verify(productRepository, times(1)).findById(productId);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));

    }

    @Test
    void addProductToInventory_ShouldUpdateQuantity_WhenProductAlreadyInInventory() {

        Long warehouseId = 1L;
        Long productId = 1L;
        int quantity = 10;

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        Product product = new Product();
        product.setId(productId);

        Inventory existingInventory = new Inventory();
        existingInventory.setWarehouse(warehouse);
        existingInventory.setProduct(product);
        existingInventory.setQuantity(5);

        when(warehouseRepository.findById(warehouseId)).thenReturn(java.util.Optional.of(warehouse));
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        when(inventoryRepository.findByWarehouseAndProduct(warehouse, product)).thenReturn(existingInventory);

        when(inventoryRepository.save(existingInventory)).thenReturn(existingInventory);

        String response = inventoryService.addProductToInventory(warehouseId, productId, quantity);

        assertEquals("Product added to inventory successfully", response);
        assertEquals(15, existingInventory.getQuantity());
        verify(inventoryRepository, times(1)).save(existingInventory);

    }
}
