package com.drg.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


import java.lang.IllegalArgumentException;

import com.drg.data.CustDetailsData;
import com.drg.data.UtilityPmtData;

import org.apache.log4j.Logger;

public class QueryTools {
	
	final static Logger logger = Logger.getLogger(QueryFileWriter.class);
	
	public Map <Integer,String> createCustDetailsUpdateQuery (Map <Integer,CustDetailsData> custDetailsData)
	{
		Map <Integer,String> custDetailsQueryLines = new HashMap <Integer,String> ();

		int clientRecordNum = custDetailsData.size();

    	if (logger.isDebugEnabled()){
			logger.debug("Entering in createCustDetailsUpdateQuery");
			logger.debug("clientRecordNum = " + clientRecordNum);
		}

		
		if (clientRecordNum >0){

				custDetailsData.forEach ((line,clientData)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
					CustDetailsData custDetails = clientData;
					
					/*
					Se asume que el orden de las columnas en el archivo es: 
					0: rut
					1: cuenta
					2: NombreCompleto
					3: TipoDireccion
					4: calle
					5: detalleDir
					6: numero
					7: comuna
					8: region

					*/
				
					if (custDetails!=null) {
					
					String rut = clientData.getRut();
					if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}
					
					String cuenta = clientData.getCuenta();
					if (logger.isDebugEnabled()){
						logger.debug("Cuenta:  " + cuenta);
					}
	
					String nombreCompleto = clientData.getNombreCompleto();
					if (logger.isDebugEnabled()){
						logger.debug("NombreCompleto:  " + nombreCompleto);
					}
					
					String tipoDireccion = clientData.getTipoDireccion();
					if (logger.isDebugEnabled()){
						logger.debug("TipoDireccion:  " + tipoDireccion);
					}
					
					String calle = clientData.getCalle();
					if (logger.isDebugEnabled()){
						logger.debug("Calle:  " + calle);
					}
					
					String detalleDir = clientData.getDetalleDir();
					if (logger.isDebugEnabled()){
						logger.debug("DetalleDir:  " + detalleDir);
					}
					
					String numero = clientData.getNumero();
					if (logger.isDebugEnabled()){
						logger.debug("Numero:  " + numero);
					}
					
					String comuna = clientData.getComuna();
					if (logger.isDebugEnabled()){
						logger.debug("Comuna:  " + comuna);
					}
					
					String region = clientData.getRegion();
					if (logger.isDebugEnabled()){
						logger.debug("Region:  " + region);
					}

					
					//TODO: Completar la salida con la query y los valores correctos
					//TODO: Validar que los campos no vienen nulos o vacíos antes de montar la Query de esa línea.
					/* UPDATE INTELLECTCARDS.CUST_DETAILS Calle = 'ELARRAYANVILLAELSOL', 
					 * DETALLE = 'SANTIAGO,13',NUMERO = '327',
					 * COD_COMUNA = (SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = 'CURACAVI'), 
					 * COD_REGION = (SELECT region from INTELLECTCARDS.OCR_CITY_MB Where CITY ='CURACAVI') 
					 * Where RUT_CLIENTE ='6287919K';
					 */
					
					StringBuffer queryDataLineSBf = new StringBuffer();
		
					//@TODO: Añadir maker y checker con sus fechas
					
					if ((tipoDireccion != null)& (tipoDireccion.equalsIgnoreCase("H"))){
					
						queryDataLineSBf.append("UPDATE INTELLECTCARDS.CUST_DETAILS SET ");
						queryDataLineSBf = queryDataLineSBf.append("STMT_MAIL_INSTR = '" + tipoDireccion.toUpperCase() + "',");
						queryDataLineSBf = queryDataLineSBf.append("CALLE = '" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("DETALLE = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("NUMERO = '" + numero + "',");
						queryDataLineSBf = queryDataLineSBf.append("COD_COMUNA = (SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append("COD_REGION = (SELECT region from INTELLECTCARDS.OCR_CITY_MB Where CITY ='" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append("MAKER_DT = sysdate ");
						queryDataLineSBf = queryDataLineSBf.append("Where RUT_CLIENTE ='"+ rut + "';");
						
						
	
					}else if (!tipoDireccion.equalsIgnoreCase("")){
						
						queryDataLineSBf.append("UPDATE INTELLECTCARDS.CUST_DETAILS SET ");
						queryDataLineSBf = queryDataLineSBf.append("STMT_MAIL_INSTR = '" + tipoDireccion.toUpperCase() + "',");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_CALLE = '" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_DETALLEDIRECCION = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_NUMERO = '" + numero + "',");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_CODCOMUNA = (SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_CODREGION = (SELECT region from INTELLECTCARDS.OCR_CITY_MB Where CITY ='" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append("MAKER_DT = sysdate ");
						queryDataLineSBf = queryDataLineSBf.append("Where RUT_CLIENTE ='"+ rut + "';");
					}
				
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					queryDataLineSBf.append("UPDATE INTELLECTCARDS.CDMST SET ");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_1 = '" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_2 = '" + numero + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_3 = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_4 = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("CITY = (SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
					queryDataLineSBf = queryDataLineSBf.append("COUNTRY = '1' ");
					queryDataLineSBf = queryDataLineSBf.append("Where CIVIL_ID ='"+ rut + "';");
	
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					
					String queryDataLine =queryDataLineSBf.toString();
					
					if (logger.isDebugEnabled()){
						logger.debug("Línea " + line);
						logger.debug("consulta " + queryDataLine);
					}

					
					custDetailsQueryLines.put(line, queryDataLine);
				}//fin de if (custDetails!=null) {

				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de cliente de entrada vacios");
			logger.error("Datos de cliente de entrada vacios");
		}
		
		return custDetailsQueryLines;
		
	}//Fin de createCustDetailsUpdateQuery
	
	public Map <Integer,String> createCustDetailsInsertQuery (Map <Integer,CustDetailsData> custDetailsData)
	{
		Map <Integer,String> custDetailsQueryLines = new HashMap <Integer,String> ();

		int clientRecordNum = custDetailsData.size();

		if (clientRecordNum >0){

				custDetailsData.forEach ((line,clientData)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
					CustDetailsData custDetails = clientData;
					
					/*
					Se asume que el orden de las columnas en el archivo es: 
					0: rut
					1: cuenta
					2: NombreCompleto
					3: TipoDireccion
					4: calle
					5: detalleDir
					6: numero
					7: comuna
					8: region
					9: codPostal
					10: primerNombre
					11: segundoNombre
					12: primerApellido
					13: segundoApellido
					14: indEECCMail
					15: email
					16: codDireccion
					17: motivoRechazo
					18: descMotivoRechazo
					*/
				
					if (custDetails!=null) {
					
					String rut = clientData.getRut();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}
					
					String cuenta = clientData.getCuenta();
			    	if (logger.isDebugEnabled()){
						logger.debug("Cuenta:  " + cuenta);
					}
	
					String nombreCompleto = clientData.getNombreCompleto();
			    	if (logger.isDebugEnabled()){
						logger.debug("NombreCompleto:  " + nombreCompleto);
					}
					
					String tipoDireccion = clientData.getTipoDireccion();
			    	if (logger.isDebugEnabled()){
						logger.debug("TipoDireccion:  " + tipoDireccion);
					}
					
					String calle = clientData.getCalle();
			    	if (logger.isDebugEnabled()){
						logger.debug("Calle:  " + calle);
					}
					
					String detalleDir = clientData.getDetalleDir();
			    	if (logger.isDebugEnabled()){
						logger.debug("DetalleDir:  " + detalleDir);
					}
					
					String numero = clientData.getNumero();
			    	if (logger.isDebugEnabled()){
						logger.debug("Numero:  " + numero);
					}
					
					String comuna = clientData.getComuna();
			    	if (logger.isDebugEnabled()){
						logger.debug("Comuna:  " + comuna);
					}

			    	String region = clientData.getRegion();
			    	if (logger.isDebugEnabled()){
						logger.debug("Region:  " + region);
					}
					
					String codPostal = clientData.getCodPostal();
			    	if (logger.isDebugEnabled()){
						logger.debug("CodPostal:  " + codPostal);
					}

			    	String primerNombre = clientData.getPrimerNombre();
			    	if (logger.isDebugEnabled()){
						logger.debug("PrimerNombre:  " + primerNombre);
					}

					String segundoNombre = clientData.getSegundoNombre();
			    	if (logger.isDebugEnabled()){
						logger.debug("SegundoNombre:  " + segundoNombre);
					}

					String primerApellido = clientData.getPrimerApellido();
			    	if (logger.isDebugEnabled()){
						logger.debug("PrimerApellido:  " + primerApellido);
					}

					String segundoApellido = clientData.getSegundoApellido();
			    	if (logger.isDebugEnabled()){
						logger.debug("SegundoApellido:  " + segundoApellido);
					}

					String indEECCMail = clientData.getIndEECCMail();
			    	if (logger.isDebugEnabled()){
						logger.debug("IndEECCMail:  " + indEECCMail);
					}

					String email = clientData.getEmail();
			    	if (logger.isDebugEnabled()){
						logger.debug("Email:  " + email);
					}

					Map <Integer,String> mailParts = CommonTools.splitMail(email);
					
					String email_usuario = mailParts.get(0);
					String email_servidor = mailParts.get(1);
					
					String codDireccion = clientData.getCodDireccion();
			    	if (logger.isDebugEnabled()){
						logger.debug("CodDireccion:  " + codDireccion);
					}

					String motivoRechazo = clientData.getMotivoRechazo();
			    	if (logger.isDebugEnabled()){
						logger.debug("MotivoRechazo:  " + motivoRechazo);
					}

					String descMotivoRechazo = clientData.getDescMotivoRechazo();
			    	if (logger.isDebugEnabled()){
						logger.debug("descMotivoRechazo:  " + descMotivoRechazo);
					}

	
					
					//TODO: Completar la salida con la query y los valores correctos
					//TODO: Validar que los campos no vienen nulos o vacíos antes de montar la Query de esa línea.
/*					INSERT INTO INTELLECTCARDS.CUST_DETAILS (RUT_CLIENTE,SEXO,FEC_NACIMIENTO,PRIMER_NOMBRE,SEGUNDO_NOMBRE, PRIMER_APELLIDO, 
							SEGUNDO_APELLIDO,IND_EECC_EMAIL,EMAIL_USUARIO, EMAIL_SERVIDOR,COD_DIRECCION, MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC)
							values ('169899754',(Select cd.sex from intellectcards.cdmst cd where cd.civil_id ='169899754'),(Select to_date(cd.birth_date,'yyyyMMdd') 
							from intellectcards.cdmst cd
							where cd.civil_id ='169899754'),'LIDIA','SOLEDAD','GARAY','ABALLAY','N',null,null,'002','0',null)
*/					
					StringBuffer queryDataLineSBf = new StringBuffer();
					
					if ((tipoDireccion != null)& (tipoDireccion.equalsIgnoreCase("H"))){
						
						queryDataLineSBf.append("INSERT INTO INTELLECTCARDS.CUST_DETAILS (RUT_CLIENTE,SEXO,FEC_NACIMIENTO,PRIMER_NOMBRE,SEGUNDO_NOMBRE, PRIMER_APELLIDO, "
								+ "SEGUNDO_APELLIDO,IND_EECC_EMAIL,EMAIL_USUARIO, EMAIL_SERVIDOR,COD_DIRECCION, MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC,STMT_MAIL_INSTR,"
								+ "CALLE,DETALLE,NUMERO,COD_COMUNA,COD_REGION,MAKER_DT)");
						queryDataLineSBf = queryDataLineSBf.append("values ('"+ rut + "',");
						queryDataLineSBf = queryDataLineSBf.append("(Select cd.sex from intellectcards.cdmst cd where cd.civil_id ='"+ rut +"'AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+cuenta+"'),");
						queryDataLineSBf = queryDataLineSBf.append("(Select to_date(cd.birth_date,'yyyyMMdd') from intellectcards.cdmst cd where cd.civil_id ='"+ rut +"'AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+cuenta+"'),");
						queryDataLineSBf = queryDataLineSBf.append("'"+ primerNombre + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ segundoNombre + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ primerApellido + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ segundoApellido + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ indEECCMail + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ email_usuario + "',");//EMAIL_USUARIO
						queryDataLineSBf = queryDataLineSBf.append("'"+ email_servidor + "',");//EMAIL_SERVIDOR
						queryDataLineSBf = queryDataLineSBf.append("'"+ codDireccion + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ motivoRechazo + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ descMotivoRechazo + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + tipoDireccion.toUpperCase() + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + numero + "',");
						queryDataLineSBf = queryDataLineSBf.append("(SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append("(SELECT region from INTELLECTCARDS.OCR_CITY_MB Where CITY ='" + comuna + "')),");
						queryDataLineSBf = queryDataLineSBf.append("sydate ;");
	
					}else if (!tipoDireccion.equalsIgnoreCase("")){
						
						
						queryDataLineSBf.append("INSERT INTO INTELLECTCARDS.CUST_DETAILS (RUT_CLIENTE,SEXO,FEC_NACIMIENTO,PRIMER_NOMBRE,SEGUNDO_NOMBRE, PRIMER_APELLIDO, "
								+ "SEGUNDO_APELLIDO,IND_EECC_EMAIL,EMAIL_USUARIO, EMAIL_SERVIDOR,COD_DIRECCION, MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC,STMT_MAIL_INSTR,"
								+ tipoDireccion + "_CALLE,"+tipoDireccion +"_DETALLEDIRECCION,"+ tipoDireccion + "_NUMERO,"+tipoDireccion + "_CODCOMUNA,"+tipoDireccion + "_CODREGION,MAKER_DT)");
						queryDataLineSBf = queryDataLineSBf.append("values ('"+ rut + "',");
						queryDataLineSBf = queryDataLineSBf.append("(Select cd.sex from intellectcards.cdmst cd where cd.civil_id ='"+ rut +"'AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+cuenta+"'),");
						queryDataLineSBf = queryDataLineSBf.append("(Select to_date(cd.birth_date,'yyyyMMdd') from intellectcards.cdmst cd where cd.civil_id ='"+ rut +"'AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+cuenta+"'),");
						queryDataLineSBf = queryDataLineSBf.append("'"+ primerNombre + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ segundoNombre + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ primerApellido + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ segundoApellido + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ indEECCMail + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ email_usuario + "',");//EMAIL_USUARIO
						queryDataLineSBf = queryDataLineSBf.append("'"+ email_servidor + "',");//EMAIL_SERVIDOR
						queryDataLineSBf = queryDataLineSBf.append("'"+ codDireccion + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ motivoRechazo + "',");
						queryDataLineSBf = queryDataLineSBf.append("'"+ descMotivoRechazo + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + tipoDireccion.toUpperCase() + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
						queryDataLineSBf = queryDataLineSBf.append("'" + numero + "',");
						queryDataLineSBf = queryDataLineSBf.append("(SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
						queryDataLineSBf = queryDataLineSBf.append("(SELECT region from INTELLECTCARDS.OCR_CITY_MB Where CITY ='" + comuna + "')),");
						queryDataLineSBf = queryDataLineSBf.append("sydate ;");
						}
				
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					queryDataLineSBf.append("UPDATE INTELLECTCARDS.CDMST SET ");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_1 = '" + CommonTools.specialCharactersCorrector(calle,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_2 = '" + numero + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_3 = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("ADDR_LINE_4 = '" + CommonTools.specialCharactersCorrector(detalleDir,'\'') + "',");
					queryDataLineSBf = queryDataLineSBf.append("CITY = (SELECT city_code from INTELLECTCARDS.OCR_CITY_MB Where CITY = '" + comuna + "'),");
					queryDataLineSBf = queryDataLineSBf.append("COUNTRY = '1' ");
					queryDataLineSBf = queryDataLineSBf.append("Where CIVIL_ID ='"+ rut + "';");
	
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					
					String queryDataLine =queryDataLineSBf.toString();
					
					if (logger.isDebugEnabled()){
						logger.debug("Línea " + line);
						logger.debug("consulta " + queryDataLine);
					}
					
					custDetailsQueryLines.put(line, queryDataLine);
				}//fin de if (custDetails!=null) {

				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de cliente de entrada vacios");
			logger.error("Datos de cliente de entrada vacios");
		}
		
		return custDetailsQueryLines;
		
	}//Fin de createCustDetailsInsertQuery
	
	
	public Map <Integer,String> createCustDetailsBasicInsertQuery (Map <Integer,CustDetailsData> custDetailsData)
	{
		/* This method creates a basic Cust_details record using RUT, CUST_NO, STMT_MAIL_INSTR and the client data available in CDMST*/
		
		Map <Integer,String> custDetailsQueryLines = new HashMap <Integer,String> ();

		int clientRecordNum = custDetailsData.size();

		if (clientRecordNum >0){

				custDetailsData.forEach ((line,clientData)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
					CustDetailsData custDetails = clientData;
					
					/*
					Se asume que el orden de las columnas en el archivo es: 
					0: rut
					1: cuenta
					2: NombreCompleto
					3: TipoDireccion
					4: calle
					5: detalleDir
					6: numero
					7: comuna
					8: region
					9: codPostal
					10: primerNombre
					11: segundoNombre
					12: primerApellido
					13: segundoApellido
					14: indEECCMail
					15: email
					16: codDireccion
					17: motivoRechazo
					18: descMotivoRechazo
					*/
				
					if (custDetails!=null) {
					
					String rut = clientData.getRut();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}
					
					String cuenta = clientData.getCuenta();
			    	if (logger.isDebugEnabled()){
						logger.debug("Cuenta:  " + cuenta);
					}
	
					String tipoDireccion = clientData.getTipoDireccion();
			    	if (logger.isDebugEnabled()){
						logger.debug("TipoDireccion:  " + tipoDireccion);
					}

					StringBuffer queryDataLineSBf = new StringBuffer();

/*					Sample Query:
						
					INSERT INTO INTELLECTCARDS.CUST_DETAILS (RUT_CLIENTE,SEXO,FEC_NACIMIENTO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,
							PRIMER_APELLIDO, SEGUNDO_APELLIDO,IND_EECC_EMAIL,EMAIL_USUARIO, EMAIL_SERVIDOR,COD_DIRECCION,STMT_MAIL_INSTR,
							O_CALLE,O_NUMERO,O_DETALLEDIRECCION,
							MAKER_DT)
							values('171305330',
							(SELECT SEX FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							to_date((SELECT birth_date FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),'yyyymmdd'),
							(Select FIRST_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							null,
							(Select FATHER_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(Select MAIDEN_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(Select STMT_MAIL_OPTION FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(SELECT SUBSTR (EMAIL_ID, 0, INSTR (EMAIL_ID, '@') - 1) FROM INTELLECTCARDS.CDMST  WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(SELECT SUBSTR (EMAIL_ID, INSTR (EMAIL_ID, '@') + 1 )FROM INTELLECTCARDS.CDMST  WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							null,
							(Select STMT_MAIL_INSTR FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(SELECT ADDR_LINE_1 FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'), 
							(SELECT SUBSTR(ADDR_LINE_2,1,15)FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							(SELECT ADDR_LINE_3 FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '171305330' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '4001281766'),
							sysdate);
							commit;
					
*/					
					queryDataLineSBf.append("INSERT INTO INTELLECTCARDS.CUST_DETAILS (RUT_CLIENTE, SEXO,FEC_NACIMIENTO,PRIMER_NOMBRE,SEGUNDO_NOMBRE," + 
							"PRIMER_APELLIDO, SEGUNDO_APELLIDO, IND_EECC_EMAIL, EMAIL_USUARIO, EMAIL_SERVIDOR, COD_DIRECCION, STMT_MAIL_INSTR,");

					if ((tipoDireccion != null)& (tipoDireccion.equalsIgnoreCase("H"))){
						
						queryDataLineSBf = queryDataLineSBf.append("CALLE, NUMERO, DETALLE, ");
	
					}else if (!tipoDireccion.equalsIgnoreCase("")){
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_CALLE,");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_NUMERO,");
						queryDataLineSBf = queryDataLineSBf.append(tipoDireccion + "_DETALLEDIRECCION,");
					}
					
					queryDataLineSBf = queryDataLineSBf.append("COD_PAIS, EST_CIVIL, ES_DIR_EECC, ");
					queryDataLineSBf = queryDataLineSBf.append("MAKER_DT) ");

					queryDataLineSBf = queryDataLineSBf.append("values('"+ rut +"',");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT SEX FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					
					queryDataLineSBf = queryDataLineSBf.append("(SELECT to_date(birth_date,'yyyymmdd') FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(Select FIRST_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("null,");
					queryDataLineSBf = queryDataLineSBf.append("(Select FATHER_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(Select MAIDEN_NAME FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(Select STMT_MAIL_OPTION FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT SUBSTR (EMAIL_ID, 0, INSTR (EMAIL_ID, '@') - 1) FROM INTELLECTCARDS.CDMST  WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT SUBSTR (EMAIL_ID, INSTR (EMAIL_ID, '@') + 1 )FROM INTELLECTCARDS.CDMST  WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("null,");
					queryDataLineSBf = queryDataLineSBf.append("(Select STMT_MAIL_INSTR FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT ADDR_LINE_1 FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'), ");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT SUBSTR(ADDR_LINE_2,1,15)FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT ADDR_LINE_3 FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT COUNTRY FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("(SELECT MARITAL_STATUS FROM INTELLECTCARDS.CDMST WHERE CIVIL_ID = '"+ rut +"' AND NVL (PARENT_CARD_NUM, 'N') = 'N' AND CUST_NO = '"+ cuenta +"'),");
					queryDataLineSBf = queryDataLineSBf.append("'S',");
					queryDataLineSBf = queryDataLineSBf.append("sysdate);");
					queryDataLineSBf = queryDataLineSBf.append(newline);
					queryDataLineSBf = queryDataLineSBf.append("commit;");
	
					
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					
					String queryDataLine =queryDataLineSBf.toString();
					
					if (logger.isDebugEnabled()){
						logger.debug("Línea " + line);
						logger.debug("consulta " + queryDataLine);
					}
					
					custDetailsQueryLines.put(line, queryDataLine);
				}//fin de if (custDetails!=null) {

				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de cliente de entrada vacios");
			logger.error("Datos de cliente de entrada vacios");
		}
		
		return custDetailsQueryLines;
		
	}//Fin de createCustDetailsBasicInsertQuery

	public Map <Integer,String> createPATDEsafiliationQuery(Map <Integer, UtilityPmtData> utilityPmtData, String requestName){
		
		Map <Integer,String> utilityPmtQueryLines = new HashMap <Integer,String> ();

		int recordNum = utilityPmtData.size();

		if (recordNum >0){

			utilityPmtData.forEach ((line,utilityPmtD)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
					UtilityPmtData utilityPmt = utilityPmtD;
					
					/*
					Se asume que el orden de las columnas en el archivo es: 
					0: rut
					1: numcertificado
					2: codProducto
					3: rubro
					4: producto
					5: fechaEmision
					6: fechaDesafiliacion
					7: numtarjeta
					8: nombreAsegurado
					9: rutCia
					*/
				
					if (utilityPmt!=null) {
					
					String rut = utilityPmt.getRut();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}

					
					String rubro = utilityPmt.getRubro();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rubro:  " + rubro);
					}
	
					StringBuffer queryDataLineSBf = new StringBuffer();

/*					Sample Query:
						

			UPDATE INTELLECTCARDS.UTILITY_PYMT SET VERIFY_FLAG = 'D',RECORD_STATUS = 'D',MAKER = 'MTS8136', MAKER_DT = TO_CHAR(SYSDATE,'YYYYMMDD'),

			VERIFIER = 'SCRIPT', VERIFIER_DT = TO_CHAR(SYSDATE,'YYYYMMDD') WHERE RUT= '59567845' AND SERVICE_CODE = '204';
							commit;
					
*/	
					if (((rut != null)& (!"".equalsIgnoreCase(rut))) || ((rubro != null)& (!"".equalsIgnoreCase(rubro)))){

						queryDataLineSBf.append("UPDATE INTELLECTCARDS.UTILITY_PYMT SET VERIFY_FLAG = 'D',RECORD_STATUS = 'D',MAKER = '"+ requestName +"', "
								+ "MAKER_DT = TO_CHAR(SYSDATE,'YYYYMMDD'), VERIFIER = 'SCRIPT', VERIFIER_DT = TO_CHAR(SYSDATE,'YYYYMMDD') "
								+ "WHERE RUT= '"+ rut +"' AND SERVICE_CODE = '"+rubro+"';");
						queryDataLineSBf = queryDataLineSBf.append(newline);
						queryDataLineSBf = queryDataLineSBf.append("commit;");
	
					}else {
						
							logger.fatal("En la línea: " + line);

						
						if ((rut != null)& (!"".equalsIgnoreCase(rut))) {
							
							logger.fatal("El valor del rut está vacio");
							
						} 

						if ((rubro != null)& (!"".equalsIgnoreCase(rubro))){
				
							logger.fatal("El valor del rubro está vacio");
							}
					}
					
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					
					String queryDataLine =queryDataLineSBf.toString();
					
					if (logger.isDebugEnabled()){
						logger.debug("Línea " + line);
						logger.debug("consulta " + queryDataLine);
					}
					
					utilityPmtQueryLines.put(line, queryDataLine);
				}//fin de if (utilityPmt!=null) 
				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de PAT de entrada vacios");
			logger.error("Datos de PAT de entrada vacios");
		}
		
		return utilityPmtQueryLines;

	}//End of createPATDEsafiliationQuery
	
	//TODO: Hacer el backup y rollback genérico pasándole el listado de tablas a respaldar

	public Map <Integer,String> createCustDetailsBackupQuery (String requestName, String processDate, String schema, Map <Integer,String> tablas )
	{
		Map <Integer,String> backupQueryLines = new HashMap <Integer,String> ();
		
		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {
			if ((tablas!=null) && (!tablas.isEmpty())) {

				tablas.forEach((pos,tabla)->{
					if (logger.isDebugEnabled()){
						logger.debug("Posicion : " + pos + " valor : " + tabla);
					}

					String query = "CREATE TABLE "+ schema + "." + tabla + "_"+ requestName +"_"+ processDate +" AS SELECT * FROM "+ schema + "." + tabla +";";
					
					backupQueryLines.put(pos, query);
					
				});
				
			}else {
				System.out.println ("Tables name not Informed");
				logger.error("Tables name not Informed");
				return null;
			}			
		}else {
			System.out.println ("RequestName not Informed");
			logger.error ("RequestName not Informed");
			return null;
		}
		
		return backupQueryLines;				
	}//Fin de createCustDetailsBackupQuery
	
	public Map <Integer,String> createCustDetailsRollbackQuery (String requestName, String processDate, String schema, Map <Integer,String> tablas)
	{
		Map <Integer,String> rollbackQueryLines = new HashMap <Integer,String> ();
		
		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {
			if ((tablas!=null) && (!tablas.isEmpty())) {

				tablas.forEach((pos,tabla)->{
					String query = "";
					String newline = System.getProperty("line.separator");
					
					if (!tabla.equalsIgnoreCase("CDMST")) {

						//@TODO: crear la lógica que genera una línea de select a insert desde la CUST_DETAILS de respaldo a la principal 
//						Probar si funciona la siguiente linea:
//							INSERT INTO INTELLECTCARDS.CDMST SELECT * FROM INTELLECTCARDS.CDMST_7805_20181103 WHERE RUT = "";

/*						query = "TRUNCATE TABLE "+ schema +"." + tabla +";";
						query = query + newline;
						query = query + "INSERT INTO "+ schema +"." + tabla +" SELECT * FROM "+ schema +"." + tabla +"_"+ requestName +"_"+ processDate +";";
						query = query + newline;
						query = query + "COMMIT;";
*/					}

					rollbackQueryLines.put(pos, query);
					
				});
				
			}else {
				System.out.println ("Tables name not Informed");
				logger.error("Tables name not Informed");
				return null;
			}			
		}else {
			System.out.println ("RequestName not Informed");
			logger.error ("RequestName not Informed");
			return null;
		}
		
		return rollbackQueryLines;				
		
	}//createCustDetailsRollbackQuery

	public Map <Integer,String> cdmstRollbackQueryCreator(String requestName, String processDate, String schema, Map <Integer,CustDetailsData> custDetailsData, Map <Integer,String> currentQueryLines ) {

		String query = "";
		String tabla = "CDMST";
		String newline = System.getProperty("line.separator");
		
		
		
		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {

			if ((processDate!=null) && (!"".equalsIgnoreCase(processDate))) {

				Integer pos = new Integer (0);
				
//					query = "TRUNCATE TABLE "+ schema +"." + tabla +";";
//					query = query + newline;
//					query = query + "INSERT INTO "+ schema +"." + tabla +" SELECT * FROM "+ schema +"." + tabla +"_"+ requestName +"_"+ processDate +";";
//					query = query + newline;
//					query = query + "COMMIT;";
				//@TODO: crear la lógica que genera una línea de select a insert desde la CDMST de respaldo a la principal 
//				Probar si funciona la siguiente linea:
//					INSERT INTO INTELLECTCARDS.CDMST SELECT * FROM INTELLECTCARDS.CDMST_7805_20181103 WHERE RUT = "";
				
				currentQueryLines.put(pos, query);

				
			}else {
				System.out.println ("RequestName not Informed");
				logger.error("Tables name not Informed");
				return null;
			}
				
		}else {
			System.out.println ("RequestName not Informed");
			logger.error ("RequestName not Informed");
			return null;
		}
		
		return currentQueryLines;				
		
	}//Find de cdmstRollbackQueryCreator
	
	
}//end of QueryTools
