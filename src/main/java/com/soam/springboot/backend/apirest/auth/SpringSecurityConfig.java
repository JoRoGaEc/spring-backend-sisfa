package com.soam.springboot.backend.apirest.auth;
/*
 * PASO 1
 * Luego del UserService este es el paso 1 que se debe realizar para configurar Spring Security
 * 
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
/*NECESITAMOS
 * Un atributo de que implemente la clase UserDetailService
 * 
 * */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{
/*
 * PASO 1. Vamos a Inyectar el UserDetailService, y como nosostros tenemos una clase implementada con la intefaz
 * va ir a buscar en el contenedor la implementacion y ya podremos acceder a dicha implementacion
 * 
 * PASO 2. Sobreescribir el metodo configure
 * 
*/
	@Autowired
	private UserDetailsService usuarioService;

	@Override
	@Autowired /* para que spring lo agrege al contenedor*/
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}
	
	
	/*
	 * Registrar este metodo como un componente de Spring, un Bean
	 * ya que mas adelante lo vamos a utilizar en otras configuraciones
	 * El objeto que retorne el metodo se va a registrar en el contenedor de Spring, para poder inyectarlo
	 * */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean("authenticationManager")
	@Override /*para que spring lo agrege al contenedor, por defecto spring lo agregara
	 			con el nombre authenticationManager()
	 			Este lo usaremos en AuthorizationServerConfig*/
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	/*AGREGAR REGLAS PERO POR EL LADO DE SPRING*/
	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.csrf().disable() //Esto porque no tenemos formularios en el backend
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/*El manejo de sesion queda desabilitado, ya que estamos guardando la informacion
		 * del empleado por token en el frontEnt con angular */
	}
	
	
	
}
