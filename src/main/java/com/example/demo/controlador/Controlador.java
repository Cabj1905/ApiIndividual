package com.example.demo.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ClienteDAO;
import com.example.demo.dao.CuentaDAO;
import com.example.demo.exceptions.CuentaException;
import com.example.demo.modelo.CajaAhorro;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.CuentaCorriente;
import com.example.demo.modelo.Movimiento;
import com.example.demo.views.MovimientoView;


@RestController
public class Controlador {

	private List<Cliente> clientes;
	private List<Cuenta> cuentas;
	
	
	@Autowired
	private ClienteDAO clientedao;
	
	@Autowired
	private CuentaDAO cuentadao;
	
	/*
	@GetMapping("/cliente")
	public ResponseEntity<List<Cliente>> obtenerclientes(){
		List<Cliente> clientes=clientedao.obtenertodoslosClientes();
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}
	*/
	
	public void crearCuenta(String documento, String tipoCuenta, String numerocuenta) {
		Cliente cliente = this.buscarCliente(documento);
		if(cliente != null)
			if(tipoCuenta.equalsIgnoreCase("Cuenta Corriente"))
				cuentas.add(new CuentaCorriente(cliente, 50000f, 1500f, 0.023f	));
			else
				cuentas.add(new CajaAhorro(cliente));
	}
	
	public void crearCliente(String nombre, String documento) {
		if(!existeCliente(documento)) {
			clientes.add(new Cliente(nombre, documento));
		}
	}

	public void agregarCuentaCliente(String documento, String nroCuenta) {
		Cliente cliente = this.buscarCliente(documento);
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		if(cliente != null && cuenta != null)
			cliente.agregarCuenta(cuenta);
	} 
	
	public int depositar(String nroCuenta, float importe) {
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		return cuenta.depositar(importe);
	}
	
	public int extraer(String nroCuenta, float importe) throws CuentaException {
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		return cuenta.extraer(importe);
	}
	
	public float disponibe(String nroCuenta) {
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		return cuenta.disponible();
	}
	
	public float posicion(int numero) {
		Cliente cliente = this.buscarCliente(numero);
		return cliente.posicion();
	} 
	
	public float saldoEnCuenta(String nroCuenta) {
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		return cuenta.obtenerSaldo();
	} 

	public List<MovimientoView> obtenerMovimientos(String nroCuenta, int mes){
		List<MovimientoView> resultado = new ArrayList<MovimientoView>();
		Cuenta cuenta = this.buscarCuenta(nroCuenta);
		List<Movimiento> movimientos = cuenta.movimientosDelMes(mes);
		for(Movimiento movimiento : movimientos)
			resultado.add(movimiento.toView());
		return resultado;
	}
	
	private boolean existeCliente(String documento) {
		return this.buscarCliente(documento) != null ;
	}
	
	private Cliente buscarCliente(String documento) {
		for(Cliente cliente : clientes)
			if(cliente.tieneDocumento(documento))
				return cliente;
		return null;
	}
	
	private Cliente buscarCliente(int numero) {
		for(Cliente cliente : clientes)
			if(cliente.tieneNumero(numero))
				return cliente;
		return null;
	}
	
	private Cuenta buscarCuenta(String nroCuenta) {
		for(Cuenta cuenta : cuentas)
			if(cuenta.tieneNumero(nroCuenta))
				return cuenta;
		return null;
	}
}
