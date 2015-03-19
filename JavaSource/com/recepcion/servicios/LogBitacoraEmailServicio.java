package com.recepcion.servicios;

import com.recepcion.controlador.BitacoraEmailControlador;
import com.recepcion.controlador.LogBitacoraEmailControlador;
import com.recepcion.entidades.LogBitacoraEmail;

public class LogBitacoraEmailServicio {
	private LogBitacoraEmailControlador logBitEmailContr;
	private BitacoraEmailControlador bitEmailContr;
	
	public LogBitacoraEmailServicio() {
		logBitEmailContr= new LogBitacoraEmailControlador();
	}

	public void insertarEmail(LogBitacoraEmail p)
	{
		logBitEmailContr.insertarLogEmail(p);
		
	}
	

}
