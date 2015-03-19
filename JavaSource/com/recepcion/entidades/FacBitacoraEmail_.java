package com.recepcion.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-09-07T01:36:03.860-0500")
@StaticMetamodel(FacBitacoraEmail.class)
public class FacBitacoraEmail_ {
	public static volatile SingularAttribute<FacBitacoraEmail, FacBitacoraEmailPK> id;
	public static volatile SingularAttribute<FacBitacoraEmail, String> adjunto;
	public static volatile SingularAttribute<FacBitacoraEmail, String> estado;
	public static volatile SingularAttribute<FacBitacoraEmail, Date> fechaEmail;
	public static volatile SingularAttribute<FacBitacoraEmail, Date> fechaProcesado;
	public static volatile SingularAttribute<FacBitacoraEmail, String> fromEmail;
	public static volatile SingularAttribute<FacBitacoraEmail, String> observacion;
	public static volatile SingularAttribute<FacBitacoraEmail, String> subject;
}
