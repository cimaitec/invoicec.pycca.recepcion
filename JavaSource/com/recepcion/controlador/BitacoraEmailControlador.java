package com.recepcion.controlador;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.recepcion.conexion.BasePostgreSQL;
import com.recepcion.entidades.FacBitacoraEmail;
import com.recepcion.entidades.LogBitacoraEmail;

public class BitacoraEmailControlador extends BasePostgreSQL {
	
	private EntityManager em ;
	
	public void insertarBitacoraEmail(FacBitacoraEmail a )
	{
		System.out.println("Insertando Email ID:"+a.getId().getIdEmail().toString());
		em = obtieneConexion();
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
	}
	
	public void updateBitacoraEmail(FacBitacoraEmail a )
	{
		System.out.println("Actualizando Email ID:"+a.getId().getIdEmail()+"|"+a.getId().getReintentos());
		em = obtieneConexion();
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
	}
	
	public FacBitacoraEmail consultaEmailId(FacBitacoraEmail p)
	{
		em = obtieneConexion();
		Query q = em.createNamedQuery("nq_consulta_emaiId");
		q.setParameter("id", p.getId());
		FacBitacoraEmail a; 
		try{
			a = (FacBitacoraEmail) q.getSingleResult();
		}catch(Exception e){
			a =null;
		}
		return a;
	}
	

	
	
}
