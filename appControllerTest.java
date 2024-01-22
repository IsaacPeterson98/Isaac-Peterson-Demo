package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.demo.dao.phonedaoimp;
import com.example.demo.entity.phoneentry;
import com.example.demo.mapper.PhoneRowMapper;

@SpringBootTest
public class appControllerTest {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private phonedaoimp phoneDao;

    @BeforeEach
    void setUp() {
        // Initialize the NamedParameterJdbcTemplate and phonedaoimp instances
        jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        phoneDao = new phonedaoimp(jdbcTemplate);
    }

    @Test
    void testFindAll() {
        // Mocking the behavior of the jdbcTemplate.query method
        List<phoneentry> expectedEntries = Collections.emptyList();
        
        when(jdbcTemplate.query(anyString(), anyMap(), any(PhoneRowMapper.class)))
            .thenReturn(expectedEntries);

        // Performing the test
        List<phoneentry> actualEntries = phoneDao.findAll();

        // Verifying the result
        assertEquals(expectedEntries, actualEntries);
    }

    @Test
void testInsertEntry_Success() {
    // Mocking the successful insertion
    when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class)))
        .thenReturn(1);

    // Creating a sample phoneentry for insertion
    phoneentry entry = new phoneentry("John", "D", "M", "1234567890", "Address", "john@example.com", "12345");

    // Performing the test
    HttpStatus result = phoneDao.insertentry(entry).getStatusCode();

    // Verifying the result
    assertEquals(HttpStatus.CREATED, result);
}

    @Test
    void testInsertEntry_Failure_InvalidFirstName() {
        // Creating a sample phoneentry with invalid first name
        phoneentry entry = new phoneentry("", "D", "M", "1234567890", "Address", "john@example.com", "12345");

        // Performing the test
        HttpStatus result = phoneDao.insertentry(entry).getStatusCode();

        // Verifying the result
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result);
    }

    @Test
    void testUpdateEntry_Success() {
        // Mocking the successful update
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class)))
            .thenReturn(1);

        // Creating a sample phoneentry for updating
        phoneentry entry = new phoneentry("John", "D", "M", "1234567890", "Address", "john@example.com", "12345");

        // Performing the test
        HttpStatus result = phoneDao.updateentry(entry).getStatusCode();

        // Verifying the result
        assertEquals(HttpStatus.OK, result);
    }

    @Test
    void testUpdateEntry_Failure() {
        // Mocking a failed update
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class)))
            .thenReturn(0);

        // Creating a sample phoneentry for updating
        phoneentry entry = new phoneentry("John", "Doe", "M", "1234567890", "Address", "john@example.com", "12345");

        // Performing the test
        HttpStatus result = phoneDao.updateentry(entry).getStatusCode();

        // Verifying the result
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result);
    }

   
}
