/*
 * This file is part of HyperLightLogistics-Java.
 *
 * HyperLightLogistics-Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HyperLightLogistics-Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HyperLightLogistics-Java.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Vsevolod Batyrov
 */

package com.hll.hyperlightlogistics.service;

import com.hll.hyperlightlogistics.model.Inventory;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.model.Warehouse;
import com.hll.hyperlightlogistics.repository.InventoryRepository;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import com.hll.hyperlightlogistics.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public String addProductToInventory(Long warehouseId, Long productId, int quantity) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid warehouse ID"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        Inventory inventory = inventoryRepository.findByWarehouseAndProduct(warehouse, product);

        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + quantity);
        } else {
            inventory = new Inventory();
            inventory.setWarehouse(warehouse);
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
        }

        inventoryRepository.save(inventory);
        return "Product added to inventory successfully";
    }
}