package com.soam.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soam.springboot.backend.apirest.models.entity.Cliente;
import com.soam.springboot.backend.apirest.models.entity.Region;
import com.soam.springboot.backend.apirest.models.services.IClienteService;
import com.soam.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	private final Logger LOG  = LoggerFactory.getLogger(ClienteRestController.class);
	
	@Autowired
	private IClienteService  clienteService; //va buscar un primer candidato tipo de la interfaz
	
	@Autowired
	private IUploadFileService uploadService;

	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		LOG.info("ini -- index()");
		LOG.info("fin -- index()");
		return clienteService.findAll();
		
		
	}
	
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		LOG.info("ini -- indexConPaginador()");
		Pageable pageable = PageRequest.of(page, 4);
		LOG.info("fin -- indexConPaginador()");
		return clienteService.findAll(pageable);
	}	
	
	
	@Secured({"ROLE_ADMIN"}) 
	@GetMapping("/clientes/regiones")
	public List<Region> listaRegiones(){
		return clienteService.findAllRegiones();
	}
	

	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"}) //Aqui si debemos colocar el prefijo
	@GetMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK)//Este es por defecto el 200
	public ResponseEntity<?> show(@PathVariable Long id) { //? porque puede ser un generic, cualquier tipo de objeto		
		LOG.info("ini -- show()");
		Cliente cliente = null;
		Map<String, Object> response =  new HashMap<>();//para no encontrado
		try {
			 cliente =  clienteService.findById(id); 		//para errores de SQL, conexiones, sintaxis , etc
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al ejecutar consulta  la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);//ERROR 500
		}

		if(cliente  ==  null) {
			response.put("mensaje", "El cliente con el ID ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//ERROR 404
		}
		LOG.info("fin -- show()");
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK); //ERROR 200
	}
	
	@Secured({"ROLE_ADMIN"}) 
	@PostMapping("/clientes")
	//@ResponseStatus(HttpStatus.CREATED) la quitamos de aqui porque puede varias dependiendo de las circustancias
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		LOG.info("ini -- create()");
		Cliente clienteGuardado = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(result.hasErrors()) {
			/**Usando Stream del JDK 1.8*/
			List<String> errors =  result.getFieldErrors()
								 .stream()
								 .map(err -> "El campo '" +err.getField()+ "' "+ err.getDefaultMessage())
								 .collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			 clienteGuardado =  clienteService.save(cliente);
		} catch (DataAccessException e) {
			 response.put("mensaje", "Error al intentar hacer el insert en la base de datos");
			 response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente se ha creado con éxito");
		response.put("cliente", clienteGuardado);
		LOG.info("fin -- create()");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@Secured({"ROLE_ADMIN"}) 
	@PutMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {	
		LOG.info("ini -- update()");
		Cliente clienteActual =  clienteService.findById(id);
		Cliente clienteActualizado  = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if(result.hasErrors()) {
			List<String> errors =  result.getFieldErrors()
								 .stream()
								 .map(err -> "El campo '" +err.getField()+ "' "+ err.getDefaultMessage())
								 .collect(Collectors.toList());						
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		
		}		
		if(clienteActual == null) {
			response.put("mensaje",  "No se puedo editar, el cliente con el ID ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			clienteActual  =  clienteService.findById(id);
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setRegion(cliente.getRegion());
			
			clienteActualizado  = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			 response.put("mensaje", "Error al actualizar en la base de datos");
			 response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		//Internamente hace un merge el Save ... si el ID es null, hace insert
		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("cliente", clienteActualizado);
		LOG.info("fin -- update()");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
	}
	
	@Secured({"ROLE_ADMIN"}) 
	@DeleteMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		LOG.info("ini -- delete()");
		 Map<String, Object> response = new HashMap<String, Object>();
		 try {
			Cliente cliente =  clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);	
			clienteService.delete(id); //Validara por debajo que el cliente exista
		 }catch(DataAccessException e) {
			 response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			 response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 response.put("mensaje", "Se ha eliminado el registro con éxito");
		 LOG.info("fin -- delete()");
		 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"}) 
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> uploadImageProfile(@RequestParam("archivo") MultipartFile archivo, 
												@RequestParam("id") Long id){
		LOG.info("ini -- uploadImageProfile()");
		Map<String, Object> response =  new HashMap<String, Object>();
		Cliente cliente =  clienteService.findById(id);
		if(!archivo.isEmpty()) {
			String nombreArchivo =null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subuir imagen del cliente : " + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);			
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			
			response.put("cliente", cliente);
			response.put("mensaje","Has subido correctamente la imagen: "+nombreArchivo);
		
		}		
		LOG.info("fin -- uploadImageProfile()");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		LOG.info("ini -- verFoto()");
		Resource recurso =  null;		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"");
		
		LOG.info("fin -- verFoto()");
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
	}
	
	
	
}
