package com.recepcion.servicios;

import sun.util.logging.resources.logging;

import com.recepcion.controlador.BitacoraEmailControlador;
import com.recepcion.entidades.FacBitacoraEmail;
import com.recepcion.entidades.LogBitacoraEmail;

public class BitacoraEmailServicio {
	private BitacoraEmailControlador bitEmailContr;
	
	
	
	public BitacoraEmailServicio() {
		bitEmailContr= new BitacoraEmailControlador();
	}

	public void insertarEmail(FacBitacoraEmail p)
	{
		
		FacBitacoraEmail facBitEmail = bitEmailContr.consultaEmailId(p);
		if(facBitEmail == null){
			bitEmailContr.insertarBitacoraEmail(p);
		}else{
			bitEmailContr.updateBitacoraEmail(p);
		}
		
	}
	
	
	

}
