package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.exceptions.CuentaException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="numero")
	private int numero;
	private String nombre;
	private String documento;
	
	@JsonIgnoreProperties("cliente")
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "clientecuenta",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
        )
	
	private List<Cuenta> cuentas;
	
	public Cliente() {
		
	}
	
	public Cliente(String nombre, String documento) {
		//this.numero = numerador++;
		this.nombre = nombre;
		this.documento = documento;
		this.cuentas = new ArrayList<Cuenta>();
	}

	public float saldoEnCuenta(String nroCuenta) throws CuentaException {
		for(Cuenta c : cuentas)
			if(c.soyLaCuenta(nroCuenta))
				return c.obtenerSaldo();
	    throw new CuentaException("No existe esa cuenta para ese cliente");
	}
	
	public float posicion() {
		float resultado = 0;
		for(Cuenta c : cuentas)
			resultado += c.obtenerSaldo();
		return resultado;
	}
	
	public List<Movimiento> movimientosMes(int mes, String nroCuenta) throws CuentaException{
		for(Cuenta c : cuentas)
			if(c.soyLaCuenta(nroCuenta)) {
				return c.movimientosDelMes(mes);
			}
		throw new CuentaException("No existe esa cuenta para ese cliente");
	}
	
	public int getNumero() {
		return numero;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDocumento() {
		return documento;
	}
	
	public boolean tieneDocumento(String documento) {
		return this.documento.equalsIgnoreCase(documento);
	}

	public boolean tieneNumero(int numero) {
		return this.numero == numero;
	}

	public void agregarCuenta(Cuenta cuenta) {
		if (this.cuentas == null) {
            this.cuentas = new ArrayList<>();
        }
		cuentas.add(cuenta);
		cuenta.agregarClienteCuenta(this);
	}

	@Override
	public String toString() {
		return "Cliente [numero=" + numero + ", nombre=" + nombre + ", documento=" + documento + ", cuentas=" + cuentas.toString()
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cuentas, documento, nombre, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cuentas, other.cuentas) && Objects.equals(documento, other.documento)
				&& Objects.equals(nombre, other.nombre) && numero == other.numero;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	
	
	
	
	
}
