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

import com.hll.hyperlightlogistics.dto.AddressDTO;
import com.hll.hyperlightlogistics.dto.CustomerRequestDTO;
import com.hll.hyperlightlogistics.mapper.AddressMapper;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import com.hll.hyperlightlogistics.repository.CustomerRepository;
import com.hll.hyperlightlogistics.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressMapper addressMapper;

    @PostMapping("/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequestDTO customerRequest) {

        String response = customerService.createCustomer(customerRequest);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/getAllAddresses/{customerId}")
    public List<AddressDTO> getAllAddresses(@PathVariable Long customerId){

        List<CustomerAddress> addressList = customerService.getAllAddresses(customerId);
        return addressList.stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());

    }
}