package com.recepcion.entidades;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the fac_doc_recepcion database table.
 * 
 */
@Entity
@Table(name="fac_doc_recepcion")
@NamedQueries({
@NamedQuery(name="FacDocRecepcion.findAll", query="SELECT f FROM FacDocRecepcion f"),
@NamedQuery(name="nq_consulta_docRecepcion", query="SELECT f FROM FacDocRecepcion f where f.id= :id")
})
public class FacDocRecepcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FacDocRecepcionPK id;

	private String ambiente;

	private String correo;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaAutorizacion\"")
	private Date fechaAutorizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="\"fechaEmision\"")
	private Date fechaEmision;

	@Column(name="\"idComp\"")
	private String idComp;

	@Column(name="\"numeroAutorizacion\"")
	private String numeroAutorizacion;

	@Column(name="\"pdfImag\"")
	private byte[] pdfImag;

	@Column(name="\"razonSocialProv\"")
	private String razonSocialProv;

	@Column(name="\"tipoEmision\"")
	private String tipoEmision;

	private BigDecimal total;

	@Column(name="\"typeError\"")
	private String typeError;

	private String version;

	@Column(name="\"xmlDoc\"")
	private String xmlDoc;

	public FacDocRecepcion() {
	}

	public FacDocRecepcionPK getId() {
		return this.id;
	}

	public void setId(FacDocRecepcionPK id) {
		this.id = id;
	}

	public String getAmbiente() {
		return this.ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getIdComp() {
		return this.idComp;
	}

	public void setIdComp(String idComp) {
		this.idComp = idComp;
	}

	public String getNumeroAutorizacion() {
		return this.numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public byte[] getPdfImag() {
		return this.pdfImag;
	}

	public void setPdfImag(byte[] pdfImag) {
		this.pdfImag = pdfImag;
	}

	public String getRazonSocialProv() {
		return this.razonSocialProv;
	}

	public void setRazonSocialProv(String razonSocialProv) {
		this.razonSocialProv = razonSocialProv;
	}

	public String getTipoEmision() {
		return this.tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTypeError() {
		return this.typeError;
	}

	public void setTypeError(String typeError) {
		this.typeError = typeError;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getXmlDoc() {
		return this.xmlDoc;
	}

	public void setXmlDoc(String xmlDoc) {
		this.xmlDoc = xmlDoc;
	}

}