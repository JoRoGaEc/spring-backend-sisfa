package com.soam.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*PASO 2.---
 * 
 *Este se encarga de todo el proceso de autenticacion por el lado de Oauth2, todo lo que tine que ver con el Token 
 *Desde Jwt, todo lo que tiene que ver con el proceso de Login...
 *Crear el Token, Validarlo, etc.
 *
 * */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
    
//1--------------------------------------------------------------------------------
	@Autowired /*Esto viene de SpringSecurityConfig*/
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired/*Esto viene de SpringSecurityConfig*/
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	/*Este es el componente que va tener la informacion extra*/
	@Autowired
	private InfoAdicionalToken infoAditionalToken;
	

//2 --------------------------------------------------------------------------------
	/*
	 * Ahora vamos a implementar 3 metodos*/

	/*1.
	 * Este se encarga de todo el proceso de autenticacion
	 * y de validar el Token y su firma
	 * 
	 * */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		 TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		 tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken,accessTokenConverter()));
		 
		 endpoints.authenticationManager(authenticationManager)
		 .tokenStore(tokenStore())      //Esto es opcional para dejarlo explicito
		 .accessTokenConverter(accessTokenConverter())
			/*AccessTokenConverter : Encargado de realiza varias cosas relacionadas con el token como por ejemplo: 
			 *Almacena los datos de autenticacion del usuario, username, roles, y cualquier informacion extra que
			 *querramos agregar, codificar y decodificar datos 
			 *NO GUARDAR alguna informacion sensible con #de tarjetas de credito */
			
		 .tokenEnhancer(tokenEnhancerChain);

		/*Se encarga de traducir los datos del token de accesso, para la autenticacion y que el token sea valido 
		 *Este componente por debajo lo va utilizar el Jwt Token Storage, otro componente que:
		 *1. Se encarga de crear el Jwt
		 *2.  De buscar, eliminar el token  (Lo relacionado a la persistencia del Token)
		 * */
		
	}
	
	@Bean
	public  JwtAccessTokenConverter accessTokenConverter() {
			JwtAccessTokenConverter jwtAccessTokenConverter  = new JwtAccessTokenConverter();
			jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA); //EL FIRMANTE....Esto va servir paraa verificar la firma
			jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA); //El que valida, el que verifica es la publica
			/*Si nosotros no lo colocamos el sistema genera uno por defecto, Ramdom*/
		return jwtAccessTokenConverter;
	}
	
	/*este es opcional*/
	@Bean
	public  JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}	
	
//3 ----------------------------------------------------------------------------------
	/* 
	 * Si tenemos muchos clientes , por ejemplo una en Angular, otra en ReactJS, Android
	 * Debemos registrar sus ID, con su codigo secreto o contraseña
	 * La idea de Oauth2 no es solo conectarse con nuestro backend, si no tambien autenticarse con la aplicacion cliente
	 * */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*1er Cliente*/
		clients.inMemory().withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		/*Este es el tipo de concesion el token,
		 *usamos password ya que los usuarios se van autenticar a traves de una pass, en conclusion via pag. de login
		 *Otros: Implicita,enviamos el Cliente ID, y el password, refresh.
		 */
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(7200);
		
		/*2do Cliente en caso de que hubiese*/
										
	}
	
	
	/*Aqui se configura los permisos de nuestros endpoints, de nuestra ruta de accesso
	 * de SpringSecurity Oauth2*/
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.tokenKeyAccess("permitAll()")  /* Sirve para el /oauth/token/ para generar el token*/
		.checkTokenAccess("isAuthenticated()");     /*Permiso al endPoint que se encarga de validar el token*/
	}



	    
	
}
