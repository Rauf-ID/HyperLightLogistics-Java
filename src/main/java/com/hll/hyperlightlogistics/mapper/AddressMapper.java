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

package com.hll.hyperlightlogistics.mapper;

import com.hll.hyperlightlogistics.dto.AddressDTO;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDTO toDTO(CustomerAddress address){
        return new AddressDTO(
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostcode()
        );
    }
}
