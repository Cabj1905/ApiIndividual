package com.example.demo.controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ClienteDAO;
import com.example.demo.dao.CuentaDAO;
import com.example.demo.dao.MovimientoDAO;
import com.example.demo.dao.NumeracionDAO;
import com.example.demo.modelo.CajaAhorro;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.CuentaCorriente;
import com.example.demo.modelo.Movimiento;
import com.example.demo.modelo.Numeracion;
import java.util.random.*;


@RestController
public class CuentaControler {
	
	@Autowired
	private CuentaDAO cuentadao;
	
	@Autowired
	private NumeracionDAO numeracion;
	
	@Autowired
	private MovimientoDAO movidao;
	
	@Autowired
	private ClienteDAO clientedao;
	
	 @PostMapping("/cuentas/{doc}/CA")
	    public ResponseEntity<Cuenta>guardarcajaAhorro(@PathVariable String doc){
		 	Numeracion numero = numeracion.getbyID(1).get();
	        int x = numero.getCajaahorro() + 1;
	        
	        Cliente c=clientedao.buscarclienteDNI(doc);
	        
	        CajaAhorro newCA = new CajaAhorro(c);
	        c.agregarCuenta(newCA);
	        newCA.agregarClienteCuenta(c);
	        newCA.setNumero("CA" + x);
	        cuentadao.save(newCA);
	        clientedao.saveparacuenta(c);
	        numero.setCajaahorro(x);
	        numeracion.save(numero);
	        return new ResponseEntity<>(newCA, HttpStatus.CREATED);
	  }
	 
	 @PostMapping("/cuentas/{doc}/CC") //NO TOCAR
	 public ResponseEntity<CuentaCorriente>guardarCuentaCorriente(@PathVariable String doc, @RequestParam float descubiertoHabilitado, float costoMantenimiento, float tasaDiariaDescubierto ){
		 Numeracion numero = numeracion.getbyID(1).get();
	        int x = numero.getCuentacorriente() + 1;
	        Cliente c=clientedao.buscarclienteDNI(doc);
	        if(c==null) {
	        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        
	        CuentaCorriente newCA = new CuentaCorriente(c,descubiertoHabilitado,costoMantenimiento,tasaDiariaDescubierto);
	        c.agregarCuenta(newCA);
	        newCA.agregarClienteCuenta(c);
	        newCA.setNumero("CC" + x);
	        cuentadao.save(newCA);
	        clientedao.saveparacuenta(c);
	        numero.setCuentacorriente(x);
	        numeracion.save(numero);
	        return new ResponseEntity<>(newCA, HttpStatus.CREATED);
	 }
	 
	 @GetMapping("/cuentas/{nrocuenta}")
	 public ResponseEntity<Cuenta>obtenercuenta(@PathVariable String nrocuenta){
		 Optional<Cuenta> nuevacuenta=cuentadao.getbyID(nrocuenta);
		 return nuevacuenta.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
	    			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	 }
	 
	 @GetMapping("/cuentas")
	 public ResponseEntity<List<Cuenta>>obtenertodascuentas(){
		 List<Cuenta> cuentas=cuentadao.obtenertodaslascuentas();
		 return new ResponseEntity<>(cuentas,HttpStatus.OK);
	 }
	 
	 
	 @PostMapping("/cuentas/{nrocuenta}/depositar") //NO TOCAR
	 public ResponseEntity<Cuenta> depositar(@PathVariable("nrocuenta") String nrocuenta,@RequestParam float saldo) {
			Optional<Cuenta> cuenta = cuentadao.getbyID(nrocuenta);
			
			if(cuenta.isEmpty()) {
				return new ResponseEntity<Cuenta>(HttpStatus.NOT_FOUND);
			} 
			Cuenta finalcuenta=cuenta.get();
			finalcuenta.depositar(saldo);
			cuentadao.save(finalcuenta);
			
			return new ResponseEntity<Cuenta>(finalcuenta, HttpStatus.OK);
	 }
	 
	 @PostMapping("/cuentas/{nrocuenta}/extraer")
	 public ResponseEntity<Cuenta>extraer(@PathVariable String nrocuenta, @RequestParam float total){
		 Optional<Cuenta>cuenta=cuentadao.getbyID(nrocuenta);
		 
		 if(cuenta.isEmpty()) {
			 return new ResponseEntity<Cuenta>(HttpStatus.NOT_FOUND);
		 }
		 Cuenta finalcuenta=cuenta.get();
		
		 try {
			 finalcuenta.extraer(total);
			 cuentadao.save(finalcuenta);
		 } catch(Exception e) {
			 return new ResponseEntity<Cuenta>(HttpStatus.METHOD_NOT_ALLOWED);
		 }
		 
		 return new ResponseEntity<Cuenta>(finalcuenta, HttpStatus.OK);
	 }
	 
	 
	 @GetMapping("/depositosEntreFechas")
	 public ResponseEntity<List<Movimiento>> depositosEntreFechas(
	         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
	         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
		 
	     if (inicio == null || fin == null || inicio.isAfter(fin)) {
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }

	     List<Movimiento> movimientos = cuentadao.verDepositosEntreFechas(inicio, fin);	     
	     return new ResponseEntity<>(movimientos, HttpStatus.OK);
	 }
	 
	 @GetMapping("/extraccionesEntreFechas")
	 public ResponseEntity<List<Movimiento>>extraccionesentrefechas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
	 LocalDate inicio,  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
		 
		 if(inicio==null || fin==null || inicio.isAfter(fin)) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		 
		 List<Movimiento> movimientos=cuentadao.verExtraccionesEntreFechas(inicio, fin);
		return new ResponseEntity<>(movimientos, HttpStatus.OK);
		 
	 }
	 
	 @GetMapping("/movimientosmes")
	 public ResponseEntity<List<Movimiento>>movimientosmes(@RequestParam int mes){
		 
		 if(mes<=0 || mes>=12) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		 
		 List<Movimiento> movimientos=cuentadao.movimientosDelMes(mes);
		 return new ResponseEntity<>(movimientos, HttpStatus.OK);
	 }
	 
	 @PostMapping("/cuentas/{nro}/agregarcliente/{dni}")
	 public ResponseEntity<Void>agregarclientecuenta(@PathVariable String nro, String dni){
		 Optional<Cuenta> c = cuentadao.getbyID(nro);
		    
		    if (c.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		    }
		    
		    Cuenta nueva = c.get();
		    
		    
		    Cliente cli = clientedao.buscarclienteDNI(dni);
		    if (cli == null) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		    }
		    
		  
		    nueva.agregarClienteCuenta(cli);

		    
		    cuentadao.save(nueva);

		    return new ResponseEntity<>(HttpStatus.OK);
		
		
		 
	 }

	 
	 
}

