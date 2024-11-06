package com.example.demo.modelo;

import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exceptions.CuentaException;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;


@Entity
@Table(name="cuenta")


@DiscriminatorValue("cuenta corriente")
//@JsonInclude(JsonInclude.Include.ALWAYS)

public class CuentaCorriente extends Cuenta {

	@Column(name="descubiertohabilitado")
	private float descubiertoHabilitado;
	@Column(name="costomantenimiento")
	private float costoMantenimiento;
	
	@Column(name="tasadiariodescubierto")
	private float tasaDiariaDescubierto;
	
	
	public CuentaCorriente() {
		super(null);
	}
	
	public CuentaCorriente(Cliente cliente,Float descubiertoHabilitado, Float costoMantenimiento, Float tasaDiariaDescubierto) {
		super(cliente);
		this.descubiertoHabilitado = descubiertoHabilitado;
		this.costoMantenimiento = costoMantenimiento;
		this.tasaDiariaDescubierto = tasaDiariaDescubierto;
	}

	@Override
	public int extraer(float importe) throws CuentaException {
		if(this.saldo + this.descubiertoHabilitado > importe) {
			this.saldo -= importe;
			Movimiento movimiento = new Movimiento(LocalDate.now(), "Extraccion", importe);
			this.movimientos.add(movimiento);
			return movimiento.getNroMovimiento();
		}
		else
			throw new CuentaException("El saldo es insuficiente");
	}

	@Override
	public float disponible() {
		return this.saldo + this.descubiertoHabilitado;
	}

	public float getDescubiertoHabilitado() {
		return descubiertoHabilitado;
	}

	public float getCostoMantenimiento() {
		return costoMantenimiento;
	}

	public float getTasaDiariaDescubierto() {
		return tasaDiariaDescubierto;
	}
	
	@Override
	public String toString() {
		return "CajaAhorro" +"[" + "Nro cuenta: " +this.nroCuenta + ",saldo: " +this.saldo + ",descubierto habilitado: "+this.descubiertoHabilitado+",costo mantenimiento: "+this.costoMantenimiento+",clientes: "
				+"tasa descubierto: " + this.tasaDiariaDescubierto+ ", clientes: "+this.clientes+" movimientos: "+ this.movimientos +"]";
		
	}
	
	public void setNumero(String n) {
		this.nroCuenta=n;
	}


	public void setDescubiertoHabilitado(Float descubiertoHabilitado) {
		this.descubiertoHabilitado = descubiertoHabilitado;
	}

	public void setCostoMantenimiento(Float costoMantenimiento) {
		this.costoMantenimiento = costoMantenimiento;
	}

	public void setTasaDiariaDescubierto(Float tasaDiariaDescubierto) {
		this.tasaDiariaDescubierto = tasaDiariaDescubierto;
	}
	
	public void setCliente(Cliente c) {
		this.clientes.add(c);
	}
	
	
	

}
