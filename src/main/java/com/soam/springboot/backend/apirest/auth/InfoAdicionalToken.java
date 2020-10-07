package com.soam.springboot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
/*PASO 4
 * Agregando informacion adicional para el cliente
 * */
import org.springframework.stereotype.Component;

import com.soam.springboot.backend.apirest.models.entity.security.Usuario;
import com.soam.springboot.backend.apirest.models.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		//Inforkacion adicional que vamos a guardar en el Token
		Map<String, Object> info= new HashMap<String, Object>();
		/*Si quisieramos pasar otros datos  como el email , deberiamos hacer una consulta en JPA
		 * y con el objeto recuperado se lo pasamos en el HashMap
		 * */
		info.put("email", usuario.getEmail());
		info.put("nombre",  usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
