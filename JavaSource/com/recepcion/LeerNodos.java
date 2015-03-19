package com.recepcion;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.jdom2.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class LeerNodos {
	private static	String 		xmlConfigParametros	= "xmlConfigParametros.xml";
	private static	File 		xmlCorreo 			= null;
	private static	File 		xmlSRI    			= null;
	private static  Document 	doc 				= null;
    private static  Document	docCorreo 			= null; 
    private static  Document	docSRI 				= null;
    private static  String		tramaErrorCampo		= null;
    private static  String		tramaErrorValor		= null;
    private static  String		tramaTypeError		= null;
    private static  boolean		typeToWarning			= false;
    private static  boolean		typeToError			= false;
    private static  boolean		typeToInformation		= false;
    

//====================================================================================================
//	Funcion para validar Archivo XML recibido por correo. Se leera el archivo para obtener la clave de
//	acceso y consultarlo en el WS del SRI, para verificar si esta autorizado y validar que los TAGs 
//  que del XML devuelto por el SRI del documento electronica concidan con el recibido por correo
//====================================================================================================
    public LeerNodos(String xmlCorreo, String xmlSRI) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();
            
            doc = builder.parse(xmlConfigParametros);
            docCorreo = builder.parse(xmlCorreo);
            docSRI =  builder.parse(xmlSRI);
            this.xmlCorreo = new File(xmlCorreo);
            this.xmlSRI = new File(xmlSRI);
            tramaErrorCampo ="";
            tramaErrorValor ="";
            
            
            System.out.println("LeerNodos() >> Cargando Archivo de Configuracion XML :"+xmlConfigParametros);
    	} catch (Exception e) {
            System.out.println("LeerNodos() >> Error : "+e.getMessage());
    	}
	}
//====================================================================================================
//	Funcion para validar Archivo XML recibido por correo. Se leera el archivo para obtener la clave de
//	acceso y consultarlo en el WS del SRI, para verificar si esta autorizado y validar que los TAGs 
//  que del XML devuelto por el SRI del documento electronica concidan con el recibido por correo
//====================================================================================================	    	
	public List<String> compararXML(String tipoDocumento) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        String typeNode = null;
        String pathXML 	= null;
        String path 	= null;
        String lvError 	= "";
        tramaTypeError = "";
        typeToError = false;
        typeToInformation = false;
        typeToWarning = false;
        List<String> listaResultados = new ArrayList<String>();

        
        try {

            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();
 
            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/Nodos/"+tipoDocumento+"/Nodo[*]/@name");
            System.out.println("LeerNodos.compararXML() >> Inicia comparacion de Archivos XML :: "+xmlCorreo.getAbsolutePath()+"<<=>>"+xmlSRI.getAbsolutePath());
            System.out.println("\t\t============================== Tipo de Documento : "+tipoDocumento+"====================================");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);          
	            for (int i=0; i<nodes.getLength(); i++){
		    		path = "/Nodos/"+tipoDocumento+"/Nodo[@name ='"+nodes.item(i).getNodeValue()+"']";
		    		pathXML = "/"+tipoDocumento+"/"+nodes.item(i).getNodeValue();
		    		typeNode = (String) xpath.compile(path+"/@type").evaluate(doc, XPathConstants.STRING);
		            if(typeNode.equals("NODESET")){
		            	comparaNodoIndividual(path, pathXML);
		            }
	            }
	        // 
		          if(typeToInformation){
		        	  tramaTypeError = "information";
		          }
		          if(typeToWarning){
		        	  tramaTypeError = "warning";
		          }
		          if(typeToError){
		        	  tramaTypeError = "error";
		          }
		          
		          if(tramaTypeError.equals("")){
		        	  tramaTypeError = "none";
		          }
		          
		          listaResultados.add(0,tramaTypeError);
		          listaResultados.add(1,tramaErrorCampo);
		          listaResultados.add(2,tramaErrorValor);
		          return listaResultados;
		          
        	} catch (Exception e) {
        		lvError = "LeerNodos.comaparaXml() >> Error : "+e.getMessage();
                System.out.println(lvError);
                e.printStackTrace();
                listaResultados.set(0,"error");//primera posicion de para el Campo error
                listaResultados.add(1,lvError);
                return listaResultados;
        	}
        }
//====================================================================================================
//	Funcion para validar Archivo XML recibido por correo. Se leera el archivo para obtener la clave de
//	acceso y consultarlo en el WS del SRI, para verificar si esta autorizado y validar que los TAGs 
//  que del XML devuelto por el SRI del documento electronica concidan con el recibido por correo
//====================================================================================================	  
	private  void comparaNodoIndividual(String paramPath,String paramPathXML) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        String typeNode =null, typeError =null;
        String valorNodoCorreo = null, valorNodoSRI = null;
        String nombreCampo = null;
        String pathXML 	= null,	 path 	= null;
        try {

            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();
 
            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile(paramPath+"/*");
            NodeList nodes = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);
            //Obtengo el nombre del nodo 
            //System.out.println(paramPathXML);
	            for (int i=0; i<1; i++){
			    		path = paramPath+"/"+nodes.item(i).getNodeName();
			    		pathXML = paramPathXML;
			    		NodeList nodeList = (NodeList ) xpath.compile(path+"/@name").evaluate(doc, XPathConstants.NODESET);
			    		
				    	for (int j=0; j<nodeList.getLength(); j++){
				    		String pathNode = path+"[@name ='"+nodeList.item(j).getNodeValue()+"']";
				    		String pathNodeXML = pathXML+"/"+nodeList.item(j).getNodeValue();
				    		typeNode = (String) xpath.compile(pathNode+"/@type").evaluate(doc, XPathConstants.STRING);
				    		typeError = (String) xpath.compile(pathNode+"/@typeError").evaluate(doc, XPathConstants.STRING);
				            if(typeNode.equals("NODESET")){
				            	comparaNodoIndividual(pathNode, pathNodeXML);
				            }else if(typeNode.equals("STRING")){
				            	nombreCampo 	=	nodeList.item(j).getNodeValue();
		                		valorNodoCorreo = 	(String) xpath.compile(pathNodeXML+"/text()").evaluate(docCorreo, XPathConstants.STRING);
		                		valorNodoSRI 	= 	(String) xpath.compile(pathNodeXML+"/text()").evaluate(docSRI, XPathConstants.STRING);
		                		
		                		//System.out.println("\t\t"+nodeList.item(j).getNodeValue()+" :"+valorNodoCorreo.trim());
		                		compararNodo(nombreCampo, valorNodoCorreo, valorNodoSRI, typeError);
				            }
				    	}
	            }
        	} catch (Exception e) {
                System.out.println("LeerNodos.comaparaXml() >> Error : "+e.getMessage());
                e.printStackTrace();
        	}
        }
	
	//====================================================================================================
	//Funcion para validar Archivo XML recibido por correo. Se leera el archivo para obtener la clave de
	//acceso y consultarlo en el WS del SRI, para verificar si esta autorizado y validar que los TAGs 
	//que del XML devuelto por el SRI del documento electronica concidan con el recibido por correo
	//====================================================================================================	  	
	private void compararNodo(String NombreCampo,String valorNodoCorreo, String valorNodoSRI, String typeError) {
		String lvError = null;
		//Comparo los valores de los dos Nodos
		if(!valorNodoCorreo.equals(valorNodoSRI)){
		  if(!(typeError.equals("error") || typeError.equals("warning") || typeError.equals("information"))){
			  lvError ="Tipo de Error no definido";
			  System.out.println(lvError);
		  }else{
				if(typeError.equals("error")){
					typeToError = true;
				}else if(typeError.equals("warning")){
					typeToWarning 		= true;
				}else if(typeError.equals("information")){
					typeToInformation 	= true;
				}else{
					
				}
		  }
		  tramaErrorCampo += NombreCampo+", ";
		  tramaErrorValor += "["+valorNodoCorreo+";"+valorNodoSRI+"]";
		  
		}
		
	}
	
	
//Metodo inicial para comparar xml - quedo obsoleto por nuevos metodos recursivos	
/*	public boolean comaparaXml(String xmlCorreo, String xmlSRI,String tipoDocumento) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null,docCorreo = null, docSRI = null;
        String path = null,path1 = null,path2 = null,path3 = null,path4 = null;
        String pathXML = null, pathXML1 = null, pathXML2 = null, pathXML3 = null, pathXML4 = null;
        String typeNode =null, typeError =null;
        String valorNodoCorreo = null, valorNodoSRI = null;
        
        
        try {
            builder = factory.newDocumentBuilder();
            
            doc = builder.parse(xmlConfigParametros);
            docCorreo = builder.parse(xmlCorreo);
            docSRI =  builder.parse(xmlSRI);
            
            System.out.println("File>>"+xmlConfigParametros);
 
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();
 
            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/Nodos/"+tipoDocumento+"/Nodo[*]/@name");
            System.out.println("============================== Tipo de Documento : "+tipoDocumento+"====================================");
            NodeList nodes = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);          
            for (int i=0; i<nodes.getLength(); i++){
	    		path = "/Nodos/"+tipoDocumento+"/Nodo[@name ='"+nodes.item(i).getNodeValue()+"']";
	    		pathXML = "/"+tipoDocumento+"/"+nodes.item(i).getNodeValue();
	    		
	            typeNode = (String) xpath.compile(path+"/@type").evaluate(doc, XPathConstants.STRING);
	            if(typeNode.equals("NODESET")){
	            	expr = xpath.compile(path+"/Subnodo/@name");
	                NodeList subNodes = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);
	                for (int j=0; j<subNodes.getLength(); j++){
	                	
	                	path1 = path+"/Subnodo[@name='"+subNodes.item(j).getNodeValue()+"']";
	                	pathXML1 = pathXML+"/"+subNodes.item(j).getNodeValue();
	
	                	System.out.println("\t"+subNodes.item(j).getNodeValue());
	                	
	                	typeNode = (String) xpath.compile(path1+"/@type").evaluate(doc, XPathConstants.STRING);
	                	typeError = (String) xpath.compile(path1+"/@typeError").evaluate(doc, XPathConstants.STRING);
	                	if(typeNode.equals("STRING")){
	                		//Reliza la primera comparacion en el primer Nivel para los type NODESET
	                		valorNodoCorreo = (String) xpath.compile(pathXML1+"/text()").evaluate(docCorreo, XPathConstants.STRING);
	                		valorNodoSRI = (String) xpath.compile(pathXML1+"/text()").evaluate(docSRI, XPathConstants.STRING);
	                		System.out.println("\t\t"+subNodes.item(j).getNodeValue()+" de  Correo :"+valorNodoCorreo.trim());
	                		System.out.println("\t\t"+subNodes.item(j).getNodeValue()+" de  SRI :"+valorNodoCorreo.trim());
	                		System.out.println("\t\t"+pathXML1);
	                		
	                		System.out.println(typeError);
	                	}else{
	                    	expr = xpath.compile(path1+"/nodoDetalle/@name");
	                        NodeList subNodesDetalles = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);            
	                        for (int k=0; k<subNodesDetalles.getLength(); k++){
	                        	
	                            	
	                            	path2 = path1+"/nodoDetalle[@name='"+subNodesDetalles.item(k).getNodeValue()+"']";
	                            	pathXML2 = pathXML1+"/"+subNodesDetalles.item(k).getNodeValue();
	                            	
	
	                            	System.out.println("\t\t"+subNodesDetalles.item(k).getNodeValue());
	                            	
	                            	typeNode = (String) xpath.compile(path2+"/@type").evaluate(doc, XPathConstants.STRING);
	                            	typeError = (String) xpath.compile(path2+"/@typeError").evaluate(doc, XPathConstants.STRING);
	                            	if(typeNode.equals("STRING")){
	                            		//Reliza la primera comparacion en el Segundo Nivel para los type STRING
	                            		valorNodoCorreo = (String) xpath.compile(pathXML2+"/text()").evaluate(docCorreo, XPathConstants.STRING);
	                            		valorNodoSRI = (String) xpath.compile(pathXML2+"/text()").evaluate(docSRI, XPathConstants.STRING);
	                            		System.out.println("\t\t"+subNodesDetalles.item(k).getNodeValue()+" de  Correo :"+valorNodoCorreo.trim());
	                            		System.out.println("\t\t"+subNodesDetalles.item(k).getNodeValue()+" de  SRI :"+valorNodoSRI.trim());
	                            		System.out.println("\t\t"+pathXML1);
	                            		System.out.println(typeError);
	                            	}else{
		                                	expr = xpath.compile(path2+"/SubnodoDetalle/@name");
		                                    NodeList Detalles = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);            
		                                    for (int h=0; h<Detalles.getLength(); h++){
			                                    	path3 = path2+"/SubnodoDetalle[@name='"+Detalles.item(h).getNodeValue()+"']";
			                                    	pathXML3 = pathXML2+"/"+Detalles.item(h).getNodeValue();
			                                    	
			                                    	System.out.println("\t\t\t"+Detalles.item(h).getNodeValue());
			                                    	
			                                      	typeNode = (String) xpath.compile(path3+"/@type").evaluate(doc, XPathConstants.STRING);
			                                      	typeError = (String) xpath.compile(path3+"/@typeError").evaluate(doc, XPathConstants.STRING);
				                                	if(typeNode.equals("STRING")){
				                                		//Reliza comparacion en el Tercer Nivel para los type STRING
				                                		valorNodoCorreo = (String) xpath.compile(pathXML3+"/text()").evaluate(docCorreo, XPathConstants.STRING);
				                                		valorNodoSRI = (String) xpath.compile(pathXML3+"/text()").evaluate(docSRI, XPathConstants.STRING);
				                                		System.out.println("\t\t\t"+Detalles.item(h).getNodeValue()+" de  Correo :"+valorNodoCorreo.trim());
				                                		System.out.println("\t\t\t"+Detalles.item(h).getNodeValue()+" de  SRI :"+valorNodoSRI.trim());
				                                		System.out.println("\t\t\t"+pathXML3);
				                                		System.out.println(typeError);
				                                	}else{	
					                                    	expr = xpath.compile(path3+"/Detalle/@name");
					                                        NodeList subDetalles = (NodeList ) expr.evaluate(doc, XPathConstants.NODESET);            
					                                        for (int t=0; t<subDetalles.getLength(); t++){
					                                        	path4 = path3+"/Detalle[@name='"+subDetalles.item(t).getNodeValue()+"']";
					                                        	pathXML4 = pathXML3+"/"+subDetalles.item(t).getNodeValue();
					                                        	System.out.println("\t\t\t\t"+subDetalles.item(t).getNodeValue());
					                                        	
					                                        	typeNode = (String) xpath.compile(path4+"/@type").evaluate(doc, XPathConstants.STRING);
					                                        	typeError = (String) xpath.compile(path4+"/@typeError").evaluate(doc, XPathConstants.STRING);
							                                	if(typeNode.equals("STRING")){
							                                		//Reliza comparacion en el Cuarto Nivel para los type STRING
							                                		valorNodoCorreo = (String) xpath.compile(pathXML4+"/text()").evaluate(docCorreo, XPathConstants.STRING);
							                                		valorNodoSRI = (String) xpath.compile(pathXML4+"/text()").evaluate(docSRI, XPathConstants.STRING);
							                                		System.out.println("\t\t\t\t\t"+subDetalles.item(t).getNodeValue()+" de  Correo :"+valorNodoCorreo.trim());
							                                		System.out.println("\t\t\t\t\t"+subDetalles.item(t).getNodeValue()+" de  SRI :"+valorNodoSRI.trim());
							                                		System.out.println("\t\t\t\t\t"+pathXML4);
							                                		System.out.println(typeError);
							                                		
							                                	}else{
							                                		System.out.println("Se encontraron SubNodos no contenplados para el Nodo:"+pathXML4);
							                                	}
					
					                                        }
				                                	}
		                                    }//for (int h=0; h<Detalles.getLength(); h++)
	                            		}
	                        		}//for (int k=0; k<subNodesDetalles.getLength(); k++){
	
	                            }
	 
	                        }//for (int j=0; j<subNodes.getLength(); j++)
	    			}//if(typeNode.equals("STRING")){
            }//for (int i=0; i<nodes.getLength(); i++){
            
        } catch (Exception e) {
            System.out.println("LeerNodos.comaparaXml() >> Error : "+e.getMessage());
            return false;
        }
		return false;
	}*/
	

}

