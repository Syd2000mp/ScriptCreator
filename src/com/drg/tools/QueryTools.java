package com.drg.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import com.drg.data.AddrsBlockData;
import com.drg.data.CustDetailsData;
import com.drg.data.PhoneMailData;
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
					if (((rut != null)& (!"".equalsIgnoreCase(rut))) && ((rubro != null)& (!"".equalsIgnoreCase(rubro)))){

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
	
	public Map <Integer,String> createAddrsBlockInsertQuery(Map <Integer, AddrsBlockData> addrsBlockData){
		
		Map <Integer,String> addrsBlockQueryLines = new HashMap <Integer,String> ();

		int recordNum = addrsBlockData.size();

		if (recordNum >0){

			addrsBlockData.forEach ((line,addrsBlockD)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
			    	AddrsBlockData addrsBlock = addrsBlockD;
					
			    	/*
			    	Se asume que el orden de las columnas en el archivo es: 
			    	0: rut
			    	1: codDireccion
			    	2: causalDevol
			    	*/
				
					if (addrsBlock!=null) {
					
					String rut = addrsBlock.getRut();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}

					
					String codDireccion = addrsBlock.getCodDireccion();
			    	if (logger.isDebugEnabled()){
						logger.debug("codDireccion:  " + codDireccion);
					}

					String causalDevol = addrsBlock.getCausalDevol();
			    	if (logger.isDebugEnabled()){
						logger.debug("causalDevol:  " + causalDevol);
					}

					StringBuffer queryDataLineSBf = new StringBuffer();

/*					Sample Query:
						
UPDATE CUST_DETAILS SET ADDR_BLOCK='Y', COD_DIRECCION = '2', 
MOTIVO_RECHAZ='7', MOTIVO_RECHAZ_DESC = (SELECT MEANING FROM INTELLECTCARDS.RE900 WHERE CNAME = 'STATEMENT_ADDR_BLOCK_REASON' AND CVALUE ='7') 
WHERE RUT_CLIENTE='25615867';
					
*/	
					if (((rut != null)& (!"".equalsIgnoreCase(rut))) && ((codDireccion != null)& (!"".equalsIgnoreCase(codDireccion)))&& ((causalDevol != null)& (!"".equalsIgnoreCase(causalDevol)))){

						queryDataLineSBf.append("UPDATE CUST_DETAILS SET ADDR_BLOCK='Y', COD_DIRECCION = '"+ codDireccion +"', "
								+ "MOTIVO_RECHAZ='"+causalDevol+"'," + "MOTIVO_RECHAZ_DESC = (SELECT MEANING FROM INTELLECTCARDS.RE900 "
								+ "WHERE CNAME = 'STATEMENT_ADDR_BLOCK_REASON' AND CVALUE ='" + causalDevol + "')"
								+ "WHERE RUT_CLIENTE='"+rut+"';");

						queryDataLineSBf = queryDataLineSBf.append(newline);
						queryDataLineSBf = queryDataLineSBf.append("commit;");
	
					}else {
						
							logger.fatal("En la línea: " + line);

						
						if ((rut != null)& (!"".equalsIgnoreCase(rut))) {
							
							logger.fatal("El valor del rut está vacio");
							
						} 

						if ((codDireccion != null)& (!"".equalsIgnoreCase(codDireccion))){
				
							logger.fatal("El valor del codigo de Direccion está vacio");
						}

						if ((causalDevol != null)& (!"".equalsIgnoreCase(causalDevol))){
							
							logger.fatal("El valor del causal de Devolucion está vacio");
						}
					}
					
					queryDataLineSBf = queryDataLineSBf.append(newline);
					
					
					String queryDataLine =queryDataLineSBf.toString();
					
					if (logger.isDebugEnabled()){
						logger.debug("Línea " + line);
						logger.debug("consulta " + queryDataLine);
					}
					
					addrsBlockQueryLines.put(line, queryDataLine);
				}//fin de if (utilityPmt!=null) 
				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de PAT de entrada vacios");
			logger.error("Datos de PAT de entrada vacios");
		}
		
		return addrsBlockQueryLines;

	}//End of createAddrsBlockInsertQuery	
	
	//TODO: Hacer el backup y rollback genérico pasándole el listado de tablas a respaldar

	public Map <Integer,String> createPhoneMailUpdateQuery(Map <Integer, PhoneMailData> phoneMailData, String requestName){
		
		Map <Integer,String> phoneMailQueryLines = new HashMap <Integer,String> ();

		int recordNum = phoneMailData.size();

		if (recordNum >0){

			phoneMailData.forEach ((line,phoneMailD)->{
					String newline = System.getProperty("line.separator");
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
			    	PhoneMailData phoneMaildata = phoneMailD;
					
					/*
					Se asume que el orden de las columnas en el archivo es: 
					0: rut
					1: phoneNum
					2: mailId
					3: serverId
					*/

				
					if (phoneMaildata!=null) {
					
					String rut = phoneMaildata.getRut();
			    	if (logger.isDebugEnabled()){
						logger.debug("Rut:  " + rut);
					}

					
					String phoneNum = phoneMaildata.getPhoneNum();
			    	if (logger.isDebugEnabled()){
						logger.debug("PhoneNum:  " + phoneNum);
					}
	
					
					String mailId = phoneMaildata.getMailId();
			    	if (logger.isDebugEnabled()){
						logger.debug("MailId:  " + mailId);
					}
	
					
					String serverId = phoneMaildata.getServerId();
			    	if (logger.isDebugEnabled()){
						logger.debug("ServerId:  " + serverId);
					}
	
					StringBuffer queryDataLineSBf = new StringBuffer();

/*					Sample Query:
						
"UPDATE intellectcards.cust_details 
SET EMAIL_USUARIO = 'dankaaracelly',EMAIL_SERVIDOR = 'gmail.com',
NUM_TELEFONO = '934280072',TIP_TELEFONO = 'C' Where RUT_CLIENTE = '190256715'; 

UPDATE intellectcards.cdmst SET EMAIL_ID = 'dankaaracelly@gmail.com', CELL_NO = '934280072', 
ADDR_PHONE_2 = '934280072' Where RUT = '190256715';"							commit;
					
*/	
					if ((rut != null)&& (!"".equalsIgnoreCase(rut))){
						
						if (((phoneNum != null)& (!"".equalsIgnoreCase(phoneNum)))
							&& ((mailId != null)& (!"".equalsIgnoreCase(mailId)))
							&& ((serverId != null)& (!"".equalsIgnoreCase(serverId)))
							){
	
							queryDataLineSBf.append("UPDATE intellectcards.cust_details "
									+ "SET EMAIL_USUARIO = '"+ mailId +"',EMAIL_SERVIDOR = '"+ serverId +"',IND_EECC_EMAIL = 'S', "
									+"NUM_TELEFONO = '"+ phoneNum +"',TIP_TELEFONO = 'C'"
									+"Where RUT_CLIENTE = '"+rut+"';");
									
							queryDataLineSBf = queryDataLineSBf.append(newline);
	
							queryDataLineSBf.append("UPDATE intellectcards.cdmst "
									+ "SET EMAIL_ID = '"+mailId+"@"+serverId+"', CELL_NO = '"+ phoneNum +"'," + 
									"ADDR_PHONE_2 = '"+ phoneNum +"' Where RUT = '"+rut+"';");
							
							queryDataLineSBf = queryDataLineSBf.append(newline);
							queryDataLineSBf = queryDataLineSBf.append("commit;");
		
						}else if (((phoneNum != null)& (!"".equalsIgnoreCase(phoneNum)))){
	
							queryDataLineSBf.append("UPDATE intellectcards.cust_details "
									+"SET NUM_TELEFONO = '"+ phoneNum +"',TIP_TELEFONO = 'C'");
									
									if (((mailId != null)& (!"".equalsIgnoreCase(mailId)))
										&& ((serverId != null)& (!"".equalsIgnoreCase(serverId)))){
										queryDataLineSBf.append(", EMAIL_USUARIO = '"+ mailId +"', EMAIL_SERVIDOR = '"+ serverId +"',IND_EECC_EMAIL = 'S'");
									}
									queryDataLineSBf.append("Where RUT_CLIENTE = '"+rut+"';");
									
							queryDataLineSBf = queryDataLineSBf.append(newline);
	
							queryDataLineSBf.append("UPDATE intellectcards.cdmst "
									+"CELL_NO = '"+ phoneNum +"'," + 
									"ADDR_PHONE_2 = '"+ phoneNum+"'");
									
									if (((mailId != null)& (!"".equalsIgnoreCase(mailId)))
											&& ((serverId != null)& (!"".equalsIgnoreCase(serverId)))){
											queryDataLineSBf.append(", EMAIL_ID = '"+mailId+"@"+serverId+"'");
										}									
							queryDataLineSBf.append("Where RUT = '"+rut+"';");
	
							queryDataLineSBf = queryDataLineSBf.append(newline);
							queryDataLineSBf = queryDataLineSBf.append("commit;");
					
							
						}else if (((mailId != null)& (!"".equalsIgnoreCase(mailId)))
										&& ((serverId != null)& (!"".equalsIgnoreCase(serverId)))){
							
							queryDataLineSBf.append("UPDATE intellectcards.cust_details "
									+"SET EMAIL_USUARIO = '"+ mailId +"', EMAIL_SERVIDOR = '"+ serverId +"',IND_EECC_EMAIL = 'S' ");
									
									queryDataLineSBf.append("Where RUT_CLIENTE = '"+rut+"';");
									
							queryDataLineSBf = queryDataLineSBf.append(newline);
	
							queryDataLineSBf.append("UPDATE intellectcards.cdmst "
									+"SET EMAIL_ID = '"+mailId+"@"+serverId+"'");
																			
							queryDataLineSBf.append("Where RUT = '"+rut+"';");
	
							queryDataLineSBf = queryDataLineSBf.append(newline);
							queryDataLineSBf = queryDataLineSBf.append("commit;");
					
					
						}
							
						queryDataLineSBf = queryDataLineSBf.append(newline);
						
						String queryDataLine =queryDataLineSBf.toString();
						
						if ((queryDataLine != null)&& (!"".equalsIgnoreCase(queryDataLine))){
							
							if (logger.isDebugEnabled()){
								logger.debug("Línea " + line);
								logger.debug("consulta " + queryDataLine);
							}
							
							phoneMailQueryLines.put(line, queryDataLine);
						}

					}else {
						logger.fatal("El valor del rut en este registro está vacio. Se rechaza el registro.");
						logger.fatal("Datos del Registro rechazado:");
						logger.fatal("rut = " + rut);
						logger.fatal("phoneNum = " + phoneNum);
						logger.fatal("mailId = " + mailId);
						logger.fatal("serverId = " + serverId);
						logger.fatal("El valor del rut en este registro está vacio. Registro ignorado");
					}
					
					
				}//fin de if (utilityPmt!=null) 
				
			});//fin del foreach
			
			
		}else {
			System.out.println("Datos de entrada vacios");
			logger.error("Datos de entrada vacios");
		}
		
		return phoneMailQueryLines;

	}//End of createPhoneMailUpdateQuery

	
	
	public Map <Integer,String> createBackupQuery (String requestName, String processDate, String schema, Map <Integer,String> tablas )
	{
		//TODO: Hacer este Backup query creator solo para custdetails, seleccionando solo los registros que modificaremos en base al rut

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
	

	public Map <Integer,String> createRutBackupQuery (String requestName, String processDate, String schema, Map <Integer,String> tablas,  Map <Integer,String> ruts )
	{


		Map <Integer,String> backupQueryLines = new HashMap <Integer,String> ();
		
		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {
			if ((tablas!=null) && (!tablas.isEmpty())) {

				tablas.forEach((pos,tabla)->{
					if (logger.isDebugEnabled()){
						logger.debug("Posicion : " + pos + " valor : " + tabla);
					}

					//TODO: Hacer este en base a un rut, para que mueva solo registros especificos en vez de la tabla completa

					String query = "";//"CREATE TABLE "+ schema + "." + tabla + "_"+ requestName +"_"+ processDate +" AS SELECT * FROM "+ schema + "." + tabla +";";

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
	

	}//createRutBackupQuery
	
	public Map <Integer,String> createCustDetailsRollbackQuery (String requestName, String processDate, String schema, Map <Integer,String> tablas)
	{
		Map <Integer,String> rollbackQueryLines = new HashMap <Integer,String> ();
		
		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {
			if ((tablas!=null) && (!tablas.isEmpty())) {

				tablas.forEach((pos,tabla)->{
					String query = "";
					String newline = System.getProperty("line.separator");
					
					if (!tabla.equalsIgnoreCase("CDMST")) {

/*						//@TODO: crear la logica que genera una línea de select a insert desde la CUST_DETAILS de respaldo a la principal 
						Probar si funciona la siguiente linea:
							
						20190508: Construir un m�todo de rollback para las actualizaciones de direcciones, que haga la vuelta atr�s de la cust_details con esta query
						  para no arriesgar.
						  
						  	UPDATE INTELLECTCARDS.CUST_DETAILS SET 
						  	STMT_MAIL_INSTR = Select STMT_MAIL_INSTR from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	CALLE = Select CALLE from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	DETALLE = Select DETALLE from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	NUMERO = Select NUMERO from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	COD_COMUNA = Select COD_COMUNA from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	COD_REGION = Select COD_REGION from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',
						  	IND_EECC_EMAIL = Select IND_EECC_EMAIL from INTELLECTCARDS.CUST_DETAILS_10136_20190508 Where RUT_CLIENTE ='188995195',, 
						  	MAKER_DT = sysdate 
						  	Where RUT_CLIENTE ='188995195';
						  	
*/
						/*						
 * 						query = "TRUNCATE TABLE "+ schema +"." + tabla +";";
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
				
/*
				20190508: Construir un m�todo de rollback para las actualizaciones de direcciones, que haga la vuelta atr�s de la cust_details con esta query
				  para no arriesgar.
				  
					UPDATE INTELLECTCARDS.CDMST SET 
					ADDR_LINE_1 = Select ADDR_LINE_1 from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195',
					ADDR_LINE_2 = Select ADDR_LINE_2 from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195',
					ADDR_LINE_3 = Select ADDR_LINE_3 from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195',
					ADDR_LINE_4 = Select ADDR_LINE_4 from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195',
					CITY = Select CITY from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195',
					COUNTRY = Select COUNTRY from INTELLECTCARDS.CDMST_10136_20190508 Where CIVIL_ID ='188995195' 
					Where CIVIL_ID ='188995195';
		
					
*/
				
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
	
		
	public Map <Integer,String> createAddrsBLockRollbackQuery(String requestName, String creationdate, String schema, Map <Integer,AddrsBlockData> addrsBlockData ) {

		String newline = System.getProperty("line.separator");
		Map <Integer,String> resultQueryLines = new HashMap <Integer,String> ();

		int recordNum = addrsBlockData.size();

		if (recordNum >0){

			addrsBlockData.forEach ((line,addrsBlockD)->{
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
			    	AddrsBlockData addrsBlock = addrsBlockD;
					
				
					if (addrsBlock!=null) {
					
						String rut = addrsBlock.getRut();
					
				    	if (logger.isDebugEnabled()){
							logger.debug("Rut:  " + rut);
						}
						
						if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {
	
								StringBuffer queryDataLineSBf = new StringBuffer();
	
								queryDataLineSBf.append("UPDATE INTELLECTCARDS.CUST_DETAILS SET ADDR_BLOCK='N'"
										+ "WHERE RUT_CLIENTE='"+rut+"';");
	
								queryDataLineSBf = queryDataLineSBf.append(newline);
								queryDataLineSBf = queryDataLineSBf.append("commit;");
								
								resultQueryLines.put(line, queryDataLineSBf.toString());

								
						}else {
							System.out.println ("RequestName not Informed");
							logger.error ("RequestName not Informed");
						}
					}// fin del if (addrsBlock!=null) {
				});//fin del foreach
			
			
			}else {
				System.out.println("Datos de PAT de entrada vacios");
				logger.error("Datos de PAT de entrada vacios");
			}

		
		return resultQueryLines;				
		
	}//Find de createAddrsBLockRollbackQuery

	/**
	 * @param requestName
	 * @param creationdate
	 * @param schema
	 * @param addrsBlockData
	 * @return
	 */
	public Map <Integer,String> createFMBackupQuery(String requestName, String creationdate,Map <Integer,PhoneMailData> phoneMailData) {

		String newline = System.getProperty("line.separator");
		Map <Integer,String> resultQueryLines = new HashMap <Integer,String> ();

		int recordNum = phoneMailData.size();

		if (recordNum >0){
			
			final StringBuffer queryDataLinePMD = new StringBuffer();

/* @TODO implementar este rollback separado en bloques mayores a 1000
 * 
 * CREATE TABLE INTELLECTCARDS.CUST_DETAILS_20200515_13431 AS 
SELECT * FROM INTELLECTCARDS.CUST_DETAILS WHERE RUT_CLIENTE in ('24408272', '24410358');

insert into INTELLECTCARDS.CUST_DETAILS_20200515_13431 (RUT_CLIENTE,EST_CIVIL,SEXO,FEC_NACIMIENTO,PRIMER_APELLIDO,SEGUNDO_APELLIDO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,PROFESION,CONYUGUE,NACIONALIDAD,EMAIL_USUARIO,EMAIL_SERVIDOR,NIVEL_ESTUDIOS,TIPO_VIVIENDA,NUM_HIJOS,NUM_DEPENDIENTES,ES_RESIDENTE,TIEMPO_VIVIEN_ACT,TOTAL_INGRESOS,COD_PAIS,SCORING,ACTIVIDAD,TIPO_SOC_CONYUGAL,NOM_CONYUGUE,FEC_MATRIMONIO,IND_SOL_PATRIMONIO,IND_EECC_EMAIL,COD_DIRECCION,TIP_DIRECCION,COD_POSTAL,DETALLE,COD_PAIS_CNT,COD_REGION,COD_COMUNA,CALLE,NUMERO,ES_DIR_EECC,IND_ESTADO,COD_FUENTE,COD_AREA,NUM_TELEFONO,TIP_TELEFONO,EXTENSION,ES_DEFAULT,INCLUIDO_POR,FEC_INCLUSION,MODIFICADO_POR,FEC_MODIFICACION,TEL_ACTIVO,COD_FUENTE_FONO,SPOUSE_RUT,SEP_FLAG,LAW_FLAG,HOME_OWNERSHIP,OCC_DATE,INDUSTRY_TYPE,OTHER_BANK_ACC_NO,OTHER_CARD_LIMIT,OTHER_CARD_OTB,OTHER_INST,OTHER_CURR_CODE,OTHER_ISSUE_DATE,OTHER_EXP_DT,RC11_DESCRIPTION,RC12_OBSERVATION,RP10_OBSERVATION,IF14_OBSERVATION,FIRM_NAME,FIRM_CODE,FIRM_TYPE,FIRM_ID,ADDR_VAL_FLG,DESIGNATION,SPECIFICATION,EMP_TYPE,WORK_EX_MON,ADDR_STATE,PH_NO_2,EXT_NO,NOTE,REF_NAME,REF_RUT,REF_AREA_CODE,REF_PHONE,REF_WORKPLACE,REF_SPEC,RELATIONSHIP,PROMO_RUT,PRO_APP_NUM,CLASS,REG_TYPE,ORG_CODE,RUT_CONYUGE,SEPARACION_BIENES,BLOQUEOLEYCONSUMIDOR,TIPO_EMPRESA_1,TIPO_EMPRESA_2,TIPO_EMPRESA_3,TIPO_EMPRESA_4,TIPO_EMPRESA_5,TIPO_EMPRESA_6,TIPO_EMPRESA_7,TIPO_EMPRESA_8,NUMERO_CUENTA_1,NUMERO_CUENTA_2,NUMERO_CUENTA_3,NUMERO_CUENTA_4,NUMERO_CUENTA_5,NUMERO_CUENTA_6,NUMERO_CUENTA_7,NUMERO_CUENTA_8,CUPO_CUENTA_1,CUPO_CUENTA_2,CUPO_CUENTA_3,CUPO_CUENTA_4,CUPO_CUENTA_5,CUPO_CUENTA_6,CUPO_CUENTA_7,CUPO_CUENTA_8,SALDO_1,SALDO_2,SALDO_3,SALDO_4,SALDO_5,SALDO_6,SALDO_7,SALDO_8,VALOR_CUOTA_1,VALOR_CUOTA_2,VALOR_CUOTA_3,VALOR_CUOTA_4,VALOR_CUOTA_5,VALOR_CUOTA_6,VALOR_CUOTA_7,VALOR_CUOTA_8,MONEDA_1,MONEDA_2,MONEDA_3,MONEDA_4,MONEDA_5,MONEDA_6,MONEDA_7,MONEDA_8,FECHA_APERTURA_1,FECHA_APERTURA_2,FECHA_APERTURA_3,FECHA_APERTURA_4,FECHA_APERTURA_5,FECHA_APERTURA_6,FECHA_APERTURA_7,FECHA_APERTURA_8,FECHA_VENCIMIENTO_1,FECHA_VENCIMIENTO_2,FECHA_VENCIMIENTO_3,FECHA_VENCIMIENTO_4,FECHA_VENCIMIENTO_5,FECHA_VENCIMIENTO_6,FECHA_VENCIMIENTO_7,FECHA_VENCIMIENTO_8,RC11_DESCRIPCION_1,RC11_DESCRIPCION_2,RC11_DESCRIPCION_3,RC11_DESCRIPCION_4,RC11_DESCRIPCION_5,RC11_DESCRIPCION_6,RC11_DESCRIPCION_7,RC11_DESCRIPCION_8,RC12_OBSERVACIONES_1,RC12_OBSERVACIONES_2,RC12_OBSERVACIONES_3,RC12_OBSERVACIONES_4,RC12_OBSERVACIONES_5,RC12_OBSERVACIONES_6,RC12_OBSERVACIONES_7,RC12_OBSERVACIONES_8,CODLABORA,FECHAINGRESO,CODMONEDA,RENTA,INDVERIFICADO,CODCARGO,PUESTO,OBSERVACIONES,TIPOEMPLEO,ANTIGUEDAD,NOMBRE_1,NOMBRE_2,IDENTIFICACION_1,IDENTIFICACION_2,FONOAREA_1,FONOAREA_2,FONONUMERO_1,FONONUMERO_2,LUGARTRABAJO_1,LUGARTRABAJO_2,PUESTO_1,PUESTO_2,RELACIONTITULAR_1,RELACIONTITULAR_2,RP10_OBSERVACIONES_1,RP10_OBSERVACIONES_2,PROMOTORFINAL,NUMEROSETLEGAL,CUPO,CLASIFICACION,CODDIRESTCTA,TIPCARGASOLIC,MAKER_DT,MAKER_TIME,APPLN_BATCH_NO,APPLN_SERIAL_NO,SITUACIONCASA,TIPOINGRESO,SCORE_DICOM,A_COD_POSTAL,O_COD_POSTAL,P_COD_POSTAL,STMT_MAIL_INSTR,H_CORRELATIVO,H_CASILLACORREO,H_UBICACION,H_ACTIVO,O_CORRELATIVO,O_TIPODIRECCION,O_CASILLACORREO,O_ESTADODOMICILIO,O_CODPAIS,O_CODREGION,O_CODCOMUNA,O_CALLE,O_NUMERO,O_DETALLEDIRECCION,O_AREATELEFONO,O_FONOTELEFONO,O_ANEXOTELEFONO,O_TIPOTELEFONO,O_UBICACION,O_NOTA,O_ACTIVO,O_DEFAULT,A_CORRELATIVO,A_TIPODIRECCION,A_CASILLACORREO,A_ESTADODOMICILIO,A_CODPAIS,A_CODREGION,A_CODCOMUNA,A_CALLE,A_NUMERO,A_DETALLEDIRECCION,A_AREATELEFONO,A_FONOTELEFONO,A_ANEXOTELEFONO,A_TIPOTELEFONO,A_UBICACION,A_NOTA,A_ACTIVO,A_DEFAULT,P_CORRELATIVO,P_TIPODIRECCION,P_CASILLACORREO,P_ESTADODOMICILIO,P_CODPAIS,P_CODREGION,P_CODCOMUNA,P_CALLE,P_NUMERO,P_DETALLEDIRECCION,P_AREATELEFONO,P_FONOTELEFONO,P_ANEXOTELEFONO,P_TIPOTELEFONO,P_UBICACION,P_NOTA,P_ACTIVO,P_DEFAULT,ADDR_BLOCK,MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC)
Select RUT_CLIENTE,EST_CIVIL,SEXO,FEC_NACIMIENTO,PRIMER_APELLIDO,SEGUNDO_APELLIDO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,PROFESION,CONYUGUE,NACIONALIDAD,EMAIL_USUARIO,EMAIL_SERVIDOR,NIVEL_ESTUDIOS,TIPO_VIVIENDA,NUM_HIJOS,NUM_DEPENDIENTES,ES_RESIDENTE,TIEMPO_VIVIEN_ACT,TOTAL_INGRESOS,COD_PAIS,SCORING,ACTIVIDAD,TIPO_SOC_CONYUGAL,NOM_CONYUGUE,FEC_MATRIMONIO,IND_SOL_PATRIMONIO,IND_EECC_EMAIL,COD_DIRECCION,TIP_DIRECCION,COD_POSTAL,DETALLE,COD_PAIS_CNT,COD_REGION,COD_COMUNA,CALLE,NUMERO,ES_DIR_EECC,IND_ESTADO,COD_FUENTE,COD_AREA,NUM_TELEFONO,TIP_TELEFONO,EXTENSION,ES_DEFAULT,INCLUIDO_POR,FEC_INCLUSION,MODIFICADO_POR,FEC_MODIFICACION,TEL_ACTIVO,COD_FUENTE_FONO,SPOUSE_RUT,SEP_FLAG,LAW_FLAG,HOME_OWNERSHIP,OCC_DATE,INDUSTRY_TYPE,OTHER_BANK_ACC_NO,OTHER_CARD_LIMIT,OTHER_CARD_OTB,OTHER_INST,OTHER_CURR_CODE,OTHER_ISSUE_DATE,OTHER_EXP_DT,RC11_DESCRIPTION,RC12_OBSERVATION,RP10_OBSERVATION,IF14_OBSERVATION,FIRM_NAME,FIRM_CODE,FIRM_TYPE,FIRM_ID,ADDR_VAL_FLG,DESIGNATION,SPECIFICATION,EMP_TYPE,WORK_EX_MON,ADDR_STATE,PH_NO_2,EXT_NO,NOTE,REF_NAME,REF_RUT,REF_AREA_CODE,REF_PHONE,REF_WORKPLACE,REF_SPEC,RELATIONSHIP,PROMO_RUT,PRO_APP_NUM,CLASS,REG_TYPE,ORG_CODE,RUT_CONYUGE,SEPARACION_BIENES,BLOQUEOLEYCONSUMIDOR,TIPO_EMPRESA_1,TIPO_EMPRESA_2,TIPO_EMPRESA_3,TIPO_EMPRESA_4,TIPO_EMPRESA_5,TIPO_EMPRESA_6,TIPO_EMPRESA_7,TIPO_EMPRESA_8,NUMERO_CUENTA_1,NUMERO_CUENTA_2,NUMERO_CUENTA_3,NUMERO_CUENTA_4,NUMERO_CUENTA_5,NUMERO_CUENTA_6,NUMERO_CUENTA_7,NUMERO_CUENTA_8,CUPO_CUENTA_1,CUPO_CUENTA_2,CUPO_CUENTA_3,CUPO_CUENTA_4,CUPO_CUENTA_5,CUPO_CUENTA_6,CUPO_CUENTA_7,CUPO_CUENTA_8,SALDO_1,SALDO_2,SALDO_3,SALDO_4,SALDO_5,SALDO_6,SALDO_7,SALDO_8,VALOR_CUOTA_1,VALOR_CUOTA_2,VALOR_CUOTA_3,VALOR_CUOTA_4,VALOR_CUOTA_5,VALOR_CUOTA_6,VALOR_CUOTA_7,VALOR_CUOTA_8,MONEDA_1,MONEDA_2,MONEDA_3,MONEDA_4,MONEDA_5,MONEDA_6,MONEDA_7,MONEDA_8,FECHA_APERTURA_1,FECHA_APERTURA_2,FECHA_APERTURA_3,FECHA_APERTURA_4,FECHA_APERTURA_5,FECHA_APERTURA_6,FECHA_APERTURA_7,FECHA_APERTURA_8,FECHA_VENCIMIENTO_1,FECHA_VENCIMIENTO_2,FECHA_VENCIMIENTO_3,FECHA_VENCIMIENTO_4,FECHA_VENCIMIENTO_5,FECHA_VENCIMIENTO_6,FECHA_VENCIMIENTO_7,FECHA_VENCIMIENTO_8,RC11_DESCRIPCION_1,RC11_DESCRIPCION_2,RC11_DESCRIPCION_3,RC11_DESCRIPCION_4,RC11_DESCRIPCION_5,RC11_DESCRIPCION_6,RC11_DESCRIPCION_7,RC11_DESCRIPCION_8,RC12_OBSERVACIONES_1,RC12_OBSERVACIONES_2,RC12_OBSERVACIONES_3,RC12_OBSERVACIONES_4,RC12_OBSERVACIONES_5,RC12_OBSERVACIONES_6,RC12_OBSERVACIONES_7,RC12_OBSERVACIONES_8,CODLABORA,FECHAINGRESO,CODMONEDA,RENTA,INDVERIFICADO,CODCARGO,PUESTO,OBSERVACIONES,TIPOEMPLEO,ANTIGUEDAD,NOMBRE_1,NOMBRE_2,IDENTIFICACION_1,IDENTIFICACION_2,FONOAREA_1,FONOAREA_2,FONONUMERO_1,FONONUMERO_2,LUGARTRABAJO_1,LUGARTRABAJO_2,PUESTO_1,PUESTO_2,RELACIONTITULAR_1,RELACIONTITULAR_2,RP10_OBSERVACIONES_1,RP10_OBSERVACIONES_2,PROMOTORFINAL,NUMEROSETLEGAL,CUPO,CLASIFICACION,CODDIRESTCTA,TIPCARGASOLIC,MAKER_DT,MAKER_TIME,APPLN_BATCH_NO,APPLN_SERIAL_NO,SITUACIONCASA,TIPOINGRESO,SCORE_DICOM,A_COD_POSTAL,O_COD_POSTAL,P_COD_POSTAL,STMT_MAIL_INSTR,H_CORRELATIVO,H_CASILLACORREO,H_UBICACION,H_ACTIVO,O_CORRELATIVO,O_TIPODIRECCION,O_CASILLACORREO,O_ESTADODOMICILIO,O_CODPAIS,O_CODREGION,O_CODCOMUNA,O_CALLE,O_NUMERO,O_DETALLEDIRECCION,O_AREATELEFONO,O_FONOTELEFONO,O_ANEXOTELEFONO,O_TIPOTELEFONO,O_UBICACION,O_NOTA,O_ACTIVO,O_DEFAULT,A_CORRELATIVO,A_TIPODIRECCION,A_CASILLACORREO,A_ESTADODOMICILIO,A_CODPAIS,A_CODREGION,A_CODCOMUNA,A_CALLE,A_NUMERO,A_DETALLEDIRECCION,A_AREATELEFONO,A_FONOTELEFONO,A_ANEXOTELEFONO,A_TIPOTELEFONO,A_UBICACION,A_NOTA,A_ACTIVO,A_DEFAULT,P_CORRELATIVO,P_TIPODIRECCION,P_CASILLACORREO,P_ESTADODOMICILIO,P_CODPAIS,P_CODREGION,P_CODCOMUNA,P_CALLE,P_NUMERO,P_DETALLEDIRECCION,P_AREATELEFONO,P_FONOTELEFONO,P_ANEXOTELEFONO,P_TIPOTELEFONO,P_UBICACION,P_NOTA,P_ACTIVO,P_DEFAULT,ADDR_BLOCK,MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC
from INTELLECTCARDS.CUST_DETAILS
Where RUT_CLIENTE in('24460886','24542483');
 * */			
			
			queryDataLinePMD.append("CREATE TABLE INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_"+requestName
					+ " AS SELECT * FROM INTELLECTCARDS.CUST_DETAILS WHERE RUT_CLIENTE in (");
			

			phoneMailData.forEach ((line,phoneMailD)->{
					
			    	if (logger.isDebugEnabled()){
						logger.debug("Procesando la línea " + line);
					}
					
			    	
			    	PhoneMailData phonemail = phoneMailD;
					
				
					if (phonemail!=null) {

						String rut = phonemail.getRut();
					
				    	if (logger.isDebugEnabled()){
							logger.debug("Rut:  " + rut);
						}
						
						if ((rut!=null) && (!"".equalsIgnoreCase(rut))) {
						
							try {
								 float mod = (float)line%800; //se pone 10 para pruebas y se deberia dejar 800
								 if (mod==0.0) {

									 queryDataLinePMD.append(");");
									 queryDataLinePMD.append(newline);
									 queryDataLinePMD.append(newline);
									 queryDataLinePMD.append("INSERT INTO INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_"+requestName
												+ " (RUT_CLIENTE,EST_CIVIL,SEXO,FEC_NACIMIENTO,PRIMER_APELLIDO,SEGUNDO_APELLIDO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,"
												+ "PROFESION,CONYUGUE,NACIONALIDAD,EMAIL_USUARIO,EMAIL_SERVIDOR,NIVEL_ESTUDIOS,TIPO_VIVIENDA,NUM_HIJOS,NUM_DEPENDIENTES,"
												+ "ES_RESIDENTE,TIEMPO_VIVIEN_ACT,TOTAL_INGRESOS,COD_PAIS,SCORING,ACTIVIDAD,TIPO_SOC_CONYUGAL,NOM_CONYUGUE,FEC_MATRIMONIO,"
												+ "IND_SOL_PATRIMONIO,IND_EECC_EMAIL,COD_DIRECCION,TIP_DIRECCION,COD_POSTAL,DETALLE,COD_PAIS_CNT,COD_REGION,COD_COMUNA,CALLE,"
												+ "NUMERO,ES_DIR_EECC,IND_ESTADO,COD_FUENTE,COD_AREA,NUM_TELEFONO,TIP_TELEFONO,EXTENSION,ES_DEFAULT,INCLUIDO_POR,FEC_INCLUSION,"
												+ "MODIFICADO_POR,FEC_MODIFICACION,TEL_ACTIVO,COD_FUENTE_FONO,SPOUSE_RUT,SEP_FLAG,LAW_FLAG,HOME_OWNERSHIP,OCC_DATE,INDUSTRY_TYPE,"
												+ "OTHER_BANK_ACC_NO,OTHER_CARD_LIMIT,OTHER_CARD_OTB,OTHER_INST,OTHER_CURR_CODE,OTHER_ISSUE_DATE,OTHER_EXP_DT,RC11_DESCRIPTION,"
												+ "RC12_OBSERVATION,RP10_OBSERVATION,IF14_OBSERVATION,FIRM_NAME,FIRM_CODE,FIRM_TYPE,FIRM_ID,ADDR_VAL_FLG,DESIGNATION,SPECIFICATION,"
												+ "EMP_TYPE,WORK_EX_MON,ADDR_STATE,PH_NO_2,EXT_NO,NOTE,REF_NAME,REF_RUT,REF_AREA_CODE,REF_PHONE,REF_WORKPLACE,REF_SPEC,RELATIONSHIP,"
												+ "PROMO_RUT,PRO_APP_NUM,CLASS,REG_TYPE,ORG_CODE,RUT_CONYUGE,SEPARACION_BIENES,BLOQUEOLEYCONSUMIDOR,TIPO_EMPRESA_1,TIPO_EMPRESA_2,"
												+ "TIPO_EMPRESA_3,TIPO_EMPRESA_4,TIPO_EMPRESA_5,TIPO_EMPRESA_6,TIPO_EMPRESA_7,TIPO_EMPRESA_8,NUMERO_CUENTA_1,NUMERO_CUENTA_2,"
												+ "NUMERO_CUENTA_3,NUMERO_CUENTA_4,NUMERO_CUENTA_5,NUMERO_CUENTA_6,NUMERO_CUENTA_7,NUMERO_CUENTA_8,CUPO_CUENTA_1,CUPO_CUENTA_2,"
												+ "CUPO_CUENTA_3,CUPO_CUENTA_4,CUPO_CUENTA_5,CUPO_CUENTA_6,CUPO_CUENTA_7,CUPO_CUENTA_8,SALDO_1,SALDO_2,SALDO_3,SALDO_4,SALDO_5,"
												+ "SALDO_6,SALDO_7,SALDO_8,VALOR_CUOTA_1,VALOR_CUOTA_2,VALOR_CUOTA_3,VALOR_CUOTA_4,VALOR_CUOTA_5,VALOR_CUOTA_6,VALOR_CUOTA_7,"
												+ "VALOR_CUOTA_8,MONEDA_1,MONEDA_2,MONEDA_3,MONEDA_4,MONEDA_5,MONEDA_6,MONEDA_7,MONEDA_8,FECHA_APERTURA_1,FECHA_APERTURA_2,"
												+ "FECHA_APERTURA_3,FECHA_APERTURA_4,FECHA_APERTURA_5,FECHA_APERTURA_6,FECHA_APERTURA_7,FECHA_APERTURA_8,FECHA_VENCIMIENTO_1,"
												+ "FECHA_VENCIMIENTO_2,FECHA_VENCIMIENTO_3,FECHA_VENCIMIENTO_4,FECHA_VENCIMIENTO_5,FECHA_VENCIMIENTO_6,FECHA_VENCIMIENTO_7,"
												+ "FECHA_VENCIMIENTO_8,RC11_DESCRIPCION_1,RC11_DESCRIPCION_2,RC11_DESCRIPCION_3,RC11_DESCRIPCION_4,RC11_DESCRIPCION_5,"
												+ "RC11_DESCRIPCION_6,RC11_DESCRIPCION_7,RC11_DESCRIPCION_8,RC12_OBSERVACIONES_1,RC12_OBSERVACIONES_2,RC12_OBSERVACIONES_3,"
												+ "RC12_OBSERVACIONES_4,RC12_OBSERVACIONES_5,RC12_OBSERVACIONES_6,RC12_OBSERVACIONES_7,RC12_OBSERVACIONES_8,CODLABORA,FECHAINGRESO,"
												+ "CODMONEDA,RENTA,INDVERIFICADO,CODCARGO,PUESTO,OBSERVACIONES,TIPOEMPLEO,ANTIGUEDAD,NOMBRE_1,NOMBRE_2,IDENTIFICACION_1,IDENTIFICACION_2,"
												+ "FONOAREA_1,FONOAREA_2,FONONUMERO_1,FONONUMERO_2,LUGARTRABAJO_1,LUGARTRABAJO_2,PUESTO_1,PUESTO_2,RELACIONTITULAR_1,RELACIONTITULAR_2,"
												+ "RP10_OBSERVACIONES_1,RP10_OBSERVACIONES_2,PROMOTORFINAL,NUMEROSETLEGAL,CUPO,CLASIFICACION,CODDIRESTCTA,TIPCARGASOLIC,MAKER_DT,"
												+ "MAKER_TIME,APPLN_BATCH_NO,APPLN_SERIAL_NO,SITUACIONCASA,TIPOINGRESO,SCORE_DICOM,A_COD_POSTAL,O_COD_POSTAL,P_COD_POSTAL,STMT_MAIL_INSTR,"
												+ "H_CORRELATIVO,H_CASILLACORREO,H_UBICACION,H_ACTIVO,O_CORRELATIVO,O_TIPODIRECCION,O_CASILLACORREO,O_ESTADODOMICILIO,O_CODPAIS,O_CODREGION,"
												+ "O_CODCOMUNA,O_CALLE,O_NUMERO,O_DETALLEDIRECCION,O_AREATELEFONO,O_FONOTELEFONO,O_ANEXOTELEFONO,O_TIPOTELEFONO,O_UBICACION,O_NOTA,O_ACTIVO,"
												+ "O_DEFAULT,A_CORRELATIVO,A_TIPODIRECCION,A_CASILLACORREO,A_ESTADODOMICILIO,A_CODPAIS,A_CODREGION,A_CODCOMUNA,A_CALLE,A_NUMERO,"
												+ "A_DETALLEDIRECCION,A_AREATELEFONO,A_FONOTELEFONO,A_ANEXOTELEFONO,A_TIPOTELEFONO,A_UBICACION,A_NOTA,A_ACTIVO,A_DEFAULT,P_CORRELATIVO,"
												+ "P_TIPODIRECCION,P_CASILLACORREO,P_ESTADODOMICILIO,P_CODPAIS,P_CODREGION,P_CODCOMUNA,P_CALLE,P_NUMERO,P_DETALLEDIRECCION,P_AREATELEFONO,"
												+ "P_FONOTELEFONO,P_ANEXOTELEFONO,P_TIPOTELEFONO,P_UBICACION,P_NOTA,P_ACTIVO,P_DEFAULT,ADDR_BLOCK,MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC)"
												+ " SELECT RUT_CLIENTE,EST_CIVIL,SEXO,FEC_NACIMIENTO,PRIMER_APELLIDO,SEGUNDO_APELLIDO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,PROFESION,CONYUGUE,"
												+ "NACIONALIDAD,EMAIL_USUARIO,EMAIL_SERVIDOR,NIVEL_ESTUDIOS,TIPO_VIVIENDA,NUM_HIJOS,NUM_DEPENDIENTES,ES_RESIDENTE,TIEMPO_VIVIEN_ACT,"
												+ "TOTAL_INGRESOS,COD_PAIS,SCORING,ACTIVIDAD,TIPO_SOC_CONYUGAL,NOM_CONYUGUE,FEC_MATRIMONIO,IND_SOL_PATRIMONIO,IND_EECC_EMAIL,COD_DIRECCION,"
												+ "TIP_DIRECCION,COD_POSTAL,DETALLE,COD_PAIS_CNT,COD_REGION,COD_COMUNA,CALLE,NUMERO,ES_DIR_EECC,IND_ESTADO,COD_FUENTE,COD_AREA,NUM_TELEFONO,"
												+ "TIP_TELEFONO,EXTENSION,ES_DEFAULT,INCLUIDO_POR,FEC_INCLUSION,MODIFICADO_POR,FEC_MODIFICACION,TEL_ACTIVO,COD_FUENTE_FONO,SPOUSE_RUT,SEP_FLAG,"
												+ "LAW_FLAG,HOME_OWNERSHIP,OCC_DATE,INDUSTRY_TYPE,OTHER_BANK_ACC_NO,OTHER_CARD_LIMIT,OTHER_CARD_OTB,OTHER_INST,OTHER_CURR_CODE,OTHER_ISSUE_DATE,"
												+ "OTHER_EXP_DT,RC11_DESCRIPTION,RC12_OBSERVATION,RP10_OBSERVATION,IF14_OBSERVATION,FIRM_NAME,FIRM_CODE,FIRM_TYPE,FIRM_ID,ADDR_VAL_FLG,DESIGNATION,"
												+ "SPECIFICATION,EMP_TYPE,WORK_EX_MON,ADDR_STATE,PH_NO_2,EXT_NO,NOTE,REF_NAME,REF_RUT,REF_AREA_CODE,REF_PHONE,REF_WORKPLACE,REF_SPEC,RELATIONSHIP,"
												+ "PROMO_RUT,PRO_APP_NUM,CLASS,REG_TYPE,ORG_CODE,RUT_CONYUGE,SEPARACION_BIENES,BLOQUEOLEYCONSUMIDOR,TIPO_EMPRESA_1,TIPO_EMPRESA_2,TIPO_EMPRESA_3,"
												+ "TIPO_EMPRESA_4,TIPO_EMPRESA_5,TIPO_EMPRESA_6,TIPO_EMPRESA_7,TIPO_EMPRESA_8,NUMERO_CUENTA_1,NUMERO_CUENTA_2,NUMERO_CUENTA_3,NUMERO_CUENTA_4,"
												+ "NUMERO_CUENTA_5,NUMERO_CUENTA_6,NUMERO_CUENTA_7,NUMERO_CUENTA_8,CUPO_CUENTA_1,CUPO_CUENTA_2,CUPO_CUENTA_3,CUPO_CUENTA_4,CUPO_CUENTA_5,CUPO_CUENTA_6,"
												+ "CUPO_CUENTA_7,CUPO_CUENTA_8,SALDO_1,SALDO_2,SALDO_3,SALDO_4,SALDO_5,SALDO_6,SALDO_7,SALDO_8,VALOR_CUOTA_1,VALOR_CUOTA_2,VALOR_CUOTA_3,VALOR_CUOTA_4,"
												+ "VALOR_CUOTA_5,VALOR_CUOTA_6,VALOR_CUOTA_7,VALOR_CUOTA_8,MONEDA_1,MONEDA_2,MONEDA_3,MONEDA_4,MONEDA_5,MONEDA_6,MONEDA_7,MONEDA_8,FECHA_APERTURA_1,"
												+ "FECHA_APERTURA_2,FECHA_APERTURA_3,FECHA_APERTURA_4,FECHA_APERTURA_5,FECHA_APERTURA_6,FECHA_APERTURA_7,FECHA_APERTURA_8,FECHA_VENCIMIENTO_1,"
												+ "FECHA_VENCIMIENTO_2,FECHA_VENCIMIENTO_3,FECHA_VENCIMIENTO_4,FECHA_VENCIMIENTO_5,FECHA_VENCIMIENTO_6,FECHA_VENCIMIENTO_7,FECHA_VENCIMIENTO_8,"
												+ "RC11_DESCRIPCION_1,RC11_DESCRIPCION_2,RC11_DESCRIPCION_3,RC11_DESCRIPCION_4,RC11_DESCRIPCION_5,RC11_DESCRIPCION_6,RC11_DESCRIPCION_7,RC11_DESCRIPCION_8,"
												+ "RC12_OBSERVACIONES_1,RC12_OBSERVACIONES_2,RC12_OBSERVACIONES_3,RC12_OBSERVACIONES_4,RC12_OBSERVACIONES_5,RC12_OBSERVACIONES_6,RC12_OBSERVACIONES_7,"
												+ "RC12_OBSERVACIONES_8,CODLABORA,FECHAINGRESO,CODMONEDA,RENTA,INDVERIFICADO,CODCARGO,PUESTO,OBSERVACIONES,TIPOEMPLEO,ANTIGUEDAD,NOMBRE_1,NOMBRE_2,"
												+ "IDENTIFICACION_1,IDENTIFICACION_2,FONOAREA_1,FONOAREA_2,FONONUMERO_1,FONONUMERO_2,LUGARTRABAJO_1,LUGARTRABAJO_2,PUESTO_1,PUESTO_2,RELACIONTITULAR_1,"
												+ "RELACIONTITULAR_2,RP10_OBSERVACIONES_1,RP10_OBSERVACIONES_2,PROMOTORFINAL,NUMEROSETLEGAL,CUPO,CLASIFICACION,CODDIRESTCTA,TIPCARGASOLIC,MAKER_DT,"
												+ "MAKER_TIME,APPLN_BATCH_NO,APPLN_SERIAL_NO,SITUACIONCASA,TIPOINGRESO,SCORE_DICOM,A_COD_POSTAL,O_COD_POSTAL,P_COD_POSTAL,STMT_MAIL_INSTR,H_CORRELATIVO,"
												+ "H_CASILLACORREO,H_UBICACION,H_ACTIVO,O_CORRELATIVO,O_TIPODIRECCION,O_CASILLACORREO,O_ESTADODOMICILIO,O_CODPAIS,O_CODREGION,O_CODCOMUNA,O_CALLE,"
												+ "O_NUMERO,O_DETALLEDIRECCION,O_AREATELEFONO,O_FONOTELEFONO,O_ANEXOTELEFONO,O_TIPOTELEFONO,O_UBICACION,O_NOTA,O_ACTIVO,O_DEFAULT,A_CORRELATIVO,"
												+ "A_TIPODIRECCION,A_CASILLACORREO,A_ESTADODOMICILIO,A_CODPAIS,A_CODREGION,A_CODCOMUNA,A_CALLE,A_NUMERO,A_DETALLEDIRECCION,A_AREATELEFONO,A_FONOTELEFONO,"
												+ "A_ANEXOTELEFONO,A_TIPOTELEFONO,A_UBICACION,A_NOTA,A_ACTIVO,A_DEFAULT,P_CORRELATIVO,P_TIPODIRECCION,P_CASILLACORREO,P_ESTADODOMICILIO,P_CODPAIS,"
												+ "P_CODREGION,P_CODCOMUNA,P_CALLE,P_NUMERO,P_DETALLEDIRECCION,P_AREATELEFONO,P_FONOTELEFONO,P_ANEXOTELEFONO,P_TIPOTELEFONO,P_UBICACION,P_NOTA,P_ACTIVO,"
												+ "P_DEFAULT,ADDR_BLOCK,MOTIVO_RECHAZ,MOTIVO_RECHAZ_DESC "
												+ " FROM INTELLECTCARDS.CUST_DETAILS WHERE RUT_CLIENTE in (");
								 }

							
							if (line < recordNum) {
								queryDataLinePMD.append("'"+rut+"',");
							}else if (line == recordNum) {
								queryDataLinePMD.append("'"+rut+"'");
							}
							
							
							} catch (Exception e) {
								// TODO poner bien los comentarios
								e.printStackTrace();
							}
								
						}
						
						
					}// fin del if (phonemail!=null) {
				});//fin del foreach
			
			queryDataLinePMD.append(");");
			queryDataLinePMD.append(newline);
			
			resultQueryLines.put(0, queryDataLinePMD.toString());
			
			
			}else {
				System.out.println("Fono / mail de entrada vacios");
				logger.error("Fono / mail de entrada vacios");
			}

		return resultQueryLines;				
		
	}//Find de createFMBackupQuery

	
	public Map <Integer,String> createFMRollbackQuery (String requestName, String creationdate, String schema, Map <Integer,PhoneMailData> phoneMailData){

		String newline = System.getProperty("line.separator");
		Map <Integer,String> resultQueryLines = new HashMap <Integer,String> ();

		if ((requestName!=null) && (!"".equalsIgnoreCase(requestName))) {

			int recordNum = phoneMailData.size();

			if (recordNum >0){
				final StringBuffer queryDataLinePMD = new StringBuffer();
				
				phoneMailData.forEach ((line,phoneMailD)->{
						
				    	if (logger.isDebugEnabled()){
							logger.debug("Procesando la línea " + line);
						}
						
				    	PhoneMailData phonemail = phoneMailD;
						
					
						if (phonemail!=null) {
						
							String rut = phonemail.getRut();
						
					    	if (logger.isDebugEnabled()){
								logger.debug("Rut:  " + rut);
							}
	
							if ((rut!=null) && (!"".equalsIgnoreCase(rut))) {	

								queryDataLinePMD.append("UPDATE intellectcards.cust_details ");
								queryDataLinePMD.append("SET EMAIL_USUARIO = (select EMAIL_USUARIO from INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_MT"+requestName+" where RUT_CLIENTE = '"+rut+"'),");
								queryDataLinePMD.append("EMAIL_SERVIDOR = (select EMAIL_SERVIDOR from INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_MT"+requestName+" where RUT_CLIENTE =  '"+rut+"'),");
								queryDataLinePMD.append("NUM_TELEFONO = (select NUM_TELEFONO from INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_MT"+requestName+" where RUT_CLIENTE = '"+rut+"'),");
								queryDataLinePMD.append("TIP_TELEFONO = (select TIP_TELEFONO from INTELLECTCARDS.CUST_DETAILS_"+creationdate+"_MT"+requestName+" where RUT_CLIENTE = '"+rut+"')");
								queryDataLinePMD.append("Where RUT_CLIENTE = '"+rut+"';");

								
							
/*
								"UPDATE intellectcards.cust_details
								SET EMAIL_USUARIO = (select EMAIL_USUARIO from INTELLECTCARDS.CUST_DETAILS_20191012_MT11627 where RUT_CLIENTE = '188365752'),
								EMAIL_SERVIDOR = (select EMAIL_SERVIDOR from INTELLECTCARDS.CUST_DETAILS_20191012_MT11627 where RUT_CLIENTE = '188365752'),
								NUM_TELEFONO = (select NUM_TELEFONO from INTELLECTCARDS.CUST_DETAILS_20191012_MT11627 where RUT_CLIENTE = '188365752'),
								TIP_TELEFONO = (select TIP_TELEFONO from INTELLECTCARDS.CUST_DETAILS_20191012_MT11627 where RUT_CLIENTE = '188365752') 
								Where RUT_CLIENTE = '188365752'; 
								UPDATE intellectcards.cdmst
								SET EMAIL_ID = (select EMAIL_ID from INTELLECTCARDS.CDMST_20191012_MT11627 where RUT = '188365752'),
								CELL_NO = (select CELL_NO from INTELLECTCARDS.CDMST_20191012_MT11627 where RUT = '188365752'),
								ADDR_PHONE_2 = (select ADDR_PHONE_2 from INTELLECTCARDS.CDMST_20191012_MT11627 where RUT = '188365752')
								Where RUT = '188365752';"
								
*/
							/*queryDataLineSBf.append("UPDATE INTELLECTCARDS.CUST_DETAILS SET ADDR_BLOCK='N'"
									+ "WHERE RUT_CLIENTE='"+rut+"';");*/

							queryDataLinePMD.append(newline);
							queryDataLinePMD.append("commit;");
							
							resultQueryLines.put(line, queryDataLinePMD.toString());

							}
	
									
						}// fin del if (addrsBlock!=null) {
				});//fin del foreach
			}else {
				System.out.println ("RequestName not Informed");
				logger.error ("RequestName not Informed");
			}
			
			
			}else {
				System.out.println("Fono / mail de entrada vacios");
				logger.error("Fono / mail de entrada vacios");
			}

		
		return resultQueryLines;				
		
		
		
	}//Fin de createFMRollbackQuery
	
}//end of QueryTools
