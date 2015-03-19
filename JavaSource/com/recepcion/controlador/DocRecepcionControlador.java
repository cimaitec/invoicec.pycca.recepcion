package com.recepcion.controlador;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.recepcion.conexion.BasePostgreSQL;
import com.recepcion.entidades.FacDocRecepcion;


public class DocRecepcionControlador extends BasePostgreSQL {
	
	private EntityManager em ;
	
	public void insertarDocRecepcion(FacDocRecepcion a )
	{
		System.out.println("Insertando Documento Recibido Clave:"+a.getId().getClaveAcceso());
		em = obtieneConexion();
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
	}
	
	public void updateDocRecepcion(FacDocRecepcion a )
	{
		System.out.println("Insertando Documento Recibido Clave:"+a.getId().getClaveAcceso());
		em = obtieneConexion();
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
	}
	
	public FacDocRecepcion consultaEmailId(FacDocRecepcion p)
	{
		em = obtieneConexion();
		Query q = em.createNamedQuery("nq_consulta_docRecepcion");
		q.setParameter("id", p.getId());
		FacDocRecepcion a; 
		try{
			a = (FacDocRecepcion) q.getSingleResult();
		}catch(Exception e){
			a =null;
		}
		return a;
	}
	

	
	
}
