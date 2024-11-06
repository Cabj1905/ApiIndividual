package com.example.demo.interfaz;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Cliente;


public interface InterfazClienteRepo extends JpaRepository<Cliente, Integer> {

}
