package com.recepcion.entidades;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-07T01:36:03.867-0500")
@StaticMetamodel(FacDocRecepcion.class)
public class FacDocRecepcion_ {
	public static volatile SingularAttribute<FacDocRecepcion, FacDocRecepcionPK> id;
	public static volatile SingularAttribute<FacDocRecepcion, String> ambiente;
	public static volatile SingularAttribute<FacDocRecepcion, String> correo;
	public static volatile SingularAttribute<FacDocRecepcion, String> estado;
	public static volatile SingularAttribute<FacDocRecepcion, Date> fecha;
	public static volatile SingularAttribute<FacDocRecepcion, Date> fechaAutorizacion;
	public static volatile SingularAttribute<FacDocRecepcion, String> idComp;
	public static volatile SingularAttribute<FacDocRecepcion, String> numeroAutorizacion;
	public static volatile SingularAttribute<FacDocRecepcion, byte[]> pdfImag;
	public static volatile SingularAttribute<FacDocRecepcion, String> razonSocialProv;
	public static volatile SingularAttribute<FacDocRecepcion, String> tipoEmision;
	public static volatile SingularAttribute<FacDocRecepcion, BigDecimal> total;
	public static volatile SingularAttribute<FacDocRecepcion, String> typeError;
	public static volatile SingularAttribute<FacDocRecepcion, String> version;
	public static volatile SingularAttribute<FacDocRecepcion, String> xmlDoc;
}
