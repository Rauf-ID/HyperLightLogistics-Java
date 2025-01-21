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

import com.hll.hyperlightlogistics.dto.WarehouseRequestDTO;
import com.hll.hyperlightlogistics.exceptions.DatabaseException;
import com.hll.hyperlightlogistics.model.Warehouse;
import com.hll.hyperlightlogistics.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createWarehouse_ShouldReturnSuccessMessage_WhenWarehouseIsSaved() {

        WarehouseRequestDTO warehouseRequest = new WarehouseRequestDTO();
        warehouseRequest.setLongitude(BigDecimal.valueOf(45.0));
        warehouseRequest.setLatitude(BigDecimal.valueOf(90.0));
        warehouseRequest.setType("Storage");
        warehouseRequest.setStatus("Active");

        Warehouse savedWarehouse = new Warehouse();
        savedWarehouse.setId(1L);

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(savedWarehouse);

        String result = warehouseService.createWarehouse(warehouseRequest);

        assertEquals("Warehouse created with ID: 1", result);
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));

    }

    @Test
    void createWarehouse_ShouldThrowDatabaseException_WhenSavingFails() {

        WarehouseRequestDTO warehouseRequest = new WarehouseRequestDTO();
        warehouseRequest.setLongitude(BigDecimal.valueOf(45.0));
        warehouseRequest.setLatitude(BigDecimal.valueOf(90.0));
        warehouseRequest.setType("Storage");
        warehouseRequest.setStatus("Active");

        when(warehouseRepository.save(any(Warehouse.class))).thenThrow(new RuntimeException("Database error"));

        DatabaseException exception = assertThrows(DatabaseException.class, () -> warehouseService.createWarehouse(warehouseRequest));
        assertEquals("Failed to add warehouse to the database", exception.getMessage());
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));

    }
}
