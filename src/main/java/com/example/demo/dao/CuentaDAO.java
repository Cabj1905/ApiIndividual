package com.example.demo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import com.example.demo.interfaz.InterfazCA;
import com.example.demo.interfaz.InterfazClienteRepo;
import com.example.demo.interfaz.InterfazCuenta;
import com.example.demo.interfaz.InterfazNumeracion;
import com.example.demo.interfaz.MovimientoInterfaz;
import com.example.demo.modelo.CajaAhorro;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.CuentaCorriente;
import com.example.demo.modelo.Movimiento;
import com.example.demo.modelo.Numeracion;

import jakarta.transaction.Transactional;


@Repository
public class CuentaDAO {
	
	@Autowired
    private InterfazCuenta cuentarepo;	
	
	@Autowired
	private InterfazNumeracion numeracion;
	
	@Autowired
	private InterfazClienteRepo clienterepo;
	
	@Autowired
	private InterfazCA carepo;
	
	@Autowired
	private MovimientoInterfaz moviinter;
	 
	//obtener cuenta por ID
	 public Optional<Cuenta> getbyID(String n) {
		return cuentarepo.findById(n);
		 
	 }
	 
	 
	 //obtener todos las cuentas
	 public List<Cuenta> obtenertodaslascuentas() {
	       return cuentarepo.findAll();
    }
	 
	 @Transactional
	public void eliminarCuenta(String n) {
		 cuentarepo.deleteById(n);
	}
	


	
	@Transactional
	public void save(Cuenta c) {
		cuentarepo.save(c);
	}

	
	public Optional<CajaAhorro> getbyIDca(String n){
		return carepo.findById(n);
	}
	
	
	public List<Movimiento> verDepositosEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<Movimiento> resultado = new ArrayList<>();
        for (Movimiento m : moviinter.findAll()) {
            if (m.soyDeposito() && !m.getFecha().isBefore(fechaDesde) && !m.getFecha().isAfter(fechaHasta)) {
                resultado.add(m);
            }
        }
        return resultado;
    }
	
	public List<Movimiento> verExtraccionesEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta){
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : moviinter.findAll())
			if(!m.soyDeposito())
				resultado.add(m);
		return resultado;
	}
	
	public List<Movimiento> movimientosDelMes(int mes) {
		List<Movimiento> resultado = new ArrayList<Movimiento>();
		for(Movimiento m : moviinter.findAll())
			if(m.soyDeEseMes(mes))
				resultado.add(m);
		return resultado;
	}
	
	
	
	
}
