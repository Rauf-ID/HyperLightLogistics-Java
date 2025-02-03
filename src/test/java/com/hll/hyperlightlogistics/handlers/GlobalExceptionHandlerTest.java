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

package com.hll.hyperlightlogistics.handlers;

import com.hll.hyperlightlogistics.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {

        globalExceptionHandler = new GlobalExceptionHandler();

    }

    @Test
    void handleDatabaseException_ShouldReturnInternalServerError() {

        DatabaseException exception = new DatabaseException("Database connection failed");

        ResponseEntity<String> response = globalExceptionHandler.handleDatabaseException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: Database connection failed", response.getBody());

    }

    @Test
    void handleGeneralException_ShouldReturnInternalServerError() {

        Exception exception = new Exception("Some general error");

        ResponseEntity<String> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());

    }
}
