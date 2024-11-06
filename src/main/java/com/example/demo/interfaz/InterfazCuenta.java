package com.example.demo.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Cuenta;

public interface InterfazCuenta extends JpaRepository<Cuenta, String> {

}
