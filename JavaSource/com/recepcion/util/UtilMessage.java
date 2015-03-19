package com.recepcion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import com.recepcion.RecibeCorreo;

public class UtilMessage {
	
//====================================================================================================
//	Funcion para descargar un Message enviado como parametro el cual se encargara de descargar 
//	archivos XML y ZIP adjuntos para luego validarl la informacion contra el SRI 	    
//====================================================================================================	
	
	public static File DescargarPDFdeMail(Message message) {
		List<File> ListaFilePDF = new ArrayList<File>();
		RecibeCorreo rc = new RecibeCorreo();
		String pahtDownloadPDF = rc.pahtDownloadPDF;
		String pathDownloadZip = rc.pathDownloadZip;
		File filePDF = null;

		String lvError= null;
		try {
			rc.bitacoraMsg(message, ListaFilePDF, lvError, "READING");
	        String contentType = message.getContentType();

        if (contentType.contains("multipart")) {
            // content may contain attachments
        
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                	
                    // this part is attachment
                    String fileName = part.getFileName();
                    
                    //Verifico extension del archivo para que solo descargue .ZIP o .PDF
                    if(fileName.toLowerCase().endsWith(".pdf")){
                    	filePDF = new File(pahtDownloadPDF + File.separator + fileName);
                    	part.saveFile(filePDF);
                    	//si encontro un archivo PDF lo retorna
                    	return filePDF;
                    }
                    else if (fileName.toLowerCase().endsWith(".zip")){
                       String zipFileNamePath = pathDownloadZip + File.separator + fileName;
                       part.saveFile(zipFileNamePath);
                       
                       //Los solo el archivo .PDF contenido en el Archivo ZIP 
                       filePDF = unZipItPDF(zipFileNamePath,pahtDownloadPDF);
                       ListaFilePDF.add(filePDF);
                       
                    }
                }
            }

        }
        
        	message.setFlag(Flags.Flag.SEEN, true);
        	if(ListaFilePDF.size()<=0){
        		lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Warning!! : Message ID:: "+((MimeMessage) message).getMessageID().toString()
        				+" :: no trajo archivos PDF adjuntos.";
        		rc.bitacoraMsg(message, ListaFilePDF, lvError, "READ");
				System.out.println(lvError);
				return null;
        	}else{
        		//Si el msj trae archivos adjuntos
        		rc.bitacoraMsg(message, ListaFilePDF, null, "READ");
				//System.out.println(lvError);
        		return filePDF;
        	}
		} catch (MessagingException e) {
			try {
				message.setFlag(Flags.Flag.SEEN, false);
				} catch (MessagingException e1) {
					e1.printStackTrace();
			}
			lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Error : " + e.getMessage();
			rc.bitacoraMsg(message, ListaFilePDF, lvError, "FAIL");
			System.out.println(lvError);
			return null;
		} catch (IOException e) {
			try {
				message.setFlag(Flags.Flag.SEEN, false);
				} catch (MessagingException e1) {
					e1.printStackTrace();
			}
			lvError="RecibeCorreo.downloadAttachMsg(Message message) >> Error : " + e.getMessage();
			rc.bitacoraMsg(message, ListaFilePDF, lvError, "FAIL");
			System.out.println(lvError);
			e.printStackTrace();
			return null;
		}
		
       
	}
	
//====================================================================================================
//Funcion que retorna una lista solo de los archivos .XML descompresos de un archivo .ZIP
//Una vez procesado el archivo ZIP es renombrado con extension .OLD salvo sus excepciones		
//====================================================================================================		
    private static File unZipItPDF(String nameZipFile, String outputFolder){
    	File filePDF = null;
    	
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
       	//get the zipped file unic entry
       	ZipEntry ze = zis.getNextEntry();
    
       	   String fileName = ze.getName();
      		
      		//Verifico extension del archivo para que solo guarde .XML 
              if (fileName.toLowerCase().endsWith(".pdf")){
              	
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
   		            filePDF = newFile;
              }
       	
        zis.closeEntry();
       	zis.close();
       	//Renombro el archivo ZIP con una extension OLD para saber que el archivo ya paso el proceso de unZip
       	//Si existe .OLD ya esciste y se descomprime el mismo ya no quedara con la extesion .OLd debido a que ya existe
       	zipFile.renameTo(new File(zipFile+".old"));
       	System.out.println("Done");

       }catch(IOException ex){
    	   System.out.println("RecibeCorreo.unZipIt(String nameZipFile, String outputFolder) >> Error : "+ex.getMessage());
       }
        
        return filePDF;
  }

}
