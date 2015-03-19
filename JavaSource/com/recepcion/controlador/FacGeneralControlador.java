package com.recepcion.controlador;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.recepcion.conexion.BasePostgreSQL;
import com.recepcion.entidades.FacDocRecepcion;


public class FacGeneralControlador extends BasePostgreSQL {
	
	private EntityManager em ;
	
	
	public boolean existeRucReceptor(String p)
	{
		em = obtieneConexion();
		Query q = em.createNamedQuery("nq_consulta_rucReceptor");
		q.setParameter("ruc", p);
		boolean a; 
		try{
			a = (q.getSingleResult()!=null?true:false);
		}catch(Exception e){
			a = false;
		}
		return a;
	}
	

	
	
}
