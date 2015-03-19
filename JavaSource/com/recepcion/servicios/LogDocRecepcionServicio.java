package com.recepcion.servicios;

import com.recepcion.controlador.LogDocRecepcionControlador;
import com.recepcion.entidades.LogDocRecepcion;


public class LogDocRecepcionServicio {
	private LogDocRecepcionControlador logDocRecepcionContr;

	
	public LogDocRecepcionServicio() {
		logDocRecepcionContr = new LogDocRecepcionControlador();
	}

	public void insertarLogDocRecepcion(LogDocRecepcion p)
	{
		logDocRecepcionContr.insertarDocRecepcion(p);
		
	}
	

}
