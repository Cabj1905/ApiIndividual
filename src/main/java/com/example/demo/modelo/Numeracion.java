package com.example.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Numeracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cuentacorriente=0;

    private int cajaahorro=0;

    public Numeracion() {
    }

    public Numeracion(int id, int cuentacorriente, int cajaahorro) {
        this.id = id;
        this.cuentacorriente = cuentacorriente;
        this.cajaahorro = cajaahorro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuentacorriente() {
        return cuentacorriente;
    }

    public void setCuentacorriente(int cuentacorriente) {
        this.cuentacorriente = cuentacorriente;
    }

    public int getCajaahorro() {
        return cajaahorro;
    }

    public void setCajaahorro(int cajaahorro) {
        this.cajaahorro = cajaahorro;
    }
}

