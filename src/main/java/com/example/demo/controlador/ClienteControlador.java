package com.example.demo.controlador;

import com.example.demo.dao.ClienteDAO;
import com.example.demo.exceptions.CuentaException;
import com.example.demo.interfaz.InterfazClienteRepo;
import com.example.demo.modelo.Cliente;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteControlador {
    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/")
    public String mensaje() {
        return "Hola Mundo";
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> obtenerTodosClientes(){
        List<Cliente> clientes=clienteDAO.obtenertodoslosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente>obtenerClienteID(@PathVariable Integer id){
    	Optional<Cliente> cliente= clienteDAO.getbyID(id);
    	return cliente.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
    			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/clientes")
    public ResponseEntity<Cliente>guardar(@RequestBody Cliente cli){
    	Cliente nuevocliente=clienteDAO.save(cli);
    	return new ResponseEntity<Cliente>(nuevocliente,HttpStatus.OK);
    }
    
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void>eliminar(@PathVariable Integer id){
    	clienteDAO.eliminarCliente(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/clientes/{doc}/saldocuenta")
    public ResponseEntity<Float>saldoencuenta(@PathVariable String doc) throws CuentaException{
    	float saldo=clienteDAO.saldoEnCuenta(doc);
    	return new ResponseEntity<Float>(saldo,HttpStatus.OK);
    }
    
    @GetMapping("/clientes/{dni}/posicion")
    public ResponseEntity<Float>posicion(@PathVariable String dni){
    	float saldo=clienteDAO.posicion(dni);
    	return new ResponseEntity<Float>(saldo,HttpStatus.OK);
    }
}
