package com.example.demo.modelo;

import java.time.LocalDate;
import java.util.Date;

import com.example.demo.views.MovimientoView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="movimiento")
public class Movimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="nromovimiento")
	private int nroMovimiento;
	
	private LocalDate fecha;
	@Column(name="tipomovimiento")
	private String tipoMovimiento;
	@Column(name="monto")

	private float importe;
	
	/*
	@ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "nrocuenta") 
    private Cuenta cuenta;
	*/
	
	public Movimiento() {
		
	}
	
	public Movimiento(LocalDate fecha, String tipoMovimiento, float importe) {
		//this.nroMovimiento = numerador++;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;		
		this.importe = importe;
	}

	public int getNroMovimiento() {
		return this.nroMovimiento;
	}
	
	public boolean soyDeEseMes(int mes) {
		if (mes < 1 || mes > 12) {
	        throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
	    }
	    return this.fecha.getMonthValue() == mes;
	}
	
	public boolean soyDeposito() {
		return this.tipoMovimiento.equalsIgnoreCase("Deposito");
	}
	
	public float obtenerImporte() {
		return this.importe;
	}

	public MovimientoView toView() {
		return new MovimientoView(nroMovimiento,fecha, tipoMovimiento, importe);
	}

	@Override
	public String toString() {
		return "Movimiento [nroMovimiento=" + nroMovimiento + ", fecha=" + fecha + ", tipoMovimiento=" + tipoMovimiento
				+ ", importe=" + importe + "]";
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}
	
	
	
}
