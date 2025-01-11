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

package com.hll.hyperlightlogistics.serviceTests;

import com.hll.hyperlightlogistics.dto.AddressDTO;
import com.hll.hyperlightlogistics.dto.CustomerRequestDTO;
import com.hll.hyperlightlogistics.exceptionHandling.DatabaseException;
import com.hll.hyperlightlogistics.model.Customer;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import com.hll.hyperlightlogistics.repository.CustomerAddressesRepository;
import com.hll.hyperlightlogistics.repository.CustomerRepository;
import com.hll.hyperlightlogistics.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAddressesRepository addressesRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createCustomer_ShouldReturnSuccessMessage() {

        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("Customer name");
        customerRequest.setEmail("customermail");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName("Customer name");
        savedCustomer.setEmail("customermail");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        String response = customerService.createCustomer(customerRequest);

        assertEquals("Customer created with ID: 1", response);
        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void createCustomer_ShouldThrowDatabaseException_WhenErrorOccurs() {

        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("Customer name");
        customerRequest.setEmail("customermail");

        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Database error"));

        DatabaseException exception = assertThrows(DatabaseException.class, () ->
                customerService.createCustomer(customerRequest));

        assertEquals("Failed to add customer to the database", exception.getMessage());
        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void addAddressToCustomer_ShouldAddAddress() {

        Long customerId = 1L;
        AddressDTO addressRequest = new AddressDTO();
        addressRequest.setCountry("Country");
        addressRequest.setState("State");
        addressRequest.setCity("City");
        addressRequest.setStreet("Street");
        addressRequest.setPostcode("1");

        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        customerService.addAddressToCustomer(customerId, addressRequest);

        // Assert
        assertNotNull(customer.getCustomerAddresses());
        assertEquals(1, customer.getCustomerAddresses().size());
        CustomerAddress address = customer.getCustomerAddresses().get(0);
        assertEquals("Country", address.getCountry());
        assertEquals("State", address.getState());
        assertEquals("City", address.getCity());
        assertEquals("Street", address.getStreet());
        assertEquals("1", address.getPostcode());

        verify(customerRepository, times(1)).save(customer);

    }

    @Test
    void addAddressToCustomer_ShouldThrowRuntimeException_WhenCustomerNotFound() {

        Long customerId = 1L;
        AddressDTO addressRequest = new AddressDTO();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                customerService.addAddressToCustomer(customerId, addressRequest));

        assertEquals("Customer not found", exception.getMessage());

    }

    @Test
    void getAllAddresses_ShouldReturnAddressesList() {

        Long customerId = 1L;
        CustomerAddress address1 = new CustomerAddress();
        address1.setCountry("USA");
        CustomerAddress address2 = new CustomerAddress();
        address2.setCountry("Canada");

        when(addressesRepository.findByCustomerId(customerId)).thenReturn(List.of(address1, address2));

        List<CustomerAddress> addresses = customerService.getAllAddresses(customerId);

        assertNotNull(addresses);
        assertEquals(2, addresses.size());
        assertEquals("USA", addresses.get(0).getCountry());
        assertEquals("Canada", addresses.get(1).getCountry());

        verify(addressesRepository, times(1)).findByCustomerId(customerId);

    }

}