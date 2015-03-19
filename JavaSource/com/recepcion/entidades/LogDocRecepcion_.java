package com.recepcion.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-07T01:36:03.888-0500")
@StaticMetamodel(LogDocRecepcion.class)
public class LogDocRecepcion_ {
	public static volatile SingularAttribute<LogDocRecepcion, Integer> id;
	public static volatile SingularAttribute<LogDocRecepcion, String> accion;
	public static volatile SingularAttribute<LogDocRecepcion, String> campoComparacion;
	public static volatile SingularAttribute<LogDocRecepcion, String> detalle;
	public static volatile SingularAttribute<LogDocRecepcion, String> estado;
	public static volatile SingularAttribute<LogDocRecepcion, Date> fechaProceso;
	public static volatile SingularAttribute<LogDocRecepcion, String> idEmail;
	public static volatile SingularAttribute<LogDocRecepcion, String> nombreArchivo;
	public static volatile SingularAttribute<LogDocRecepcion, Integer> reintento;
	public static volatile SingularAttribute<LogDocRecepcion, String> typeError;
	public static volatile SingularAttribute<LogDocRecepcion, String> valorComparacion;
}
