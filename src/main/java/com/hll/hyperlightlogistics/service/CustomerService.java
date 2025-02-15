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

import com.hll.hyperlightlogistics.dto.AddressDTO;
import com.hll.hyperlightlogistics.dto.CustomerRequestDTO;
import com.hll.hyperlightlogistics.exceptions.DatabaseException;
import com.hll.hyperlightlogistics.mapper.AddressMapper;
import com.hll.hyperlightlogistics.model.CustomerAddress;
import com.hll.hyperlightlogistics.model.Customer;
import com.hll.hyperlightlogistics.repository.CustomerAddressesRepository;
import com.hll.hyperlightlogistics.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerAddressesRepository addressesRepository;

    public String createCustomer(CustomerRequestDTO customerRequest) {

        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        try {
            Customer savedCustomer = customerRepository.save(customer);
            return "Customer created with ID: " + savedCustomer.getId();
        } catch (Exception e) {
            throw new DatabaseException("Failed to add customer to the database", e);

        }
    }

    @Transactional
    public void addAddressToCustomer(Long customerId, AddressDTO addressRequest) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerAddress newCustomerAddresses = AddressMapper.INSTANCE.toEntity(addressRequest);

        newCustomerAddresses.setCustomer(customer);
        List<CustomerAddress> customerAddresses = customer.getCustomerAddresses();
        customerAddresses.add(newCustomerAddresses);
        customer.setCustomerAddresses(customerAddresses);

        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new DatabaseException("Failed to add address to the customer", e);

        }

    }

    public List<CustomerAddress> getAllAddresses(Long customerId) {

        try {
            return addressesRepository.findByCustomerId(customerId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to fetch addresses for customer ID: " + customerId, e);

        }

    }
}
