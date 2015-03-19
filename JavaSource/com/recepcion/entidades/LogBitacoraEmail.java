package com.recepcion.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the log_bitacora_email database table.
 * 
 */
@Entity
@Table(name="log_bitacora_email")
@NamedQuery(name="LogBitacoraEmail.findAll", query="SELECT l FROM LogBitacoraEmail l")
public class LogBitacoraEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqLogBitacoraEmail", allocationSize=1,initialValue=1,sequenceName="seqLogBitacoraEmail")
	@GeneratedValue(generator="seqLogBitacoraEmail",strategy=GenerationType.SEQUENCE)
	private long id;

	@Column(name="\"idEmail\"")
	private String idEmail;
	
	private String subject;
	
	@Column(name="\"fromEmail\"")
	private String fromEmail;
	
	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaEmail\"")
	private Date fechaEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaProcesado\"")
	private Date fechaProcesado;

	private String estado;
	
	private String adjunto;
	
	private String observacion;
	
	private BigDecimal reintentos;
	
	public LogBitacoraEmail() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdjunto() {
		return this.adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEmail() {
		return this.fechaEmail;
	}

	public void setFechaEmail(Date fechaEmail) {
		this.fechaEmail = fechaEmail;
	}

	public Date getFechaProcesado() {
		return this.fechaProcesado;
	}

	public void setFechaProcesado(Date fechaProcesado) {
		this.fechaProcesado = fechaProcesado;
	}

	public String getFromEmail() {
		return this.fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getReintentos() {
		return this.reintentos;
	}

	public void setReintentos(BigDecimal reintentos) {
		this.reintentos = reintentos;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}