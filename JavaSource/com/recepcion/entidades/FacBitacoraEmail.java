package com.recepcion.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fac_bitacora_email database table.
 * 
 */
@Entity
@Table(name="fac_bitacora_email")
@NamedQueries({
	@NamedQuery(name="FacBitacoraEmail.findAll", query="SELECT f FROM FacBitacoraEmail f"),
	@NamedQuery(name="nq_consulta_emaiId", query="SELECT f FROM FacBitacoraEmail f where f.id = :id")
})
public class FacBitacoraEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacBitacoraEmailPK id;

	private String adjunto;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaEmail\"")
	private Date fechaEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaProcesado\"")
	private Date fechaProcesado;

	@Column(name="\"fromEmail\"")
	private String fromEmail;

	private String observacion;

	private String subject;

	public FacBitacoraEmail() {
	}

	public FacBitacoraEmailPK getId() {
		return this.id;
	}

	public void setId(FacBitacoraEmailPK id) {
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

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}