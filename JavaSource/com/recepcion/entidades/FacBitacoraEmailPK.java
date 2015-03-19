package com.recepcion.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fac_bitacora_email database table.
 * 
 */
@Embeddable
public class FacBitacoraEmailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"idEmail\"")
	private String idEmail;

	private long reintentos;

	public FacBitacoraEmailPK() {
	}
	public String getIdEmail() {
		return this.idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	public long getReintentos() {
		return this.reintentos;
	}
	public void setReintentos(long reintentos) {
		this.reintentos = reintentos;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacBitacoraEmailPK)) {
			return false;
		}
		FacBitacoraEmailPK castOther = (FacBitacoraEmailPK)other;
		return 
			this.idEmail.equals(castOther.idEmail)
			&& (this.reintentos == castOther.reintentos);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEmail.hashCode();
		hash = hash * prime + ((int) (this.reintentos ^ (this.reintentos >>> 32)));
		
		return hash;
	}
}