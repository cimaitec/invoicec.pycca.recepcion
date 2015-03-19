package com.recepcion.test;

import java.util.Date;

import com.recepcion.controlador.BitacoraEmailControlador;
import com.recepcion.controlador.LogBitacoraEmailControlador;
import com.recepcion.entidades.FacBitacoraEmail;
import com.recepcion.entidades.LogBitacoraEmail;

public class Test {
	  public static void main(String argv[])
	    {
		  LogBitacoraEmailControlador contEmail = new LogBitacoraEmailControlador();
		  LogBitacoraEmail logBitEmail = new LogBitacoraEmail();
		  logBitEmail.setIdEmail("7");
		  logBitEmail.setEstado("X");
		  logBitEmail.setFechaEmail(new Date());
		  logBitEmail.setObservacion("Prueba Persistencia");
		  contEmail.insertarLogEmail(logBitEmail);
	    }
}
