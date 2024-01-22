package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.phoneentry;

public interface phoneservice  {
    List<phoneentry> findAll();

    ResponseEntity<String> insertentry(phoneentry pho);
    
    ResponseEntity<String> updateentry(phoneentry pho);

    void deleteentry(phoneentry pho);
}
