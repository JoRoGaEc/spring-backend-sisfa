package com.soam.springboot.backend.apirest.models.entity.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L; //Nos permite guardar el objeto en una sesion Http, Ademas de poder serializarlo en JSON

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique= true, length = 20)
	private String username;
	
	@Column(length = 60) //El password pasará a ser una cadena larga cuando lo hasheamos
	private String password;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="email")
	private String email;
	
	@Column
	private Boolean enabled;
	
	@ManyToMany(fetch =  FetchType.LAZY, cascade =  CascadeType.ALL)
	//Lo de definir un nombre personalizado para la tabla intermedia se hace en la dueña de la relacion
	@JoinTable(
			name = "usuarios_roles", 
			uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id","role_id" }) }, 
			joinColumns = @JoinColumn(name = "usuario_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles =  new ArrayList<Role>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
