package com.soam.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.soam.springboot.backend.apirest.models.entity.security.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	@Query("from Usuario u where u.username  = ?1")
	public Usuario findByUsernameJpql(String name);
	
}
