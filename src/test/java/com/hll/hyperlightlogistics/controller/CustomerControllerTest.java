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
import com.hll.hyperlightlogistics.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreateCustomer() {

        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        when(customerService.createCustomer(customerRequest)).thenReturn("Customer created");

        ResponseEntity<String> response = customerController.createCustomer(customerRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Customer created", response.getBody());
        verify(customerService, times(1)).createCustomer(customerRequest);

    }

    @Test
    void testAddAddressToCustomer_Success() {

        Long customerId = 1L;
        AddressDTO addressRequest = new AddressDTO();
        doNothing().when(customerService).addAddressToCustomer(customerId, addressRequest);

        ResponseEntity<String> response = customerController.addAddressToCustomer(customerId, addressRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Address added successfully", response.getBody());
        verify(customerService, times(1)).addAddressToCustomer(customerId, addressRequest);

    }

    @Test
    void testAddAddressToCustomer_Failure() {

        Long customerId = 1L;
        AddressDTO addressRequest = new AddressDTO();
        doThrow(new RuntimeException("Error adding address")).when(customerService).addAddressToCustomer(customerId, addressRequest);

        ResponseEntity<String> response = customerController.addAddressToCustomer(customerId, addressRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error adding address", response.getBody());
        verify(customerService, times(1)).addAddressToCustomer(customerId, addressRequest);

    }

    @Test
    void testGetAllAddresses() {

        Long customerId = 1L;
        CustomerAddress customerAddress = new CustomerAddress();
        AddressDTO addressDTO = new AddressDTO();

        when(customerService.getAllAddresses(customerId)).thenReturn(Collections.singletonList(customerAddress));
        when(addressMapper.toDTO(customerAddress)).thenReturn(addressDTO);

        ResponseEntity<List<AddressDTO>> response = customerController.getAllAddresses(customerId);
        List<AddressDTO> addresses = response.getBody();

        assertNotNull(addresses);
        assertEquals(1, addresses.size());
        verify(customerService, times(1)).getAllAddresses(customerId);
        verify(addressMapper, times(1)).toDTO(customerAddress);

    }
}
