package com.soam.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="facturas")
public class Factura implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id") /*Join column, es el nombre que va tomar la foranea de cliente en esta tabla*/
	@JsonIgnoreProperties(value = {"facturas","hibernateLazyInitializer", "handler"}, allowSetters = true) /*facturas es como esta en la Entidad Cliente*/
	/*AllowSetter para evitar problema de recursion al subir el cambio */
	/*Significa que el cliente que esta en Factura ignoro sus facturas para evitar el loop*/
	private Cliente cliente;
	
	@OneToMany(fetch =  FetchType.LAZY, cascade  = CascadeType.ALL)
	@JoinColumn(name="factura_id") /*Este nombre se va ocupar para crear el factura_id en ItemFactura, el cual es el dueño*/
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<ItemFactura>  items; /*No ignoramos nada en esta Entidad ya que en ItemFactura no tenemos el atributo Factura*/
	
	public Factura() {
		items =  new ArrayList<>(); /*Para inicializar el arrayList donde vamos a insertar los productos*/
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	
	public Double getTotal() {
		Double total = 0.00;
		for(ItemFactura item:  this.items) {
			total += item.getImporte();
		}
		return total;
	}
	
	
}
