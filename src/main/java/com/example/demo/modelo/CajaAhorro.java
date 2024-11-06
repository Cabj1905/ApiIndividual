package com.example.demo.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exceptions.CuentaException;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name="cuenta")
@DiscriminatorValue("caja de ahorro")
//@JsonInclude(JsonInclude.Include.ALWAYS)

public class CajaAhorro extends Cuenta {
	
	@Column(name="tasainteres")
	private float tasainteres;
	

	


	public CajaAhorro() {
		super(null);
	}
	
	
	
	public CajaAhorro(Cliente cliente) {
		super(cliente);
		this.tasainteres = 1.8f;
		this.clientes=new ArrayList<Cliente>();
		this.movimientos=new ArrayList<Movimiento>();
	}

	@Override
	public int extraer(float importe) throws CuentaException {
		if(this.saldo > importe) {
			this.saldo -= importe;
			Movimiento movimiento = new Movimiento(LocalDate.now(), "Extraccion", importe);
			this.movimientos.add(movimiento);
			//MovimientoDAO.getinstance().save(movimiento);
			return movimiento.getNroMovimiento();
		}
		else
			throw new CuentaException("Saldo Insuficiente");
	}

	@Override
	public float disponible() {
		return this.saldo;
	}

	public float getTasaInteres() {
		return tasainteres;
	}

	@Override
	public String toString() {
		return "CajaAhorro" +"[" + "Nro cuenta: " +this.nroCuenta + ",saldo: " +this.saldo + ",tasaInteres: "+this.tasainteres+",clientes: "+this.clientes+" movimientos: "+ this.movimientos +"]";
	}
	
	
	public void setNumero(String numero) {
		this.nroCuenta=numero;
	}


	public void setTasainteres(float tasainteres) {
		this.tasainteres = tasainteres;
	} 
	
	

}
