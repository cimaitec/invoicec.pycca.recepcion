CREATE TABLE fac_bitacora_email (ADJUNTO VARCHAR(255), ESTADO VARCHAR(255), "fechaEmail" DATE, "fechaProcesado" DATE, "fromEmail" VARCHAR(255), OBSERVACION VARCHAR(255), SUBJECT VARCHAR(255), REINTENTOS BIGINT NOT NULL, "idEmail" VARCHAR(255) NOT NULL, PRIMARY KEY (REINTENTOS, "idEmail"))
CREATE TABLE fac_doc_recepcion (AMBIENTE VARCHAR(255), CORREO VARCHAR(255), ESTADO VARCHAR(255), FECHA DATE, "fechaAutorizacion" DATE, "idComp" VARCHAR(255), "numeroAutorizacion" VARCHAR(255), "pdfImag" BYTEA, "razonSocialProv" VARCHAR(255), "tipoEmision" VARCHAR(255), TOTAL DECIMAL(38), "typeError" VARCHAR(255), VERSION VARCHAR(255), "xmlDoc" VARCHAR(255), "nombreArchivo" VARCHAR(255) NOT NULL, SECUENCIAL VARCHAR(255) NOT NULL, "claveAcceso" VARCHAR(255) NOT NULL, "idEmail" VARCHAR(255) NOT NULL, "ptoEmi" VARCHAR(255) NOT NULL, "rucReceptor" VARCHAR(255) NOT NULL, "codDoc" VARCHAR(255) NOT NULL, "rucProveedor" VARCHAR(255) NOT NULL, ESTAB VARCHAR(255) NOT NULL, PRIMARY KEY ("nombreArchivo", SECUENCIAL, "claveAcceso", "idEmail", "ptoEmi", "rucReceptor", "codDoc", "rucProveedor", ESTAB))
CREATE TABLE fac_general ("codUnico" VARCHAR(255) NOT NULL, "codTabla" VARCHAR(255), DESCRIPCION VARCHAR(255), "idGeneral" VARCHAR(255), "isActive" VARCHAR(255), PORCENTAJE VARCHAR(255), PRIMARY KEY ("codUnico"))
CREATE TABLE log_bitacora_email (ID BIGINT NOT NULL, ADJUNTO VARCHAR(255), ESTADO VARCHAR(255), "fechaEmail" DATE, "fechaProcesado" DATE, "fromEmail" VARCHAR(255), "idEmail" VARCHAR(255), OBSERVACION VARCHAR(255), REINTENTOS DECIMAL(38), SUBJECT VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE log_doc_recepcion (ID INTEGER NOT NULL, ACCION VARCHAR(255), "campoComparacion" VARCHAR(255), DETALLE VARCHAR(255), ESTADO VARCHAR(255), "fechaProceso" DATE, "idEmail" VARCHAR(255), "nombreArchivo" VARCHAR(255), REINTENTO INTEGER, "typeError" VARCHAR(255), "valorComparacion" VARCHAR(255), PRIMARY KEY (ID))
CREATE SEQUENCE seqLogDocRecepcion START WITH 1
CREATE SEQUENCE seqLogBitacoraEmail START WITH 1
