package com.recepcion.servicios;

import com.recepcion.controlador.FacGeneralControlador;



public class FacGeneralServicio {
	private FacGeneralControlador facGeneralContr;

	
	public FacGeneralServicio() {
		facGeneralContr = new FacGeneralControlador();
	}

	public boolean existeRucReceptor(String ruc)
	{
		try{
			return facGeneralContr.existeRucReceptor(ruc);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	

}
