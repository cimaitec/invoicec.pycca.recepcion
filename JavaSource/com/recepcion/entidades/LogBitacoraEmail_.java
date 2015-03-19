package com.recepcion.entidades;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-07T01:36:03.884-0500")
@StaticMetamodel(LogBitacoraEmail.class)
public class LogBitacoraEmail_ {
	public static volatile SingularAttribute<LogBitacoraEmail, Long> id;
	public static volatile SingularAttribute<LogBitacoraEmail, String> idEmail;
	public static volatile SingularAttribute<LogBitacoraEmail, String> subject;
	public static volatile SingularAttribute<LogBitacoraEmail, String> fromEmail;
	public static volatile SingularAttribute<LogBitacoraEmail, Date> fechaEmail;
	public static volatile SingularAttribute<LogBitacoraEmail, Date> fechaProcesado;
	public static volatile SingularAttribute<LogBitacoraEmail, String> estado;
	public static volatile SingularAttribute<LogBitacoraEmail, String> adjunto;
	public static volatile SingularAttribute<LogBitacoraEmail, String> observacion;
	public static volatile SingularAttribute<LogBitacoraEmail, BigDecimal> reintentos;
}
