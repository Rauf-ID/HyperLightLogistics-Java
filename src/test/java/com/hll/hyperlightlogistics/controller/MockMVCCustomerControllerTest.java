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
import com.hll.hyperlightlogistics.mapper.AddressMapper;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import com.hll.hyperlightlogistics.repository.CustomerRepository;
import com.hll.hyperlightlogistics.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class MockMVCCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AddressMapper addressMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testCreateCustomer() throws Exception {

        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("Name");

        when(customerService.createCustomer(Mockito.any())).thenReturn("Customer created successfully");

        mockMvc.perform(post("/api/customers/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer created successfully"));

    }

    @Test
    void testAddAddressToCustomer() throws Exception {

        AddressDTO addressRequest = new AddressDTO("USA", "California", "Los Angeles", "Main Street", "90001");

        mockMvc.perform(post("/api/customers/1/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Address added successfully"));

    }

    @Test
    void testAddAddressToCustomerFailure() throws Exception {

        AddressDTO addressRequest = new AddressDTO("USA", "California", "Los Angeles", "Main Street", "90001");

        doThrow(new RuntimeException("Customer not found")).when(customerService).addAddressToCustomer(1L, addressRequest);

        mockMvc.perform(post("/api/customers/1/addAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Customer not found"));

    }

    @Test
    void testGetAllAddresses() throws Exception {

        CustomerAddress address1 = new CustomerAddress();
        address1.setId(1L);
        address1.setCountry("USA");
        address1.setState("California");
        address1.setCity("Los Angeles");
        address1.setStreet("Main Street");
        address1.setPostcode("90001");

        CustomerAddress address2 = new CustomerAddress();
        address2.setId(2L);
        address2.setCountry("Canada");
        address2.setState("Ontario");
        address2.setCity("Toronto");
        address2.setStreet("Queen Street");
        address2.setPostcode("M5H");
        List<CustomerAddress> addressList = Arrays.asList(address1, address2);

        AddressDTO addressDto1 = new AddressDTO("USA", "California", "Los Angeles", "Main Street", "90001");
        AddressDTO addressDto2 = new AddressDTO("Canada", "Ontario", "Toronto", "Queen Street", "M5H");

        when(customerService.getAllAddresses(1L)).thenReturn(addressList);
        when(addressMapper.toDTO(address1)).thenReturn(addressDto1);
        when(addressMapper.toDTO(address2)).thenReturn(addressDto2);

        mockMvc.perform(get("/api/customers/getAllAddresses/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("USA"))
                .andExpect(jsonPath("$[0].state").value("California"))
                .andExpect(jsonPath("$[0].city").value("Los Angeles"))
                .andExpect(jsonPath("$[1].country").value("Canada"))
                .andExpect(jsonPath("$[1].state").value("Ontario"))
                .andExpect(jsonPath("$[1].city").value("Toronto"));

    }
}

