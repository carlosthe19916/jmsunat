package io.github.carlosthe19916.util;

public class SenderConstants {

    public static final String PREFIX = "SENDER_";


    public static final String WS_URL = PREFIX + "WS_URL";
    public static final String WS_OPERATION = PREFIX + "WS_OPERATION";

    public static final String SUNAT_USERNAME = PREFIX + "SUNAT_USERNAME";
    public static final String SUNAT_PASSWORD = PREFIX + "SUNAT_PASSWORD";

    /**
     * Bill Service
     */
    public static final String FILE_NAME = PREFIX + "FILE_NAME";
    public static final String PARTY_TYPE = PREFIX + "PARTY_TYPE";

    /**
     * Bill Consult Service
     */
    public static final String RUC_COMPROBANTE = "JMSUNAT_SENDER_RUC_COMPROBANTE";
    public static final String TIPO_COMPROBANTE = "JMSUNAT_SENDER_TIPO_COMPROBANTE";
    public static final String SERIE_COMPROBANTE = "JMSUNAT_SENDER_SERIE_COMPROBANTE";
    public static final String NUMERO_COMPROBANTE = "JMSUNAT_SENDER_NUMERO_COMPROBANTE";

    /**
     * Bill Valid Service
     */
    public static final String TIPO_DOCUMENTO_RECEPTOR = "JMSUNAT_SENDER_TIPO_DOCUMENTO_RECEPTOR";
    public static final String NUMERO_DOCUMENTO_RECEPTOR = "JMSUNAT_SENDER_NUMERO_DOCUMENTO_RECEPTOR";
    public static final String FECHA_EMISION = "JMSUNAT_SENDER_FECHA_EMISION";
    public static final String IMPORTE_TOTAL = "JMSUNAT_SENDER_IMPORTE_TOTAL";
    public static final String NUMERO_AUTORIZACION = "JMSUNAT_SENDER_NUMERO_AUTORIZACION";

    /**
     * Operations
     */
    public static final String SEND_BILL = "SEND_BILL";
    public static final String GET_STATUS = "GET_STATUS";
    public static final String SEND_SUMMARY = "SEND_SUMMARY";
    public static final String SEND_PACK = "SEND_PACK";
    public static final String GET_STATUS_CDR = "GET_STATUS_CDR";

}
