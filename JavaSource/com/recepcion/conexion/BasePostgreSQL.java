package com.recepcion.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BasePostgreSQL {
	private EntityManagerFactory baseDatos;
	private EntityManager conexion;
	
	protected EntityManager obtieneConexion(){
		
		if (baseDatos == null || conexion == null) {
			baseDatos = Persistence
					.createEntityManagerFactory("Invoice");
			conexion = baseDatos.createEntityManager();
		}
		
		return conexion;
	}
}
