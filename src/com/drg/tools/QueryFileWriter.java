package com.drg.tools;

import java.util.Map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.IllegalArgumentException;

import org.apache.log4j.Logger;

public class QueryFileWriter {
	
	final static Logger logger = Logger.getLogger(QueryFileWriter.class);


	public void writeQueryFile (Map <Integer,String> body, String outputPath, String filename, String schema){
		
		if (body.isEmpty()) {
 			logger.fatal("Query body empty for this query request");
			throw new IllegalArgumentException ("Query body empty for this query request");
		}
		
	
		String filePath = outputPath + filename;
		
		String logFilename = filename +".log";
		
		
		try {
			Path path = Paths.get(filePath);
			
			if(Files.exists(path)){
				// Si existe el archivo lo borramos para recrearlo
				Files.delete(path);
			}
			
			Files.createFile(path);

			BufferedWriter writer = Files.newBufferedWriter(path);
			
			String fileHeader ="";
			
			if ((schema!=null)&& (!"".equals(schema))) {
				fileHeader = scriptHeaderBuilder (logFilename, schema);				
			}else {
	 			logger.fatal("Schema the query no informado");
				throw new IllegalArgumentException ("Schema the query no informado");
				
			}
			
			writer.write(fileHeader);

			
			body.forEach((pos,linea)->{
			
				try {
					writer.write(linea);
					writer.newLine();

					 float mod = (float)pos%100; /*Se baja la cantidad de lineas a 500 por problemas con el script*/
					 if (mod==0.0) {
							writer.write("COMMIT;");
							writer.newLine();
					 }
					
					
				} catch (IOException e) {
					// TODO poner bien los comentarios
					e.printStackTrace();
				}
				
			});
			
			String filebottom = scriptBottomBuilder();
			writer.write(filebottom);
			
			writer.close();
			
		} catch (IOException ioe) {
 			logger.fatal("Exception while writing query file: "+ ioe.getMessage());
	 			
 			ioe.printStackTrace();
			
		}catch (RuntimeException re) {
			
 			logger.fatal("Exception while writing query file: " + re.getMessage());
			re.printStackTrace();
		}
	

		
	}// fin de writeQueryFile
	
	public void writeQueryFile (Map <Integer,String> body, String outputPath, String schema){
		
		/* Version del writeQueryFile
		 *  que genera el nombre de archivo de manera dinámica, porque cuenta la cantidad de líneas incluidas en el archivo
		 *  y va generando un nuevo archivo cada 1000 lineas.
		 * 
		 * */
		//TODO: más adelante, mover el archivo de las 1000 lineas a un archivo de propiedades.
		
	}//Fin de writeQueryFile sin nombre de archivo
		
	
	private String scriptHeaderBuilder (String logFileName, String schemaName) {
				
		StringBuffer header = new StringBuffer();
		String saltoLinea = System.getProperty("line.separator");
		
		header.append("SPOOL " + logFileName);
		header.append(saltoLinea);
		header.append("SET ECHO ON");
		header.append(saltoLinea);
		header.append("SET TIME ON"); 
		header.append(saltoLinea);
		header.append("SET TIMING ON");
		header.append(saltoLinea);
		header.append("SET TRIMSPOOL ON");
		header.append(saltoLinea);
		header.append("SET SERVEROUTPUT ON");
		header.append(saltoLinea);
		header.append("ALTER SESSION SET CURRENT_SCHEMA = "+schemaName +";");
		header.append(saltoLinea);
		
		return header.toString();
	}//scriptheaderBuilder

	private String scriptBottomBuilder() {
		
		StringBuffer bottom = new StringBuffer();
		String saltoLinea = System.getProperty("line.separator");
		
		bottom.append("commit;");
		bottom.append(saltoLinea);
		bottom.append("SPOOL OFF");
		bottom.append(saltoLinea);
		bottom.append("EXIT");
		bottom.append(saltoLinea);
		
		return bottom.toString();
	}//scriptheaderBuilder

}
