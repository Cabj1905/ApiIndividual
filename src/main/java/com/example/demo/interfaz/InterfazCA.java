package com.example.demo.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.CajaAhorro;

public interface InterfazCA extends JpaRepository<CajaAhorro, String> {

}
