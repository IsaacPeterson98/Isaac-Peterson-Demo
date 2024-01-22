package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.phoneentry;
import com.example.demo.service.phoneservice;

@RestController
@RequestMapping("/postgressApp")
@CrossOrigin(origins = "http://localhost:5173")
public class appController {
 
    @Resource
    phoneservice phoneservice;

    @GetMapping(value = "/phoneList")
    public List<phoneentry> getPhoneentries() {
        return phoneservice.findAll();
    }

    @PutMapping(value = "/insertentry")
    public ResponseEntity<String> insertentry(@RequestBody phoneentry pho) {
        return phoneservice.insertentry(pho);
    }

    @DeleteMapping(value = "/deleteentry")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteentry(@RequestBody phoneentry pho) {
        phoneservice.deleteentry(pho);
    }

    @PatchMapping(value = "/updateentry")
    public ResponseEntity<String> updateentry(@RequestBody phoneentry pho) {
       return phoneservice.updateentry(pho);
    }
}
