package com.example.demo.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Movimiento;

public interface MovimientoInterfaz extends JpaRepository<Movimiento, Integer>{

}
