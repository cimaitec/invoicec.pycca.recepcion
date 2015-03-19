package com.recepcion.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_doc_recepcion database table.
 * 
 */
@Embeddable
public class FacDocRecepcionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String secuencial;

	@Column(name="\"nombreArchivo\"")
	private String nombreArchivo;

	@Column(name="\"ptoEmi\"")
	private String ptoEmi;

	@Column(name="\"idEmail\"")
	private String idEmail;

	@Column(name="\"claveAcceso\"")
	private String claveAcceso;

	@Column(name="\"rucReceptor\"")
	private String rucReceptor;

	@Column(name="\"codDoc\"")
	private String codDoc;

	@Column(name="\"rucProveedor\"")
	private String rucProveedor;

	private String estab;

	public FacDocRecepcionPK() {
	}
	public String getSecuencial() {
		return this.secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public String getNombreArchivo() {
		return this.nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getPtoEmi() {
		return this.ptoEmi;
	}
	public void setPtoEmi(String ptoEmi) {
		this.ptoEmi = ptoEmi;
	}
	public String getIdEmail() {
		return this.idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public String getClaveAcceso() {
		return this.claveAcceso;
	}
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	public String getRucReceptor() {
		return this.rucReceptor;
	}
	public void setRucReceptor(String rucReceptor) {
		this.rucReceptor = rucReceptor;
	}
	public String getCodDoc() {
		return this.codDoc;
	}
	public void setCodDoc(String codDoc) {
		this.codDoc = codDoc;
	}
	public String getRucProveedor() {
		return this.rucProveedor;
	}
	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}
	public String getEstab() {
		return this.estab;
	}
	public void setEstab(String estab) {
		this.estab = estab;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacDocRecepcionPK)) {
			return false;
		}
		FacDocRecepcionPK castOther = (FacDocRecepcionPK)other;
		return 
			this.secuencial.equals(castOther.secuencial)
			&& this.nombreArchivo.equals(castOther.nombreArchivo)
			&& this.ptoEmi.equals(castOther.ptoEmi)
			&& this.idEmail.equals(castOther.idEmail)
			&& this.claveAcceso.equals(castOther.claveAcceso)
			&& this.rucReceptor.equals(castOther.rucReceptor)
			&& this.codDoc.equals(castOther.codDoc)
			&& this.rucProveedor.equals(castOther.rucProveedor)
			&& this.estab.equals(castOther.estab);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.secuencial.hashCode();
		hash = hash * prime + this.nombreArchivo.hashCode();
		hash = hash * prime + this.ptoEmi.hashCode();
		hash = hash * prime + this.idEmail.hashCode();
		hash = hash * prime + this.claveAcceso.hashCode();
		hash = hash * prime + this.rucReceptor.hashCode();
		hash = hash * prime + this.codDoc.hashCode();
		hash = hash * prime + this.rucProveedor.hashCode();
		hash = hash * prime + this.estab.hashCode();
		
		return hash;
	}
}