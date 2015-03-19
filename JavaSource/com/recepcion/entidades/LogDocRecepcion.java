package com.recepcion.entidades;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the log_doc_recepcion database table.
 * 
 */
@Entity
@Table(name="log_doc_recepcion")
@NamedQuery(name="LogDocRecepcion.findAll", query="SELECT l FROM LogDocRecepcion l")
public class LogDocRecepcion implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@SequenceGenerator(name="seqLogDocRecepcion", allocationSize=1,initialValue=1,sequenceName="seqLogDocRecepcion")
	@GeneratedValue(generator="seqLogDocRecepcion",strategy=GenerationType.SEQUENCE)
	@Id
	private Integer id;
	
	private String accion;

	@Column(name="\"campoComparacion\"")
	private String campoComparacion;

	private String detalle;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaProceso\"")
	private Date fechaProceso;

	@Column(name="\"idEmail\"")
	private String idEmail;

	@Column(name="\"nombreArchivo\"")
	private String nombreArchivo;

	private Integer reintento;

	@Column(name="\"typeError\"")
	private String typeError;

	@Column(name="\"valorComparacion\"")
	private String valorComparacion;

	public LogDocRecepcion() {
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCampoComparacion() {
		return this.campoComparacion;
	}

	public void setCampoComparacion(String campoComparacion) {
		this.campoComparacion = campoComparacion;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaProceso() {
		return this.fechaProceso;
	}

	public void setFechaProceso(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Integer getReintento() {
		return this.reintento;
	}

	public void setReintento(Integer reintento) {
		this.reintento = reintento;
	}

	public String getTypeError() {
		return this.typeError;
	}

	public void setTypeError(String typeError) {
		this.typeError = typeError;
	}

	public String getValorComparacion() {
		return this.valorComparacion;
	}

	public void setValorComparacion(String valorComparacion) {
		this.valorComparacion = valorComparacion;
	}

}