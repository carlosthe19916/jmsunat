# Common info

All JMS should being used sending this headers:

1. **SUNAT_JMS_USERNAME**: String (Clave Sol)
2. **SUNAT_JMS_PASSWORD**: String (Clave Sol)

# BillService

## sendBill

When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 3 elements:

1. **SUNAT_JMS_FILE**: byte[]
2. **SUNAT_JMS_FILE_NAME**: String
3. **SUNAT_JMS_PARTY_TYPE**: String (Optional)
    
The JMS will return an **javax.jms.BytesMessage** which represents the **CDR** file
    
## sendSummary

When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 3 elements:

1. **SUNAT_JMS_FILE**: byte[]
2. **SUNAT_JMS_FILE_NAME**: String
3. **SUNAT_JMS_PARTY_TYPE**: String (Optional)
    
The JMS will return an **javax.jms.TextMessage** containing the **TICKET**

## sendPack

When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 3 elements:

1. **SUNAT_JMS_FILE**: byte[]
2. **SUNAT_JMS_FILE_NAME**: String
3. **SUNAT_JMS_PARTY_TYPE**: String (Optional)
    
The JMS will return an **javax.jms.TextMessage** containing the **TICKET**

## getStatus

When someone wants to use this method, the user must use a **javax.jms.TextMessage** and send the **TICKET** as body:

The JMS will return an **javax.jms.MapMessage** containing 2 elements:
 
 - **SUNAT_JMS_RESULT_CONTENT**: String
 - **SUNAT_JMS_RESULT_STATUS_CODE**: String
 
 
# BillConsultService
 
## getStatusCdr
 
When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 4 elements:
 
 1. **SUNAT_JMS_RUC_COMPROBANTE**: String
 2. **SUNAT_JMS_TIPO_COMPROBANTE**: String
 3. **SUNAT_JMS_SERIE_COMPROBANTE**: String
 3. **SUNAT_JMS_NUMERO_COMPROBANTE**: String
 
The JMS will return an **javax.jms.MapMessage** containing 3 elements:
  
  - **SUNAT_JMS_RESULT_CONTENT**: String
  - **SUNAT_JMS_RESULT_STATUS_CODE**: String
  - **SUNAT_JMS_RESULT_STATUS_MESSAGE**: String

# BillConsultService

## validaCDPcriterios

When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 4 elements:
 
 1. **SUNAT_JMS_RUC_COMPROBANTE**: String
 2. **SUNAT_JMS_TIPO_COMPROBANTE**: String
 3. **SUNAT_JMS_SERIE_COMPROBANTE**: String
 4. **SUNAT_JMS_NUMERO_COMPROBANTE**: String
 5. **SUNAT_JMS_TIPO_DOCUMENTO_RECEPTOR**: String
 6. **SUNAT_JMS_NUMERO_DOCUMENTO_RECEPTOR**: String
 7. **SUNAT_JMS_FECHA_EMISION**: String
 8. **SUNAT_JMS_IMPORTE_TOTAL**: String
 9. **SUNAT_JMS_NUMERO_AUTORIZACION**: String
 
The JMS will return an **javax.jms.MapMessage** containing 3 elements:
  
  - **SUNAT_JMS_RESULT_CONTENT**: String
  - **SUNAT_JMS_RESULT_STATUS_CODE**: String
  - **SUNAT_JMS_RESULT_STATUS_MESSAGE**: String
      
## verificaCPEarchivo

When someone wants to use this method, the user must use a **javax.jms.MapMessage** and send 3 elements:

1. **SUNAT_JMS_FILE**: byte[]
2. **SUNAT_JMS_FILE_NAME**: String
    
The JMS will return an **javax.jms.MapMessage** containing 3 elements:
  
  - **SUNAT_JMS_RESULT_CONTENT**: String
  - **SUNAT_JMS_RESULT_STATUS_CODE**: String
  - **SUNAT_JMS_RESULT_STATUS_MESSAGE**: String