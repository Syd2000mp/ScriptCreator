package com.drg.main;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.drg.data.CustDetailsData;
import com.drg.data.UtilityPmtData;
import com.drg.tools.ExcelReader;
import com.drg.tools.QueryTools;
import com.drg.tools.QueryFileWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class QueryGenerator {

	final static Logger logger = Logger.getLogger(QueryGenerator.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		 System.out.println("Generator options: ");
		 System.out.println("1 - Cust Details Adress Update ");
		 System.out.println("2 - Cust Details Adress Insert ");
		 System.out.println("3 - Cust Details Basic Insert ");
		 System.out.println("4 - Pat removal  ");
		 System.out.println("5 - Address Block  ");
		 System.out.println("");
		 
		 System.out.print("Please enter your option:  ");
		 
		 Scanner scanner = new Scanner(System.in);
         String opcion = scanner.nextLine();

         if (logger.isDebugEnabled()) {
        	 logger.debug("Your option is " + opcion);
         }

         String creationdate = "";			
		 String outputPath =  "";
		 String requestName =  "";
		 String excelFilepath =  "";
		 String schema = "";
        
     	Properties propFileData = new Properties();
    	InputStream input = null;

    	try {

    		input = new FileInputStream("resources/scriptCreator.properties");

    		// cargamos los valores
    		propFileData.load(input);

    		// vamos obteniendo los valores
    		
    		creationdate = propFileData.getProperty("creationdate");    		
    		outputPath = propFileData.getProperty("outputPath");
    		requestName = propFileData.getProperty("requestName");
    		excelFilepath = propFileData.getProperty("excelFilepath");
    		schema = propFileData.getProperty("schema");

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

 


         if (logger.isDebugEnabled()) {
        	 logger.debug("Seteo Inicial:");
        	 logger.debug("creationdate = " + creationdate);
        	 logger.debug("outputPath = " + outputPath);
        	 logger.debug("requestName = " + requestName);
        	 logger.debug("excelFilepath = " + excelFilepath);
         }

		 
		 switch (opcion){
			 case "1":
				 	generateCustDetailsUpdateFiles (creationdate, outputPath,excelFilepath, requestName,schema);
				 	break;
			 case "2":
				 	generateCustDetailsInsertFiles (creationdate, outputPath,excelFilepath, requestName,schema);
				 	break;
			 case "3":
				 	generateCustDetailsBasicInsertFiles (creationdate, outputPath,excelFilepath, requestName,schema);
				 	break;
			 case "4":
				 	generatePatRemovaFiles  (creationdate, outputPath,excelFilepath, requestName,schema);
				 	break;
			 case "5":
				 	generateBlockAdressFiles  (creationdate, outputPath,excelFilepath, requestName,schema);
				 	break;
			default:
				 System.out.println("Wrong option ");
				 break;
					
		 }
	}
	
	public static void generateCustDetailsUpdateFiles (String creationdate, String outputPath,String excelFilepath, String requestName,String schema) {
	   	
        if (logger.isDebugEnabled()) {
       	 logger.debug("Entrendo en generateCustDetailsUpdateFiles: ");
       	 logger.debug("creationdate = " + creationdate);
       	 logger.debug("outputPath = " + outputPath);
       	 logger.debug("excelFilepath = " + excelFilepath);
       	 logger.debug("requestName = " + requestName);
        }

		String filename = "";


		
		/* Pasos:
		 * Leer el excel
		 * Generar Script de backup (generar Query, calcular nombre, generar archivo
		 * generar Script de creaci贸n
		 * generar Script de rollback
		 */

		QueryTools queryTools = new QueryTools();
		QueryFileWriter queryFileWriter = new QueryFileWriter ();
		
		//Creating BackupQuery
		Map <Integer,String> backupQuery = new HashMap <Integer,String> ();
				
		Map <Integer,String> tablas= new HashMap <Integer,String> ();
		tablas.put(new Integer (0), "CUST_DETAILS");
		tablas.put(new Integer (1), "CDMST");

		backupQuery = queryTools.createBackupQuery(requestName,creationdate,schema,tablas);
	
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}else {
			filename = "01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}

		queryFileWriter.writeQueryFile(backupQuery, outputPath, filename, schema);
		
		//Creating CustDetailsQuery
		
		Map <Integer,CustDetailsData> custDetailsData = new HashMap <Integer,CustDetailsData> ();
		
		custDetailsData = ExcelReader.readCustDetailsDataFile (excelFilepath, true);
		Map <Integer,String> custDetailsQuery= new HashMap <Integer,String> ();
		custDetailsQuery = queryTools.createCustDetailsUpdateQuery(custDetailsData);

		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"02_" + requestName +"_Update_CustomerDetailsTables_" + creationdate +".sql";
		}else {
			filename = "02_" + requestName +"_Update_CustomerDetailsTables_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(custDetailsQuery, outputPath, filename, schema);
		
		//Creating RollbackQuery
		Map <Integer,String> rollbackQuery = new HashMap <Integer,String> ();
		rollbackQuery = queryTools.createCustDetailsRollbackQuery (requestName,creationdate, schema, tablas);
		
		//@TODO: Pendiente de terminar el servicio
		//rollbackQuery = cdmstRollbackQueryCreator(requestName, creationdate, schema, custDetailsData, rollbackQuery )

		
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}else {
			filename = "03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(rollbackQuery, outputPath, filename, schema);
		
		
		System.out.println("Se completo la ejecucion con exito.");
		logger.info("Se completo la ejecucion con exito.");
		
	}//generateCustDetailsUpdateFiles

	public static void generateCustDetailsInsertFiles (String creationdate, String outputPath,String excelFilepath, String requestName,String schema) {

        if (logger.isDebugEnabled()) {
         	 logger.debug("Entrendo en generateCustDetailsInsertFiles: ");
         	 logger.debug("creationdate = " + creationdate);
         	 logger.debug("outputPath = " + outputPath);
         	 logger.debug("excelFilepath = " + excelFilepath);
         	 logger.debug("requestName = " + requestName);
          }
	   	
		String filename = "";
				
		/* Pasos:
		 * Leer el excel
		 * Generar Script de backup (generar Query, calcular nombre, generar archivo
		 * generar Script de creaci贸n
		 * generar Script de rollback
		 */

		QueryTools queryTools = new QueryTools();
		QueryFileWriter queryFileWriter = new QueryFileWriter ();
		
		//Creating BackupQuery
		Map <Integer,String> backupQuery = new HashMap <Integer,String> ();
				
		Map <Integer,String> tablas= new HashMap <Integer,String> ();
		tablas.put(new Integer (0), "CUST_DETAILS");
		tablas.put(new Integer (1), "CDMST");

		backupQuery = queryTools.createBackupQuery(requestName,creationdate,schema,tablas);
	
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}else {
			filename = "01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}

		queryFileWriter.writeQueryFile(backupQuery, outputPath, filename, schema);
		
		//Creating CustDetailsQuery
		
		Map <Integer,CustDetailsData> custDetailsData = new HashMap <Integer,CustDetailsData> ();
		
		custDetailsData = ExcelReader.readCustDetailsDataFile (excelFilepath, true);
		Map <Integer,String> custDetailsQuery= new HashMap <Integer,String> ();
		custDetailsQuery = queryTools.createCustDetailsInsertQuery(custDetailsData);

		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}else {
			filename = "02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(custDetailsQuery, outputPath, filename, schema);
		
		//Creating RollbackQuery
		Map <Integer,String> rollbackQuery = new HashMap <Integer,String> ();
		rollbackQuery = queryTools.createCustDetailsRollbackQuery (requestName,creationdate, schema, tablas);
		
		//@TODO: Pendiente de terminar el servicio
		//rollbackQuery = cdmstRollbackQueryCreator(requestName, creationdate, schema, custDetailsData, rollbackQuery )

		
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}else {
			filename = "03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(rollbackQuery, outputPath, filename, schema);
		
		
		System.out.println("Se completo la ejecucion con exito.");
		logger.info("Se completo la ejecucion con exito.");
		
	}//generateCustDetailsInsertFiles
	
	public static void generateCustDetailsBasicInsertFiles (String creationdate, String outputPath,String excelFilepath, String requestName,String schema) {

        if (logger.isDebugEnabled()) {
          	 logger.debug("Entrendo en generateCustDetailsBasicInsertFiles: ");
          	 logger.debug("creationdate = " + creationdate);
          	 logger.debug("outputPath = " + outputPath);
          	 logger.debug("excelFilepath = " + excelFilepath);
          	 logger.debug("requestName = " + requestName);
           }
	   	
		String filename = "";
		
		
		/* Pasos:
		 * Leer el excel
		 * Generar Script de backup (generar Query, calcular nombre, generar archivo
		 * generar Script de creaci贸n
		 * generar Script de rollback
		 */

		QueryTools queryTools = new QueryTools();
		QueryFileWriter queryFileWriter = new QueryFileWriter ();
		
		//Creating BackupQuery
		Map <Integer,String> backupQuery = new HashMap <Integer,String> ();
				
		Map <Integer,String> tablas= new HashMap <Integer,String> ();
		tablas.put(new Integer (0), "CUST_DETAILS");
		//tablas.put(new Integer (1), "CDMST");

		backupQuery = queryTools.createBackupQuery(requestName,creationdate,schema,tablas);
	
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}else {
			filename = "01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}

		queryFileWriter.writeQueryFile(backupQuery, outputPath, filename, schema);
		
		//Creating CustDetailsQuery
		
		Map <Integer,CustDetailsData> custDetailsData = new HashMap <Integer,CustDetailsData> ();
		
		custDetailsData = ExcelReader.readCustDetailsDataFile (excelFilepath, true);
		Map <Integer,String> custDetailsQuery= new HashMap <Integer,String> ();
		custDetailsQuery = queryTools.createCustDetailsBasicInsertQuery(custDetailsData);

		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}else {
			filename = "02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(custDetailsQuery, outputPath, filename, schema);
		
		//Creating RollbackQuery
		Map <Integer,String> rollbackQuery = new HashMap <Integer,String> ();
		rollbackQuery = queryTools.createCustDetailsRollbackQuery (requestName,creationdate, schema, tablas);
		
		//@TODO: Pendiente de terminar el metodo de rollback solo con los registros de este caso
		//rollbackQuery = cdmstRollbackQueryCreator(requestName, creationdate, schema, custDetailsData, rollbackQuery )

		
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}else {
			filename = "03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(rollbackQuery, outputPath, filename, schema);
		
		
		System.out.println("Se completa la ejecucion con exito.");
		logger.info("Se completa la ejecucion con exito.");
		
	}//generateCustDetailsBasicInsertFiles
	
	public static void generatePatRemovaFiles (String creationdate, String outputPath,String excelFilepath, String requestName,String schema) {
		
        if (logger.isDebugEnabled()) {
         	 logger.debug("Entrendo en generatePatRemovaFiles: ");
         	 logger.debug("creationdate = " + creationdate);
         	 logger.debug("outputPath = " + outputPath);
         	 logger.debug("excelFilepath = " + excelFilepath);
         	 logger.debug("requestName = " + requestName);
          }
	   	
		String filename = "";
		
		
		/* Pasos:
		 * Leer el excel
		 * Generar Script de backup (generar Query, calcular nombre, generar archivo
		 * generar Script de creaci贸n
		 * generar Script de rollback
		 */

		QueryTools queryTools = new QueryTools();
		QueryFileWriter queryFileWriter = new QueryFileWriter ();
		
		//Creating BackupQuery
		Map <Integer,String> backupQuery = new HashMap <Integer,String> ();
				
		Map <Integer,String> tablas= new HashMap <Integer,String> ();
		tablas.put(new Integer (0), "UTILITY_PYMT");

		backupQuery = queryTools.createBackupQuery(requestName,creationdate,schema,tablas);
	
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"01_" + requestName +"_Backup_UtilityPymtTables_" + creationdate +".sql";
		}else {
			filename = "01_" + requestName +"_Backup_UtilityPymtTables_" + creationdate +".sql";
		}

		queryFileWriter.writeQueryFile(backupQuery, outputPath, filename, schema);
		
		//Creating CustDetailsQuery
		
		Map <Integer,UtilityPmtData> utilityPmtData = new HashMap <Integer,UtilityPmtData> ();
		
		utilityPmtData = ExcelReader.readUtilityPmtDataFile (excelFilepath, true);
		
		Map <Integer,String> utilityPmtQuery= new HashMap <Integer,String> ();
		
		utilityPmtQuery = queryTools.createPATDEsafiliationQuery(utilityPmtData,requestName);

		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"02_" + requestName +"_Update_UtilityPymtTables_" + creationdate +".sql";
		}else {
			filename = "02_" + requestName +"_Update_UtilityPymtTables_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(utilityPmtQuery, outputPath, filename, schema);
		
		//Creating RollbackQuery
		Map <Integer,String> rollbackQuery = new HashMap <Integer,String> ();
		rollbackQuery = queryTools.createCustDetailsRollbackQuery (requestName,creationdate, schema, tablas);
		
		
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}else {
			filename = "03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(rollbackQuery, outputPath, filename, schema);
		
		
		System.out.println("Se completo la ejecucion con exito.");
		logger.info("Se completo la ejecucion con exito.");
		

		
	}//generatePatRemovaFiles

	public static void generateBlockAdressFiles (String creationdate, String outputPath,String excelFilepath, String requestName,String schema) {

        if (logger.isDebugEnabled()) {
          	 logger.debug("Entrendo en generateBlockAdressFiles: ");
          	 logger.debug("creationdate = " + creationdate);
          	 logger.debug("outputPath = " + outputPath);
          	 logger.debug("excelFilepath = " + excelFilepath);
          	 logger.debug("requestName = " + requestName);
           }
	   	
		String filename = "";
		
		
		/* Pasos:
		 * Leer el excel
		 * Generar Script de backup (generar Query, calcular nombre, generar archivo
		 * generar Script de creaci贸n
		 * generar Script de rollback
		 */

		QueryTools queryTools = new QueryTools();
		QueryFileWriter queryFileWriter = new QueryFileWriter ();
		
		//Creating BackupQuery
		Map <Integer,String> backupQuery = new HashMap <Integer,String> ();
				
		Map <Integer,String> tablas= new HashMap <Integer,String> ();
		tablas.put(new Integer (0), "CUST_DETAILS");

		//TODO: Hacer el cambio para que ocupe el m閠odo createRutBackupQuery 
		backupQuery = queryTools.createBackupQuery(requestName,creationdate,schema,tablas);
			
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}else {
			filename = "01_" + requestName +"_BackupTablesScript" + creationdate +".sql";
		}

		queryFileWriter.writeQueryFile(backupQuery, outputPath, filename, schema);
		
		
		Map <Integer,AddrsBlockData> addrsBlockData = new HashMap <Integer,AddrsBlockData> ();
		
		addrsBlockData = ExcelReader.readCustDetailsDataFile (excelFilepath, true);
		Map <Integer,String> addrsBlockQuery= new HashMap <Integer,String> ();
		addrsBlockQuery = queryTools.createAddrsBlockInsertQuery(addrsBlockData);

		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}else {
			filename = "02_" + requestName +"_InsertTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(addrsBlockQuery, outputPath, filename, schema);
		
		//Creating RollbackQuery
		Map <Integer,String> rollbackQuery = new HashMap <Integer,String> ();
		rollbackQuery = queryTools.createCustDetailsRollbackQuery (requestName,creationdate, schema, tablas);
		
		//@TODO: Pendiente de terminar el metodo de rollback solo con los registros de este caso
		//rollbackQuery = cdmstRollbackQueryCreator(requestName, creationdate, schema, custDetailsData, rollbackQuery )

		
		if ((outputPath != null) && (outputPath.equals(""))) {
			
			filename = outputPath +"03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}else {
			filename = "03_" + requestName +"_RollbackTablesScript_" + creationdate +".sql";
		}
		
		queryFileWriter.writeQueryFile(rollbackQuery, outputPath, filename, schema);
		
		
		System.out.println("Se completo la ejecucion con exito.");
		logger.info("Se completo la ejecucion con exito.");
		
	}//generateBlockAdressFiles


}// Fin de QueryGenerator
