package com.example.demo.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exceptions.CuentaException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;

@Entity
@Table(name="cuenta")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
//@JsonInclude(JsonInclude.Include.ALWAYS)

public abstract class Cuenta {
	
	@Id
	@Column(name = "nrocuenta")

	protected String nroCuenta;
	
	@Column(name="saldo")
	protected float saldo;

	@JsonIgnoreProperties("cuentas")
	@ManyToMany(mappedBy = "cuentas", fetch=FetchType.EAGER)
	protected List<Cliente> clientes;
	
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	protected List<Movimiento> movimientos;

	
	public Cuenta(Cliente cliente) {
		this.nroCuenta = null;
		this.saldo = 0;
		this.clientes = new ArrayList<Cliente>();
		this.clientes.add(cliente);
		this.movimientos = new ArrayList<Movimiento>();
		
	}

	public void agregarClienteCuenta(Cliente cliente){
		 for (Cliente c : this.clientes) {
		        if (c.getDocumento().equals(cliente.getDocumento())) {
		            return;
		        }
		    }
		    this.clientes.add(cliente);
	}
	
	public int depositar(float importe) {
		if(importe > 0) {
			this.saldo += importe;
			LocalDate localdate=LocalDate.now();
			Movimiento movimiento = new Movimiento(localdate, "Deposito", importe);
			movimientos.add(movimiento);
			return movimiento.getNroMovimiento();
	}
		return 0;
	}
	
	public float obtenerSaldo() {
		return this.saldo;
	} 
	
	public abstract int extraer(float importe) throws CuentaException;
	
	public abstract float disponible();
	
	public List<Movimiento> verDepositosEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta){
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : movimientos)
			if(m.soyDeposito())
				resultado.add(m);
		return resultado;
	}
	
	public List<Movimiento> verExtraccionesEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta){
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : movimientos)
			if(!m.soyDeposito())
				resultado.add(m);
		return resultado;
	}
	
	public boolean soyLaCuenta(String nroCuenta) {
		return this.nroCuenta.equalsIgnoreCase(nroCuenta);
	}

	public List<Movimiento> movimientosDelMes(int mes) {
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : movimientos)
			if(m.soyDeEseMes(mes))
				resultado.add(m);
		return resultado;
	}

	public boolean tieneNumero(String nroCuenta) {
		return this.nroCuenta.equalsIgnoreCase(nroCuenta);
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public float getSaldo() {
		return saldo;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}
	
	
}
