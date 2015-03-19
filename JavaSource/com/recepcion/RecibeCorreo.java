package com.recepcion;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

import com.recepcion.entidades.FacBitacoraEmail;
import com.recepcion.entidades.FacBitacoraEmailPK;
import com.recepcion.entidades.FacDocRecepcion;
import com.recepcion.entidades.FacDocRecepcionPK;
import com.recepcion.entidades.LogBitacoraEmail;
import com.recepcion.entidades.LogDocRecepcion;
import com.recepcion.servicios.BitacoraEmailServicio;
import com.recepcion.servicios.DocRecepcionServicio;
import com.recepcion.servicios.LogBitacoraEmailServicio;
import com.recepcion.servicios.LogDocRecepcionServicio;
import com.recepcion.util.Util;
import com.recepcion.util.UtilMessage;

import ec.gob.sri.comprobantes.util.ArchivoUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;
import java.math.BigDecimal;

public class RecibeCorreo {
	  private static	String xmlConfigCorreoEntrante	 = "xmlConfigCorreo.xml";
	  private static	String urlMailIn		= null;
	  private static	String userMail			= null;
	  private static	String passMail	 		= null;
	  private static	String protocolMAilIn	= null;
	  private static	String hostMailIn		= null;
	  private static	String portMailIn		= null;
	  private static	String folderMail	 	= null;
	  public  static	String pathDownloadZip	= null;
	  public  static	String pahtDownloadXML	= null;
	  public  static	String pahtDownloadSRI	= null;
	  public  static	String pahtDownloadPDF  = null;
	  private static	String pathAutorizados	= null;
	  private static	String pahtNoAutorizados= null;
	  private static	String pahtFirmados		= null;
	  private static	String ambiente			= null;
	  private static	String pathDirTmp	    = System.getProperty("java.io.tmpdir");
	  
	  public static FacDocRecepcion facDocRecepcion	= null;

	  
	  public RecibeCorreo() {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder;
	        org.w3c.dom.Document doc = null;
	        String pathXML = "/parametros";
	            try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(xmlConfigCorreoEntrante);
		            System.out.println("RecibeCorreo.RecibeCorreo() >> Archivo de configuracion para correo entrante :"+xmlConfigCorreoEntrante);
		       	 
		            // Create XPathFactory object
		            XPathFactory xpathFactory = XPathFactory.newInstance();
		 
		            // Create XPath object
		            XPath xpath = xpathFactory.newXPath();
		            urlMailIn = (String) xpath.compile(pathXML+"/urlMailIn/text()").evaluate(doc, XPathConstants.STRING);
		            userMail = (String) xpath.compile(pathXML+"/userMail/text()").evaluate(doc, XPathConstants.STRING);
		            passMail = (String) xpath.compile(pathXML+"/passMail/text()").evaluate(doc, XPathConstants.STRING);
					protocolMAilIn = (String) xpath.compile(pathXML+"/protocolMAilIn/text()").evaluate(doc, XPathConstants.STRING);
					hostMailIn = (String) xpath.compile(pathXML+"/hostMailIn/text()").evaluate(doc, XPathConstants.STRING);
					portMailIn = (String) xpath.compile(pathXML+"/portMailIn/text()").evaluate(doc, XPathConstants.STRING);
					folderMail = (String) xpath.compile(pathXML+"/folderMail/text()").evaluate(doc, XPathConstants.STRING);
					ambiente		  =  (String) xpath.compile(pathXML+"/ambiente/text()").evaluate(doc, XPathConstants.STRING);
					
					File dirDirTmp = new File(pathDirTmp);
					
					if(!dirDirTmp.exists()){
						dirDirTmp.mkdirs();
					}
					
					pathDownloadZip = pathDirTmp + (String) xpath.compile(pathXML+"/pathDownloadZip/text()").evaluate(doc, XPathConstants.STRING);
					pahtDownloadXML = pathDirTmp +  (String) xpath.compile(pathXML+"/pahtDownloadXML/text()").evaluate(doc, XPathConstants.STRING);
					pahtDownloadSRI = pathDirTmp +  (String) xpath.compile(pathXML+"/pahtDownloadSRI/text()").evaluate(doc, XPathConstants.STRING);
					pahtDownloadPDF = pathDirTmp +  (String) xpath.compile(pathXML+"/pahtDownloadPDF/text()").evaluate(doc, XPathConstants.STRING);
					pathAutorizados	= pathDirTmp +   (String) xpath.compile(pathXML+"/pathAutorizados/text()").evaluate(doc, XPathConstants.STRING);
					pahtNoAutorizados = pathDirTmp +   (String) xpath.compile(pathXML+"/pahtNoAutorizados/text()").evaluate(doc, XPathConstants.STRING);
					pahtFirmados	  = pathDirTmp +  (String) xpath.compile(pathXML+"/pahtFirmados/text()").evaluate(doc, XPathConstants.STRING);
					
					
					
					File dirZip = new File(pathDownloadZip);
					File dirXML = new File(pahtDownloadXML);
					File dirSRI = new File(pahtDownloadSRI);
					File dirPDF = new File(pahtDownloadPDF);
					File dirAutorizados = new File(pathAutorizados);
					File dirNoAutorizados = new File(pahtNoAutorizados);
					File dirFirmados = new File(pahtFirmados);

					
					if(!dirZip.exists()){
						dirZip.mkdirs();
					}
					if(!dirXML.exists()){
						dirXML.mkdirs();
					}
					if(!dirSRI.exists()){
						dirSRI.mkdirs();
					}
					
					if(!dirPDF.exists()){
						dirPDF.mkdirs();
					}
					if(!dirAutorizados.exists()){
						dirAutorizados.mkdirs();
					}
					if(!dirNoAutorizados.exists()){
						dirNoAutorizados.mkdirs();
					}
					if(!dirFirmados.exists()){
						dirFirmados.mkdirs();
					}
					

					
		            if(urlMailIn==null){
		            	urlMailIn=protocolMAilIn+"://"+userMail+":"+passMail+"@"+hostMailIn+":"+portMailIn+"/"+folderMail;
						System.out.println(" RecibeCorreo.RecibeCorreo() >> Armando URL para conexion de correo entrante  : "+urlMailIn);
					}
					
		            
				} catch (ParserConfigurationException e) {
					System.out.println(" RecibeCorreo.RecibeCorreo() >> Error:"+e.getMessage());
				} catch (SAXException e) {
					System.out.println(" RecibeCorreo.RecibeCorreo() >> Error:"+e.getMessage());
				} catch (IOException e) {
					System.out.println(" RecibeCorreo.RecibeCorreo() >> Error:"+e.getMessage());
	            } catch (XPathExpressionException e) {
	            	System.out.println(" RecibeCorreo.RecibeCorreo() >> Error:"+e.getMessage());
					e.printStackTrace();
				}
	            
	  
	  }      
	  
	    public static void main(String argv[])
	    {
	     RecibeCorreo rc = new RecibeCorreo();
	     try {

	      Properties props = System.getProperties();
	      props.put("mail.imaps.partialfetch", false);
	      //props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      //props.setProperty("mail.imap.socketFactory.port","110");
	      
	      Session session = Session.getDefaultInstance(props);
	      Store store = null;
	      if (urlMailIn != null) {
		       URLName urln = new URLName(urlMailIn);
		       store = session.getStore(urln);
		       store.connect();
	      }
	      
	      // Open the Folder
	      String mbox = folderMail;
	      Folder folder = store.getDefaultFolder();
	      if (folder == null) {
		       System.out.println(" RecibeCorreo.RecibeCorreo() >> Error: No default folder");
		       System.exit(1);
	      }

	      folder = folder.getFolder(mbox);
	      if (!folder.exists()) {
		       System.out.println("RecibeCorreo.RecibeCorreo() >> "+mbox +" does not exist");
		       System.exit(1);
	      }
	      
	      folder.open(Folder.READ_WRITE);
	      int totalMessages = folder.getUnreadMessageCount();
	      System.out.println("Unread COUNT:"+folder.getUnreadMessageCount());
	      
	      if (totalMessages == 0) {
		       System.out.println("RecibeCorreo.RecibeCorreo() >> Empty folder");
		       folder.close(false);
		       store.close();
		       System.exit(1);
	      }

	      
	      //Filtro los no leidos
	      Flags seen = new Flags(Flags.Flag.SEEN);
	      FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
	      
	      
	      // Attributes & Flags for ALL messages ..
	      //Message[] msgs = ufolder.getMessagesByUID(1, UIDFolder.LASTUID);
	      // Attributes & Flags for ALL messages ..
	      Message[] msgs = folder.search(unseenFlagTerm);
	      
	      // Use a suitable FetchProfile
	      FetchProfile fp = new FetchProfile();
	      fp.add(FetchProfile.Item.ENVELOPE);
	      fp.add(FetchProfile.Item.FLAGS);
	      
	      folder.fetch(msgs, fp);
	      System.out.println("Listando Mensajes no leidos en la bandeja :"+ folderMail);
	      for (int i = 0; i < msgs.length; i++) {
		       System.out.println("====================================================");
		       boolean flagServicioWS = rc.testWSConsulta();
		       if(!flagServicioWS){
		    	   System.out.println("Durmiendo servicio");
			   }else{
			       String msgId = ((MimeMessage) msgs[i]).getMessageID().toString();
			       String lvError = null;
			       List<File> filesAttch = rc.downloadAttachMsg(msgs[i]);
			       if(filesAttch==null){
			    	   lvError = "RecibeCorreo () >> EL Msg ID :: "+msgId+" no trajo archivos XML adjuntos";
			    	   System.out.println(lvError);
					   continue;
			       }{
				    	   //Valido XMl que se hayan retornado en la Lista
		                   for (int j = 0; j < filesAttch.size(); j++) {
		                	   File fileXmlUnZip = filesAttch.get(j);
			   					//Funcion que valida el formato y si es  correcto continue con la validacion de informacion contra el SRI
			   					//Si me retorna False --> salgo y retorno False 
		                	   
		                	   String  []infoAutorizacion = null; 
		                	   String   claveAccessoCompuesta=null;
		                	   claveAccessoCompuesta = rc.obtieneClave(fileXmlUnZip);
		                	   
		                	   if (claveAccessoCompuesta==null){
		                		    lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error al validar formato del XML:"+fileXmlUnZip.getAbsolutePath();
			   						System.out.println(lvError);
			   						rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, lvError, "FORMATO-CLAVE-INCORRECCTO", null);
			   						continue;
			                	  }else{
			                		  infoAutorizacion = rc.obtieneClave(fileXmlUnZip).split("\\|");
			                	  }
		                	   
		                	   fileXmlUnZip = rc.ValidaFormatoXML(fileXmlUnZip,infoAutorizacion[1]);
		                	   //Valido el XMl contenido 
			   					if(fileXmlUnZip==null){
			   						lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error al validar formato del XML";
			   						System.out.println(lvError);
			   						rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, lvError, "FORMATO-INCORRECCTO",null);
			   						continue;
			   					}else{
			   					   rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, null, "FORMATO-CORRECCTO",null);
			   					   //
				   					   List<String> ListaResultadosComparacion = rc.ValidaXMLconSRI(fileXmlUnZip);
				                	   lvError = "Resuldado de la comparacion para el Archivo :"+fileXmlUnZip.getAbsolutePath()+" :"+(ListaResultadosComparacion!=null?"Exitosa":"Fallida");
				                       System.out.println(lvError);
				                       
			                       if(ListaResultadosComparacion!=null){
			                    	   rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, lvError, "CONSULTA-SRI-CORRECTO",ListaResultadosComparacion);
			                    	   ListaResultadosComparacion.add(5, fileXmlUnZip.getAbsolutePath());
				                       ListaResultadosComparacion.add(6, msgId);
				                       //Guardo en la Base de Datos independientemente si hubo no inconsistencias en la comparacion
				                       try{
				                       rc.RegistroXML(ListaResultadosComparacion,ListaResultadosComparacion,msgs[i]);
				                       }catch(Exception ex){
				                    	   rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, ex.getMessage(), "ERROR-BITACORA",null);
				                    	   continue;
				                       }
			                       }else{
			                    	   rc.bitacoraValidacionXML(msgs[i], fileXmlUnZip, lvError, "CONSULTA-SRI-INCORRECTO",null);
			                       }
			                       
			                       
			   					}   
		                   }
			       }
			       
			       System.out.println("====================================================");
			       }//Fin if(!flagServicioWS)
	      }

	      folder.close(false);
	      store.close();
	     } catch (Exception ex) {
	      System.out.println("RecibeCorreo.RecibeCorreo() >> Oops, got exception!  " + ex.getMessage());
	      ex.printStackTrace();
	     }
	    }
	    

//====================================================================================================
//		Funcion para bitacorizar Msg leidos con la lista de sus archivos XML adjuntos     
//====================================================================================================		    
	   private void RegistroXML(List<String> listaResultados,List<String> listaResultadoComparacion,Message msg)
	   {
	       DocRecepcionServicio docRecpServ = new DocRecepcionServicio();
	       FacDocRecepcion docRecp = new FacDocRecepcion();
	       SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyyy"); 
	       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder;
	        org.w3c.dom.Document docSRI = null;//, docCorreo = null;
	        
	        String typeError = listaResultados.get(0);
		    String tramaErrorCampo = listaResultados.get(1);
		    String tramaErrorValor = listaResultados.get(2);
		    String pahtXMLSri = listaResultados.get(3);
		    String pahtXMLRespuestaSri  = listaResultados.get(4);
		    String pahtXMLCorreo = listaResultados.get(5);
		    String messageId  = listaResultados.get(6);
		    
		    String ambiente ="",  tipoEmision="", rucProveedor="",codDoc="", estab="", ptoEmi="",
		    		secuencial="",razonSocialProveedor="",importeTotal="",version="",estado="",rucReceptor ="",stringFechaEmision = "";
		    //S stringechaEmision = "";
		    //,fechaAutorizacion="",numeroAutorizacion="",claveAcceso="",
		    Date fechaEmision = new Date();
		    
	            try {
					builder = factory.newDocumentBuilder();
					docSRI = builder.parse(pahtXMLSri);
					//docCorreo = builder.parse(pahtXMLCorreo);
					
		            // Create XPathFactory object
		            XPathFactory xpathFactory = XPathFactory.newInstance();
		 
		            // Create XPath object
		            XPath xpath = xpathFactory.newXPath();
		            ambiente = (String) xpath.compile("//infoTributaria/ambiente/text()").evaluate(docSRI, XPathConstants.STRING);
		            tipoEmision = (String) xpath.compile("//infoTributaria/tipoEmision/text()").evaluate(docSRI, XPathConstants.STRING);
		            rucProveedor = (String) xpath.compile("//infoTributaria/ruc/text()").evaluate(docSRI, XPathConstants.STRING);
		            codDoc = (String) xpath.compile("//infoTributaria/codDoc/text()").evaluate(docSRI, XPathConstants.STRING);
		            estab = (String) xpath.compile("//infoTributaria/estab/text()").evaluate(docSRI, XPathConstants.STRING);
		            ptoEmi = (String) xpath.compile("//infoTributaria/ptoEmi/text()").evaluate(docSRI, XPathConstants.STRING);
		            secuencial = (String) xpath.compile("//infoTributaria/secuencial/text()").evaluate(docSRI, XPathConstants.STRING);
		            razonSocialProveedor = (String) xpath.compile("//infoTributaria/razonSocial/text()").evaluate(docSRI, XPathConstants.STRING);
		            stringFechaEmision = (String) xpath.compile("//infoFactura/fechaEmision/text()").evaluate(docSRI, XPathConstants.STRING);
		            System.out.println("La fecha emision..."+stringFechaEmision);
		            fechaEmision = dt.parse(stringFechaEmision);
		            
		            
		            version = (String) xpath.compile("//@version").evaluate(docSRI, XPathConstants.STRING);
		            if(codDoc.equals("01")){
		            	importeTotal = (String) xpath.compile("//./importeTotal/text()").evaluate(docSRI, XPathConstants.STRING);
		            	rucReceptor  = (String) xpath.compile("//identificacionComprador/text()").evaluate(docSRI, XPathConstants.STRING);
		            }
		            if(codDoc.equals("04")){
		            	importeTotal = (String) xpath.compile("//./valorModificacion/text()").evaluate(docSRI, XPathConstants.STRING);
		            	rucReceptor  = (String) xpath.compile("//identificacionComprador/text()").evaluate(docSRI, XPathConstants.STRING);
		            }
		            //===========================================================================================
		            //fechaAutorizacion = facDocRecepcion.getFechaAutorizacion().toString();
		            //numeroAutorizacion = facDocRecepcion.getNumeroAutorizacion();
		            //claveAcceso = facDocRecepcion.getId().getClaveAcceso();
		            estado = facDocRecepcion.getEstado();
		            
		            
	            }catch(Exception ex){
	            	System.err.println("RecibeCorreo.RegistroXML() >> Error al extraer la informacion de tributaria del XML:"+pahtXMLSri);
	            }
	          
		       System.out.println("Bitacora >> Message Id: "+messageId+" XML Correo =>> "+pahtXMLSri+" XML SRI =>> "+pahtXMLCorreo+" Tipo de Error :"+typeError);
		       System.out.println("trama Valor :"+typeError);
		       System.out.println("trama Campo :"+tramaErrorCampo);
		       System.out.println("trama Valor :"+tramaErrorValor);
		       
		       docRecp.setAmbiente(ambiente);
		       docRecp.setEstado(estado);
		       docRecp.setFecha(new Date());
		       docRecp.setNumeroAutorizacion(facDocRecepcion.getNumeroAutorizacion());
		       docRecp.setFechaAutorizacion(facDocRecepcion.getFechaAutorizacion());
		       //=====================================
		       FacDocRecepcionPK facDocRcpPK = facDocRecepcion.getId();
		       facDocRcpPK.setCodDoc(codDoc);
		       facDocRcpPK.setEstab(estab);
		       facDocRcpPK.setIdEmail(messageId);
		       facDocRcpPK.setNombreArchivo(pahtXMLSri);
		       facDocRcpPK.setPtoEmi(ptoEmi);
		       facDocRcpPK.setRucProveedor(rucProveedor);
		       facDocRcpPK.setSecuencial(secuencial);
		       facDocRcpPK.setRucReceptor(rucReceptor);
		       //=======================================
		       docRecp.setId(facDocRcpPK);
		       docRecp.setRazonSocialProv(razonSocialProveedor);
		       docRecp.setTipoEmision(tipoEmision);
		       docRecp.setTotal(new BigDecimal(importeTotal));
		       docRecp.setVersion(version);
		       docRecp.setXmlDoc(ArchivoUtils.archivoToString(pahtXMLRespuestaSri));
		       docRecp.setTypeError(typeError);
		       docRecp.setFechaEmision(fechaEmision);
	try {
					docRecp.setCorreo(msg.getFrom()[0].toString());
				} catch (MessagingException e1) {
					System.err.println("RecibeCorreo.RegistroXML() >> Error al intentar consultar el Id del Message :"+e1.getMessage());				}
					File pdfFile = UtilMessage.DescargarPDFdeMail(msg);
				try {
					if(pdfFile!=null){
					   byte[] pdfByte = Util.readBytesFromFile(pdfFile);
					   docRecp.setPdfImag(pdfByte);
					}
				} catch (IOException ex) {
					System.err.println("RecibeCorreo.RegistroXML() >> Error al intentar guardar PDF en la Base de Datos :"+ex.getMessage());
				}
			     
		    try {
		    	docRecpServ.insertarFacDocRecepcion(docRecp);
				
			} catch (Exception e) {
				System.err.println("RecibeCorreo.RegistroXML() >> Error General al intentar guardar en la Base de Datos :"+e.getMessage());
	}
}	    
//====================================================================================================
//		Funcion para bitacorizar Msg leidos con la lista de sus archivos XML adjuntos     
//====================================================================================================		    
	   public void bitacoraMsg(Message message, List<File> listaFileAttach, String observacion, String estado) {
		   String lvError= null;;
	       Address[] fromAddress;
	       String tramaListaFileAttach ="";
	       LogBitacoraEmailServicio logServEmail = new LogBitacoraEmailServicio();
	       LogBitacoraEmail logBitEmail = new LogBitacoraEmail();
	       BitacoraEmailServicio ServEmail = new BitacoraEmailServicio();
	       FacBitacoraEmail BitEmail = new FacBitacoraEmail();
		    try{   
		       fromAddress = message.getFrom();
		       String from = fromAddress[0].toString();
		       String subject = message.getSubject();
		       //String sentDate = message.getSentDate().toString();
		       String messageId  = ((MimeMessage) message).getMessageID().toString();
		       
				  logBitEmail.setIdEmail(messageId);
				  logBitEmail.setEstado(estado);
				  logBitEmail.setSubject(subject);
				  logBitEmail.setFromEmail(from);
				  logBitEmail.setFechaEmail(message.getSentDate());
				  logBitEmail.setFechaProcesado(new Date());
				  logBitEmail.setObservacion(observacion);
				  
				  BitEmail.setId(new FacBitacoraEmailPK() {
					public void setIdEmail(String messageId) {
						super.setIdEmail(messageId);
					}
				  });
				  FacBitacoraEmailPK bitEmailPK = new FacBitacoraEmailPK();
				  bitEmailPK.setIdEmail(messageId);
				  BitEmail.setId(bitEmailPK);;
				  BitEmail.setEstado(estado);
				  BitEmail.setSubject(subject);
				  BitEmail.setFromEmail(from);
				  BitEmail.setFechaEmail(message.getSentDate());
				  BitEmail.setFechaProcesado(new Date());
				  
		       for (int i = 0; i < listaFileAttach.size(); i++) {
		    	   tramaListaFileAttach += listaFileAttach.get(i)+"|";
		       }
		       logBitEmail.setAdjunto(tramaListaFileAttach);
		       BitEmail.setAdjunto(tramaListaFileAttach);
		       System.out.println("Archivos descargados _: "+tramaListaFileAttach+" Estado :"+estado);
		       logServEmail.insertarEmail(logBitEmail);
		       ServEmail.insertarEmail(BitEmail);
			} catch (MessagingException e) {
				lvError="RecibeCorreo.R bitacoraMsg(Message message) >> Error : " + e.getMessage();
				System.out.println(lvError);
			} 
	   }

//====================================================================================================
//		Funcion para bitacorizar Msg leidos con la lista de sus archivos XML adjuntos     
//====================================================================================================		    
	   private void bitacoraValidacionXML(Message message, File fileAttachXML, String observacion, String estado,List<String> tramaResultado) {
		   String lvError= null;
	       LogDocRecepcionServicio logServDocRecp = new LogDocRecepcionServicio();
	       LogDocRecepcion logDocRecp = new LogDocRecepcion();
		   
		    try{   
		       String messageId  = ((MimeMessage) message).getMessageID().toString();
		       logDocRecp.setIdEmail(messageId);
		       logDocRecp.setNombreArchivo(fileAttachXML.getAbsolutePath()
		    		   );
		       logDocRecp.setFechaProceso(new Date());
		       if(tramaResultado!=null){
		    	   logDocRecp.setCampoComparacion(tramaResultado.get(1));
		    	   logDocRecp.setValorComparacion(tramaResultado.get(2));
		    	   logDocRecp.setTypeError(tramaResultado.get(0));
		       }
			   logDocRecp.setDetalle(observacion);
			   logDocRecp.setEstado(estado);
			   
		       System.out.println("Bitacora >> Message Id:"+messageId+" XML: "+fileAttachXML.getName() +" Observación :"+observacion+" Estado :"+estado);
		       logServDocRecp.insertarLogDocRecepcion(logDocRecp);
			} catch (MessagingException e) {
				lvError="RecibeCorreo.bitacoraValidacionXML() >> Error : " + e.getMessage();
				System.out.println(lvError);
				
			} 
	   }	   
//====================================================================================================
//		Funcion para descargar un Message enviado como parametro el cual se encargara de descargar 
//		archivos XML y ZIP adjuntos para luego validarl la informacion contra el SRI 	    
//====================================================================================================		  
		private List<File> downloadAttachMsg(Message message) {
			
			String lvError= null;
			List<File> listaFileXml = new ArrayList<File>();
			Address[] fromAddress;
			try {
				bitacoraMsg(message, listaFileXml, lvError, "READING");
		        String contentType = message.getContentType();
		        fromAddress = message.getFrom();
	
		        // print out details of each message
		        System.out.println("MESSAGE ID # :" +  ((MimeMessage) message).getMessageID().toString() + ":");
		        System.out.println("\t From: " + fromAddress[0].toString());
		        System.out.println("\t Subject: " + message.getSubject());
		        System.out.println("\t Sent Date: " + message.getSentDate().toString());
		        //System.out.println("\t Message: " + messageContent);
		        
		        // store attachment file name, separated by comma
		        String attachFiles = "";

	        if (contentType.contains("multipart")) {
	            // content may contain attachments
	        
	            Multipart multiPart = (Multipart) message.getContent();
	            int numberOfParts = multiPart.getCount();
	            for (int partCount = 0; partCount < numberOfParts; partCount++) {
	                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
	                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	                	
	                    // this part is attachment
	                    String fileName = part.getFileName();
	                    attachFiles += fileName + ", ";
	                    
	                    //Verifico extension del archivo para que solo descargue .ZIP o .XML
	                    if(fileName.toLowerCase().endsWith(".xml")){
	                    	File fileXml = new File(pahtDownloadXML + File.separator + fileName);
	                    	
	                    	System.out.println(part.getEncoding());
	                    	part.saveFile(fileXml);
	                    	//System.out.println((BAS part.getContent());
	                    	/*
	                    	//if (part.getContent() instanceof BASE64DecoderStream)
	                    	//{
	                    	    BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) part.getContent();
	                    	    DataOutputStream output = new DataOutputStream(
	                    	            new BufferedOutputStream(new FileOutputStream(fileXml)));
	                    	    byte[] buffer = new byte[1024];
	                            int bytesRead;
	                            while ((bytesRead = base64DecoderStream.read(buffer)) != -1) {
	                               output.write(buffer, 0, bytesRead);
	                            }
	                            /*
	                    	    byte[] byteArray = IOUtils. .toByteArray(base64DecoderStream );
	                    	    byte[] encodeBase64 = Base64.encodeBase64(byteArray);
	                    	    base64Content[0] = new String(encodeBase64, "UTF-8");
	                    	    base64Content[1] = getContentTypeString(part);
	                    	    */
	                    	//}
	                    	
	                    	//part.saveFile(fileXml);
	                    	listaFileXml.add(fileXml);
	                    	//boolean bValidaXMl = ValidaXMLconSRI(fileXml);
	                    	//System.out.println("Resuldado de la comparacion para el Archivo :"+fileXml.getAbsolutePath()+" :"+(bValidaXMl==true?"Exitosa":"Fallida"));
	                    }
	                    else if (fileName.toLowerCase().endsWith(".zip")){
	                       String zipFileNamePath = pathDownloadZip + File.separator + fileName;
	                       part.saveFile(zipFileNamePath);
	                       
	                       if (attachFiles.length() > 1) {
	       	                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
	       	               }
	                       //Los solo archivos .XML contenidos en el Archivo ZIP 
	                       List<String> listUnZipXML = unZipIt(zipFileNamePath,pahtDownloadXML);
	                       attachFiles += "[ ";
	                       for (int i = 0; i < listUnZipXML.size(); i++) {
	                    	   String pathFileXml = listUnZipXML.get(i);
	                    	   File fileXmlUnZip = new File(pathFileXml);
	                    	   listaFileXml.add(fileXmlUnZip);
	                    	   attachFiles += fileXmlUnZip.getName() + ", ";
		                    	//boolean bValidaXMl = ValidaXMLconSRI(fileXmlUnZip);
		                    	//System.out.println("Resuldado de la comparacion para el Archivo :"+fileXmlUnZip.getAbsolutePath()+" :"+(bValidaXMl==true?"Exitosa":"Fallida"));
	                       }
	                       
	                       if (attachFiles.length() > 1) {
		       	                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
		       	           }
	                       attachFiles += " ], ";
	                    }
	                }
	            }

	            if (attachFiles.length() > 1) {
	                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
	            }
	        }
	        
	        	System.out.println("\t Attachments: " + attachFiles);
	        	message.setFlag(Flags.Flag.SEEN, true);
	        	if(listaFileXml.size()<=0){
	        		lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Warning!! : Message ID:: "+((MimeMessage) message).getMessageID().toString()
	        				+" :: no trajo archivos XMl adjuntos.";
	        		bitacoraMsg(message, listaFileXml, lvError, "READ");
					System.out.println(lvError);
					return null;
	        	}else{
	        		//Si el msj trae archivos adjuntos
	        		bitacoraMsg(message, listaFileXml, null, "READ");
					//System.out.println(lvError);
	        		return listaFileXml;
	        	}
			} catch (MessagingException e) {
				try {
					message.setFlag(Flags.Flag.SEEN, false);
					} catch (MessagingException e1) {
						e1.printStackTrace();
				}
				lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Error : " + e.getMessage();
				bitacoraMsg(message, listaFileXml, lvError, "FAIL");
				System.out.println(lvError);
				return null;
			} catch (IOException e) {
				try {
					message.setFlag(Flags.Flag.SEEN, false);
					} catch (MessagingException e1) {
						e1.printStackTrace();
				}
				lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Error : " + e.getMessage();
				bitacoraMsg(message, listaFileXml, lvError, "FAIL");
				System.out.println(lvError);
				e.printStackTrace();
				return null;
			}
			
	       
		}
		
//====================================================================================================
//	Funcion que retorna una lista solo de los archivos .XML descompresos de un archivo .ZIP
//	Una vez procesado el archivo ZIP es renombrado con extension .OLD salvo sus excepciones		
//====================================================================================================		
	    public List<String> unZipIt(String nameZipFile, String outputFolder){
	    	List<String> listaFileXML = new ArrayList<String>();
	    	
	        byte[] buffer = new byte[1024];
	    
	        try{
	    
	       	//create output directory is not exists
	       	File folder = new File(nameZipFile);
	       	if(!folder.exists()){
	       		folder.mkdirs();
	       	}
	           
	       	File zipFile = new File(nameZipFile);
	       	//get the zip file content
	       	ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
	       	//get the zipped file list entry
	       	ZipEntry ze = zis.getNextEntry();
	    
	       	while(ze!=null){
	    
	       	   String fileName = ze.getName();
	      		
	      		//Verifico extension del archivo para que solo guarde .XML 
	              if (fileName.toLowerCase().endsWith(".xml") ){
	              	
	   		           File newFile = new File(outputFolder + File.separator + fileName);
	   		 
	   		           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	   		 
	   		            //create all non exists folders
	   		            //else you will hit FileNotFoundException for compressed folder
	   		            new File(newFile.getParent()).mkdirs();
	   		 
	   		            FileOutputStream fos = new FileOutputStream(newFile);             
	   		 
	   		            int len;
	   			            while ((len = zis.read(buffer)) > 0) {
	   			       		fos.write(buffer, 0, len);
	   			            }
	   		 
	   		            fos.close(); 
	   		            listaFileXML.add(newFile.getAbsolutePath());
	   		            
	              }
	               ze = zis.getNextEntry();
	       	}
	       	
	       	
	        zis.closeEntry();
	       	zis.close();
	       	//Renombro el archivo XML con una extension OLD para saber que el archivo ya paso el proceso de unZip
	       	//Si existe .OLD ya esciste y se descomprime el mismo ya no quedara con la extesion .OLd debido a que ya existe
	       	zipFile.renameTo(new File(zipFile+".old"));
	       	System.out.println("Done");
	       	return listaFileXML;
	    
	       }catch(IOException ex){
	    	   System.out.println("RecibeCorreo.unZipIt(String nameZipFile, String outputFolder) >> Error : "+ex.getMessage());
	          return null; 
	       }

      }

//====================================================================================================
//		Funcion para validar formato Basico del XML 
//		que tenga informacion tributaria
//====================================================================================================	    
	    
    private File ValidaFormatoXML(File XMLCorreo,String tipoEsquema){
    	String lvError = null;
    	String claveAccesso = null;
    	org.w3c.dom.Document doc = null;
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document documento = null;
        boolean flatError = false;
    	//Se crea el documento a traves del archivo
		try {
			
			//====================
	        
				builder = factory.newDocumentBuilder();
				doc = builder.parse(XMLCorreo);
				
				// Create XPathFactory object
	            XPathFactory xpathFactory = XPathFactory.newInstance();
	 
	            // Create XPath object
	            XPath xpath = xpathFactory.newXPath();
	            if(tipoEsquema.endsWith("1")){
	            String cdataXML = (String) xpath.compile("/autorizacion/comprobante/text()").evaluate(doc, XPathConstants.STRING);
	            File cDataFile = ArchivoUtils.stringToArchivo(pathDirTmp + XMLCorreo.getName(), cdataXML);
	            
	            
	    		/** Creamos el documento xml a partir del archivo File **/
	    		SAXBuilder constructorSAX = new SAXBuilder();
	    		documento = (Document)constructorSAX.build(cDataFile);
	    		XMLCorreo = cDataFile;
	    		
	        }else
	         if(tipoEsquema.endsWith("2")){
	        	/** Creamos el documento xml a partir del archivo File **/
	    		SAXBuilder constructorSAX = new SAXBuilder();
				documento = (Document)constructorSAX.build(XMLCorreo);
	         }else{
	        	 return null;
	         }
	        
			//====================
			
				/** Obtenemos el nodo raiz o principal **/
				Element nodoRaiz = documento.getRootElement();
				
				@SuppressWarnings("rawtypes")
				//List listaacomprobante = null;
				List listaacomprobante = nodoRaiz.getChildren("infoTributaria");
				/** verificando que sea de un formato sencillo en caso de ser True
				 * caso contrario filtra hasta llegar al punto  del documento**/
				
				if(nodoRaiz.getName().trim().equals("factura") || nodoRaiz.getName().trim().equals("comprobanteRetencion") || 
						nodoRaiz.getName().trim().equals("guiaRemision") || nodoRaiz.getName().trim().equals("notaCredito") ||
						 nodoRaiz.getName().trim().equals("notaDebito"))
				{
						
				}
				/* VPI - Valida Nodo Raiz en caso que no encuentre el tipo de documento  */
				else{	
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> El archivo :: " + XMLCorreo.getAbsolutePath() + " no tiene al información del tipo de documento";
					System.out.println(lvError);
					flatError = true;
			    }
				
				/** aqui va todo igual para en cualquier formato de xml **/
				
				if(listaacomprobante.size() == 0){
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> nombre del archivo:: " + XMLCorreo.getAbsolutePath() + " \n no tiene al información tributaria";
					System.out.println(lvError);
					flatError = true;
				}
				
				for (int i = 0; i < listaacomprobante.size(); i++) {
					   Element node = (Element) listaacomprobante.get(i);
					   System.out.println("Clave de acceso contenida en el XML "+XMLCorreo+ " CLAVE :" +node.getChildText("claveAcceso"));
					   claveAccesso = node.getChildText("claveAcceso");
					}
				
				if (claveAccesso==null){
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error : El archivo XML "+XMLCorreo.getAbsolutePath()+" no cuenta con Clave de Acceso.";
					System.out.println(lvError);
					flatError = true;
				}
				
		
				
				//Si pasa todas las validaciones de formato BASICAS retornamos true 
				//para continuar con las validaciones con el SRI

			} catch (Exception e) {
				System.out.println("RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error: "+ e.getMessage());
				e.printStackTrace();
				flatError = true;
		}
		if(flatError){
			return null;
		}else{
			return XMLCorreo;
		}
	}
    
    
    
//====================================================================================================
//	Funcion para validar Archivo XML recibido por correo. Se leera el archivo para obtener la clave de
//	acceso y consultarlo en el WS del SRI, para verificar si esta autorizado y validar que los TAGs 
//  que del XML devuelto por el SRI del documento electronica concidan con el recibido por correo
//====================================================================================================	    
	    private List<String> ValidaXMLconSRI(File XMLCorreo){
	    	String lvError = null;
	    	String claveAccesso = null;
	    	String secuencial = null;
	    	String[] infoAutorizacion = new String[10];
			String respAutorizacion = null;
			List<String> listaResultados = new ArrayList<String>();
			List<Object> respuestaCompuesta = new ArrayList<Object>();
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        Document documento = null;
	        facDocRecepcion = new FacDocRecepcion();
	        
	    	//Se crea el documento a traves del archivo
			try {

		    		/** Creamos el documento xml a partir del archivo File **/
		    		SAXBuilder constructorSAX = new SAXBuilder();
						documento = (Document)constructorSAX.build(XMLCorreo);

					
					/** Obtenemos el nodo raiz o principal **/
					Element nodoRaiz = documento.getRootElement();
					String tipoDocumento = nodoRaiz.getName();
					List list = nodoRaiz.getChildren("infoTributaria");

					for (int i = 0; i < list.size(); i++) {
						   Element node = (Element) list.get(i);
						   claveAccesso = node.getChildText("claveAcceso");
						   secuencial = node.getChildText("secuencial");
						}
					
					if (claveAccesso!=null && secuencial!=null){
					
						//Bitacora del Archivo XML en las Tablas
						
		        		  try{
		                	  //respAutorizacion = com.recepcion.AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAccesso,XMLCorreo.getName(),ambiente,pathAutorizados,pahtNoAutorizados,pahtFirmados);
		        			  respuestaCompuesta = com.recepcion.AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAccesso, XMLCorreo.getName(), ambiente, pathAutorizados, 4, 60000, secuencial);
		        			  respAutorizacion = respuestaCompuesta.get(0).toString();
		                	  System.out.println("RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Respuesta Ws:"+respAutorizacion);
		                	  
		                	  //System.out.println("Fin Envio2 Txt:::"+new Date());					                	 
		                	  if (respAutorizacion.equals("")){
		                		  infoAutorizacion[0] = "SIN-RESPUESTA";
		                	  }else{
		                		  infoAutorizacion = respAutorizacion.split("\\|");
		                		 
		                	  }
		                  }catch(Exception excep){
		                	  lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error al consultar Ws con documento ::  "+XMLCorreo.getAbsolutePath();
							  System.out.println(lvError);
		                	  excep.printStackTrace();				                	  
		                  }	
					}else{
						lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error : El archivo XML "+XMLCorreo.getAbsolutePath()+" no cuenta con Clave de Acceso.";
						System.out.println(lvError);
						return null;
					}
	        		  
		        		  if (infoAutorizacion[0].trim().equals("AUTORIZADO")) {
		        			  
		        			  //En el llamado a la funcion se setean la informacion en la Entidad DocRecepcion
		        			  //File XMLsri = com.recepcion.AutorizacionComprobantesWs.obtieneComprobanteXMLIndividual(claveAccesso, XMLCorreo.getName(),ambiente,pahtDownloadSRI);
		        			  File XMLsri = (File )respuestaCompuesta.get(1);
		        			  File respuestaCompletaSRI = (File )respuestaCompuesta.get(2);
		        			  if(XMLsri!=null && respuestaCompletaSRI!=null){
			        			  //Comparo la informacion de los dos Archivos XMl - El enviado por Correo y el Devuelto por el SRI 
			        			  LeerNodos leerNodos = new LeerNodos(XMLCorreo.getAbsolutePath(), XMLsri.getAbsolutePath());
			        			  listaResultados = leerNodos.compararXML(tipoDocumento);
			        			  //leerNodos.comaparaXml(XMLCorreo.getAbsolutePath(), XMLsri.getAbsolutePath(),tipoDocumento);
		        			  }
			        			  if(listaResultados==null){
			        				  	lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Fallo la comparacion de los archivos XML  : "+XMLCorreo.getAbsolutePath()+" <<=>> "+XMLsri.getAbsolutePath();
										System.out.println(lvError);
				        			  	return null;
			        			  }
			        			  //si la lista no es null la retorno
			        			  listaResultados.add(3,XMLsri.getAbsolutePath());
			        			  listaResultados.add(4,respuestaCompletaSRI.getAbsolutePath());
			        			  return listaResultados;
		        		  }else{
		        			  	lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> No se pudo validar Autorizacion del archivo : "+XMLCorreo.getAbsolutePath();
								System.out.println(lvError);
		        			  	return null;
		        		  }

				} catch (Exception e) {
				System.out.println("RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >>"+e.getMessage());
				return null;
			}
			
		}
	    
//====================================================================================================
//		Funcion para validar formato Basico del XML 
//		que tenga informacion tributaria
//====================================================================================================	    
	    
    private String obtieneClave(File XMLCorreo){
    	String lvError = null;
    	String claveAccesso = null;
    	org.w3c.dom.Document doc = null;
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document documento = null;
        
        String tipoEsquema = "0", respuesta = null;
    	//Se crea el documento a traves del archivo
        
        try{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(XMLCorreo);
			
			// Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();
 
            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            String cdataXML = (String) xpath.compile("/autorizacion/comprobante/text()").evaluate(doc, XPathConstants.STRING);
            File cDataFile = ArchivoUtils.stringToArchivo(pathDirTmp + XMLCorreo.getName(), cdataXML);
            
            
    		/** Creamos el documento xml a partir del archivo File **/
    		SAXBuilder constructorSAX = new SAXBuilder();
    		documento = (Document)constructorSAX.build(cDataFile);
    		tipoEsquema = "1";
    		
        }catch (Exception e) {
        	/** Creamos el documento xml a partir del archivo File **/
    		SAXBuilder constructorSAX = new SAXBuilder();
    		try {
				documento = (Document)constructorSAX.build(XMLCorreo);
				tipoEsquema = "2";
			} catch (JDOMException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
				e1.printStackTrace();
			}
		}
        

				/** Obtenemos el nodo raiz o principal **/
				Element nodoRaiz = documento.getRootElement();
				
				@SuppressWarnings("rawtypes")
				//List listaacomprobante = null;
				List listaacomprobante = nodoRaiz.getChildren("infoTributaria");
				/** verificando que sea de un formato sencillo en caso de ser True
				 * caso contrario filtra hasta llegar al punto  del documento**/
				
				if(nodoRaiz.getName().trim().equals("factura") || nodoRaiz.getName().trim().equals("comprobanteRetencion") || 
						nodoRaiz.getName().trim().equals("guiaRemision") || nodoRaiz.getName().trim().equals("notaCredito") ||
						 nodoRaiz.getName().trim().equals("notaDebito"))
				{
						
				}
				/* VPI - Valida Nodo Raiz en caso que no encuentre el tipo de documento  */
				else{	
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> El archivo :: " + XMLCorreo.getAbsolutePath() + " no tiene al información del tipo de documento";
					System.out.println(lvError);
					
			    }
				
				/** aqui va todo igual para en cualquier formato de xml **/
				
				if(listaacomprobante.size() == 0){
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> nombre del archivo:: " + XMLCorreo.getAbsolutePath() + " \n no tiene al información tributaria";
					System.out.println(lvError);
					
				}
				
				for (int i = 0; i < listaacomprobante.size(); i++) {
					   Element node = (Element) listaacomprobante.get(i);
					   System.out.println("Clave de acceso contenida en el XML "+XMLCorreo+ " CLAVE :" +node.getChildText("claveAcceso"));
					   claveAccesso = node.getChildText("claveAcceso");
					}
				
				if (claveAccesso==null){
					lvError = "RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Error : El archivo XML "+XMLCorreo.getAbsolutePath()+" no cuenta con Clave de Acceso.";
					System.out.println(lvError);
					
				}else{
					respuesta = claveAccesso+"|"+tipoEsquema;
				}
		return respuesta;
	}	    
	
    private boolean testWSConsulta() {
    	String lvError = null;
    	String[] infoAutorizacion = new String[10];
		String respAutorizacion = null;
		List<Object> respuestaCompuesta = new ArrayList<Object>();
		boolean servicioActivo = false;
    	try{
    	  	  //respAutorizacion = com.recepcion.AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAccesso,XMLCorreo.getName(),ambiente,pathAutorizados,pahtNoAutorizados,pahtFirmados);
    			  respuestaCompuesta = com.recepcion.AutorizacionComprobantesWs.autorizarComprobanteIndividual("0","test.xml", ambiente, pathDirTmp, 4, 60000, "0");
    			  respAutorizacion = respuestaCompuesta.get(0).toString();
    	  	  System.out.println("RecibeCorreo.ValidaFormatoXML(File XMLCorreo) >> Respuesta Ws:"+respAutorizacion);
    	  	  
    	  	  //System.out.println("Fin Envio2 Txt:::"+new Date());					                	 
    	  	  if (respAutorizacion.equals("")){
    	  		  infoAutorizacion[0] = "SIN-RESPUESTA";
    	  	  }else{
    	  		  infoAutorizacion = respAutorizacion.split("\\|");
    	  		 
    	  	  }
    	  	  
    	  	  if(infoAutorizacion[0].trim().equals("NO-EXISTE-DOCUMENTO")){
    	  		servicioActivo = true; 
    	  		 System.out.println(">> .::Ws de consulta Activo ::.");
    	  	  }else{
    	  		servicioActivo = false;  
    	  		System.out.println(">> .::Ws de consulta Inactivo ::.");
    	  	  }
	    }catch(Exception excep){
	  	  lvError = "RecibeCorreo.testWSConsulta() >> Error al consultar Ws  ::  "+excep.getMessage();
			  System.err.println(lvError);
	  	  excep.printStackTrace();				                	  
	    }

    	return servicioActivo;
	}
	    
}

