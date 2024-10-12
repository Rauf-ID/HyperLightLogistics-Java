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
import com.hll.hyperlightlogistics.model.Warehouse;
import com.hll.hyperlightlogistics.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public String createWarehouse(WarehouseRequestDTO warehouseRequest) {

        Warehouse warehouse = new Warehouse();
        warehouse.setLongitude(warehouseRequest.getLongitude());
        warehouse.setLatitude(warehouseRequest.getLatitude());
        warehouse.setType(warehouseRequest.getType());
        warehouse.setStatus(warehouseRequest.getStatus());

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return "Warehouse created with ID: " + savedWarehouse.getId();

    }

}
