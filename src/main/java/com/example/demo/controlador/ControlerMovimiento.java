package com.example.demo.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.MovimientoDAO;
import com.example.demo.modelo.Movimiento;

@RestController
public class ControlerMovimiento {

	@Autowired
	private MovimientoDAO movidao;
	
	@GetMapping("/movimientos")
	public ResponseEntity<List<Movimiento>>obtenermovimientos(){
		List<Movimiento>movimientos=movidao.obtenertodos();
		return new ResponseEntity<>(movimientos,HttpStatus.OK);
	}
	
	@GetMapping("/movimientos/{numero}")
	public ResponseEntity<Movimiento>getbyid(@PathVariable Integer numero){
		Optional<Movimiento>movi=movidao.getById(numero);
		return movi.map(c-> new ResponseEntity<>(c, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	
}
