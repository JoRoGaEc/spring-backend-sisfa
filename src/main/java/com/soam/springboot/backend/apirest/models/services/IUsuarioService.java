package com.soam.springboot.backend.apirest.models.services;

import com.soam.springboot.backend.apirest.models.entity.security.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
	
}
