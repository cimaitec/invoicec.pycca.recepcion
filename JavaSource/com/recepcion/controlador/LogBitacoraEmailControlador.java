package com.recepcion.controlador;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.recepcion.conexion.BasePostgreSQL;
import com.recepcion.entidades.FacBitacoraEmail;
import com.recepcion.entidades.LogBitacoraEmail;

public class LogBitacoraEmailControlador extends BasePostgreSQL {
	
	private EntityManager em ;
	
	public void insertarLogEmail(LogBitacoraEmail a )
	{
		System.out.println("Insertando Email ID:"+a.getIdEmail());
		em = obtieneConexion();
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
	}
	
}
