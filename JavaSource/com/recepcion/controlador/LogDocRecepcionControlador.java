package com.recepcion.controlador;

import javax.persistence.EntityManager;



import com.recepcion.conexion.BasePostgreSQL;
import com.recepcion.entidades.LogDocRecepcion;


public class LogDocRecepcionControlador extends BasePostgreSQL {
	
	private EntityManager em ;
	
	public void insertarDocRecepcion(LogDocRecepcion a )
	{
		System.out.println("Insertando Email ID:");
		em = obtieneConexion();
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
	}
	
}
