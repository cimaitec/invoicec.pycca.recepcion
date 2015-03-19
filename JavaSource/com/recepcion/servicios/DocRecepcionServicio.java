package com.recepcion.servicios;

import java.io.File;
import java.io.IOException;

import com.recepcion.controlador.DocRecepcionControlador;
import com.recepcion.entidades.FacDocRecepcion;
import com.recepcion.util.Util;


public class DocRecepcionServicio {
	private DocRecepcionControlador docRecepContr;
	
	
	
	public DocRecepcionServicio() {
		docRecepContr= new DocRecepcionControlador();
	}

	public void insertarFacDocRecepcion(FacDocRecepcion p)
	{
		
		FacDocRecepcion facDocRecep = docRecepContr.consultaEmailId(p);
		
		if(facDocRecep == null){
			docRecepContr.insertarDocRecepcion(p);
		}else{
			docRecepContr.updateDocRecepcion(p);
		}
/*		
		try {
			Util.writeBytesToFile(new File("C:/VictorPincayPrueba.pdf"), facDocRecep.getPdfImag());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}
	
	
	

}
