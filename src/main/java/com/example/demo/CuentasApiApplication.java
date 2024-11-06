package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.ClienteDAO;
import com.example.demo.dao.CuentaDAO;

import com.example.demo.interfaz.InterfazClienteRepo;
import com.example.demo.interfaz.InterfazCuenta;
import com.example.demo.interfaz.InterfazNumeracion;
import com.example.demo.modelo.CajaAhorro;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.CuentaCorriente;
import com.example.demo.modelo.Numeracion;



@SpringBootApplication
public class CuentasApiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CuentasApiApplication.class, args);
	}
	
	@Autowired
	InterfazClienteRepo icliente;
	
	@Autowired
	InterfazCuenta icuenta;
	
	@Autowired
	InterfazNumeracion inum;
	

	@Override
	public void run(String... args) throws Exception {
		
		
		/*
		Cliente c1=new Cliente("Maria Josefa","DNI 443214");
		icliente.save(c1);
		*/
		
		/*
		Optional <Cliente> c1=ClienteDAO.getinstance().getbyID(1, icliente);
		Cliente c12=c1.get();
		System.out.println(c12.toString());
		CajaAhorro ca=new CajaAhorro(c12,30000);
		CuentaDAO.getinstance().save(ca, icuenta);
		*/
		
		/*
		Optional <Cliente> c1=ClienteDAO.getinstance().getbyID(1, icliente);
		Cliente c12=c1.get();
		
		CuentaCorriente cc=new CuentaCorriente(c12, 19000, 2300, 1400);
		CuentaDAO.getinstance().save(cc, icuenta);
		*/
		

		/*
		Optional<Cuenta> c=CuentaDAO.getinstance().getByID("CC1", icuenta);
		Cuenta c1=c.get();
		
		c1.depositar(1000);
		
		System.out.println(c.toString());

		 */

		
		/*
		Cuenta cu=c.get();
		cu.depositar(1000);
		
		if(c.isPresent()) {
			System.out.println(c.toString());
		} else {
			System.out.println("No existe ese ID");
		}
		*/

		if(inum.findById(1).isEmpty()) {
			Numeracion numero = new Numeracion();
			inum.save(numero);
		}
		
		
	}

}
