package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.interfaz.MovimientoInterfaz;
import com.example.demo.modelo.Movimiento;

@Repository
public class MovimientoDAO {
	
	@Autowired
	private MovimientoInterfaz moviinterfaz;
	
	public List<Movimiento>obtenertodos(){
		return moviinterfaz.findAll();
	}
	
	public Optional<Movimiento>getById(Integer n){
		return moviinterfaz.findById(n);
	}
	
	public void delete(Integer n) {
		moviinterfaz.deleteById(n);
	}
	
	
		
	}
	


