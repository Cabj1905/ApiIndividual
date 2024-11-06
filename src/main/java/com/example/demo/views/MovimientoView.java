package com.example.demo.views;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class MovimientoView implements Serializable{

	private static final long serialVersionUID = 139014362110072106L;
	private int nroMovimiento;
	private LocalDate fecha;
	private String tipoMovimiento;
	private float importe;
	
	public MovimientoView(int nroMovimiento, LocalDate fecha2, String tipoMovimiento, float importe) {
		this.nroMovimiento = nroMovimiento;
		this.fecha = fecha2;
		this.tipoMovimiento = tipoMovimiento;
		this.importe = importe;
	}

	public MovimientoView() {}

	public int getNroMovimiento() {
		return nroMovimiento;
	}

	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
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

}
