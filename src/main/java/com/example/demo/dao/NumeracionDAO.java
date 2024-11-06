package com.example.demo.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.interfaz.InterfazNumeracion;
import com.example.demo.modelo.Numeracion;

@Repository
public class NumeracionDAO {
	
	@Autowired
    private InterfazNumeracion numeracionRepo;
	
	public Optional<Numeracion> getbyID(int n) {
		return numeracionRepo.findById(n); 
	 }
	
	public void save(Numeracion numero) {
		 numeracionRepo.save(numero);
	}

}
