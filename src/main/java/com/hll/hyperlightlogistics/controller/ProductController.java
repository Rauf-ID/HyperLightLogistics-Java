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
import com.hll.hyperlightlogistics.dto.AddressDTO;
import com.hll.hyperlightlogistics.dto.CustomerRequestDTO;
import com.hll.hyperlightlogistics.dto.ProductRequestDTO;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import com.hll.hyperlightlogistics.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequestDTO productRequest) {

        String response = productService.createProduct(productRequest);

        return ResponseEntity.ok(response);

    }

}

