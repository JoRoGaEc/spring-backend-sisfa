package com.soam.springboot.backend.apirest.models.services.security;



import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soam.springboot.backend.apirest.models.dao.IUsuarioDao;
import com.soam.springboot.backend.apirest.models.entity.security.Usuario;
import com.soam.springboot.backend.apirest.models.services.IUsuarioService;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService{
	
	private Logger LOG  = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	
	@Override
	@Transactional(readOnly =  true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario =  usuarioDao.findByUsername(username);		
		
		if(usuario == null ) {
			LOG.info("Error del login: NO existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException("Erro en el login : no existe el usuario '" + username + "' en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles() //La implementacion concreta es SimpleGrantedAuthority
					.stream() //para trabajar con Flujos, Aca lo usamos para hacer una conversion de cada instancia de forma elegante
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				    .peek(authority -> LOG.info("Role : " + authority.getAuthority()))
					.collect(Collectors.toList());
		
		return new User(username, usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

///--------------------------------------------------------------------------------------------------------
	/*metodos especificos*/
	@Override
	public Usuario findByUsername(String username) {

		return usuarioDao.findByUsername(username);
	}

}
