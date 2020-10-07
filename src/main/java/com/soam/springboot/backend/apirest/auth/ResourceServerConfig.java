package com.soam.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*PASO 3-
 * para dar acceso a los recursos siempre y cuando se tenga el token valido
 * */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	/*Esta configuracion es por el lado de Oauth2
	 * Esto lo vamos a copiar y llevar a SpringSecurityConfig*/
	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**", "/images/**").permitAll()
		//.antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
		//.antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
		//.antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
		//.antMatchers("/api/clientes").hasRole("ADMIN") /*Esto se aplicara para todos los demas que no se hayan indicado antes*/
		/*.antMatchers("/api/clientes/{id}").permitAll()
		  .antMatchers("/api/facturas/**").permitAll()*/
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	/**
	 * 
	 * 
	 * ESPO ES LO ULTIMO: La configuracion del Cors
	 * */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config =  new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE","PUT","OPTIONS")); /*OJO: los metodos van en mayuscula, con una letra que vaya mal dara un error Policy cors block*/
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	
	}
	
	/*Configurar un filtro*/
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		/*Creamos un filtro de Cors a la cual le pasamos la configuracion de arriba 
		 * Y lo registramos dentro del Stack del conjunto de filtros que maneja Spring Framework
		 * Y le dimos una prioridad alta */
		FilterRegistrationBean<CorsFilter> bean =  new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return bean;
	}

	
}
