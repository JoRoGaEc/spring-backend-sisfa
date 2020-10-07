package com.soam.springboot.backend.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootBackendApplication implements CommandLineRunner{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String clave="sisfa2019.";
		for(int i=0; i <4 ;i++) {
			//String claveEncoded  = passwordEncoder.encode(clave);
			//System.out.println(claveEncoded);
		}
		
	}

}
