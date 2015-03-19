package com.recepcion.entidades;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the fac_general database table.
 * 
 */
@Entity
@Table(name="fac_general")

@NamedQueries({
	@NamedQuery(name="FacGeneral.findAll", query="SELECT f FROM FacGeneral f"),
@NamedQuery(name="nq_consulta_RupReceptor", query="SELECT f FROM FacGeneral f where f.codTabla = :ruc and f.isActive = \"Y\" ")
})
public class FacGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"codUnico\"")
	private String codUnico;

	@Column(name="\"codTabla\"")
	private String codTabla;

	private String descripcion;

	@Column(name="\"idGeneral\"")
	private String idGeneral;

	@Column(name="\"isActive\"")
	private String isActive;

	private String porcentaje;

	public FacGeneral() {
	}

	public String getCodUnico() {
		return this.codUnico;
	}

	public void setCodUnico(String codUnico) {
		this.codUnico = codUnico;
	}

	public String getCodTabla() {
		return this.codTabla;
	}

	public void setCodTabla(String codTabla) {
		this.codTabla = codTabla;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdGeneral() {
		return this.idGeneral;
	}

	public void setIdGeneral(String idGeneral) {
		this.idGeneral = idGeneral;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

}