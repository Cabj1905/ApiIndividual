package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.exceptions.CuentaException;
import com.example.demo.interfaz.InterfazClienteRepo;
import com.example.demo.interfaz.InterfazCuenta;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Cuenta;

import jakarta.transaction.Transactional;

@Repository
public class ClienteDAO {
	
	@Autowired
    private InterfazClienteRepo clienteRepository;
	
	@Autowired
	private InterfazCuenta	intercuenta;
	
	private boolean sabersiexiste(Cliente cli) {
		List<Cliente> clientes=this.obtenertodoslosClientes();
		
		for(Cliente c: clientes) {
			if(cli.getDocumento().equals(c.getDocumento())) {
				return true;
			}
		}
		
		return false;
		}

	//guardar cliente
	@Transactional
	public Cliente save(Cliente cli) {
	    if (!sabersiexiste(cli)) {
	        return clienteRepository.save(cli);
	    } else {
	        throw new IllegalArgumentException("El cliente con el documento " + cli.getDocumento() + " ya existe.");
	    }
	}
	
	@Transactional
	public Cliente saveparacuenta(Cliente cli) {
	    return clienteRepository.save(cli);
	}
	
	//HAY QUE CREAR UN METODO PARA QUE ACTULICE EL CLIENTE PERO QUE NO LO VUELVA A GUARDAR SI YA EXISTE
	//CAPAZ TIENE QUE VER CON EL REQUEST BODY
	 
	//obtener cliente por ID
	 public Optional<Cliente> getbyID(int n) {
		return clienteRepository.findById(n);
		 
	 }
	 
	 
	 //obtener todos los clientes
	 public List<Cliente> obtenertodoslosClientes() {
	       return clienteRepository.findAll();
    }
	 
	@Transactional
	public void eliminarCliente(int n) {
		 clienteRepository.deleteById(n);
	}
	
	public Cliente buscarclienteDNI(String dni) {
		List<Cliente>clientes=this.obtenertodoslosClientes();
		Cliente cliente;
		
		for(Cliente c: clientes) {
			if(c.getDocumento()!=null) {
				if(c.getDocumento().equals(dni)) {
					cliente=c;
					return cliente;
				} 
			}
			
		}
		
		return null;
	}
	
	public float saldoEnCuenta(String nroCuenta) throws CuentaException {
		List<Cuenta> cuentas=intercuenta.findAll();
		
		for(Cuenta c : cuentas)
			if(c.soyLaCuenta(nroCuenta))
				return c.obtenerSaldo();
	    throw new CuentaException("No existe esa cuenta para ese cliente");
		
	}
	
	public float posicion(String dni) {
		Cliente cli=this.buscarclienteDNI(dni);
		
		float resultado = 0;
		for(Cuenta c : cli.getCuentas()) {
			resultado += c.obtenerSaldo();
		}
		return resultado;
		
		
		}
			
	
	

}
