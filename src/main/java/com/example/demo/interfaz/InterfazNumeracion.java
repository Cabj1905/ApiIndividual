package com.example.demo.interfaz;

import com.example.demo.modelo.Numeracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterfazNumeracion extends JpaRepository<Numeracion, Integer> {
}
