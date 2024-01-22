package com.example.demo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.phonedao;
import com.example.demo.entity.phoneentry;

@Service
public class phoneserviceimp implements phoneservice{
    
    @Resource
    phonedao phonedao;
	
    @Override
	public List<phoneentry> findAll(){
		return phonedao.findAll();
	}
	@Override
	public ResponseEntity<String> insertentry(phoneentry pho) {
		return phonedao.insertentry(pho);
		
	}
	@Override
	public ResponseEntity<String> updateentry(phoneentry pho) {
		return phonedao.updateentry(pho);
		
	}
	@Override
	public void deleteentry(phoneentry pho) {
		phonedao.deleteentry(pho);
		
	}

}
