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

package com.hll.hyperlightlogistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hll.hyperlightlogistics.dto.ProductRequestDTO;
import com.hll.hyperlightlogistics.dto.WarehouseRequestDTO;
import com.hll.hyperlightlogistics.model.Warehouse;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import com.hll.hyperlightlogistics.repository.WarehouseRepository;
import com.hll.hyperlightlogistics.service.ProductService;
import com.hll.hyperlightlogistics.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/createWarehouse")
    public ResponseEntity<String> createWarehouse(@RequestBody WarehouseRequestDTO warehouseRequest) {

        String response = warehouseService.createWarehouse(warehouseRequest);

        return ResponseEntity.ok(response);

    }
}

