package com.drg.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.log4j.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import com.drg.data.CustDetailsData;
import com.drg.data.UtilityPmtData; 


public class ExcelReader {
	
	final static Logger logger = Logger.getLogger(ExcelReader.class);


	public static Map <Integer,CustDetailsData> readCustDetailsDataFile (String filepath){

     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in readCustDetailsDataFile with filepath= " + filepath);
 		}
		
		 Map <Integer,CustDetailsData> customersDetails = new HashMap <Integer,CustDetailsData> ();
		
		 customersDetails = readCustDetailsDataFile (filepath, true);
		 
		 return customersDetails;
		
	}// fin de sin control de cabecera


	public static Map <Integer,CustDetailsData> readCustDetailsDataFile (String filepath, boolean withHeader){
		
     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in readCustDetailsDataFile with filepath= " + filepath + "and withHeader = " + withHeader);
 		}

		logger.info("Leyendo el archivo de entrada " + filepath);
		System.out.println("Leyendo el archivo de entrada " + filepath);

     	
		Map <Integer,CustDetailsData> customersDetails = new HashMap <Integer,CustDetailsData> ();
		 
		 Map <Integer,Row> excellines = ReadExcelDataFile (filepath);

		 if ((excellines!=null)&(!excellines.isEmpty()))
		 {
			 excellines.forEach((pos,fila)->{
				 if (fila!=null) {
					 CustDetailsData customerData = readRowCellsCustDetails(fila);
					 customersDetails.put(pos, customerData);
				 }	
				});


		 } else {
			 logger.fatal("Archivo excel vacio");
		 }
		  
		 return customersDetails;
		 
	}// Fin de ReadCustDetailsDataFile con control de cabecera
	
	private static CustDetailsData readRowCellsCustDetails(Row fila) {
		
			/* Controlo que la fila sea mayor a 0 porque trae cabecera y los datos empiezan en
			la línea 2*/
			
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
		
			
	// Creo un DataFormatter para leer todos los datos como String
    	DataFormatter dataFormatter = new DataFormatter();
	
		
		CustDetailsData datosLinea = new CustDetailsData ();
		int nullfields = 0;
    	
    	Cell valorCelda = fila.getCell(0);
    	String svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setRut(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Rut = " + svalorCelda);
    		}
        	
    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(1);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setCuenta(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Cuenta = " + svalorCelda);
    		}
        	
    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(2);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setNombreCompleto(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("NombreCompleto = " + svalorCelda);
    		}
        	
    	}else {
    		nullfields++;
    	}

       	valorCelda = fila.getCell(3);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setTipoDireccion(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("TipoDireccion = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}
   	
    	
    	valorCelda = fila.getCell(4);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setCalle(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Calle = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(5);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setDetalleDir(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("DetalleDir = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(6);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setNumero(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Numero = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(7);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setComuna(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Comuna = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(8);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setRegion(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Region = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}
    	
  	
    	valorCelda = fila.getCell(9);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setCodPostal(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("CodPostal = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(10);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setPrimerNombre(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("PrimerNombre = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(11);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setSegundoNombre(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("SegundoNombre = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(12);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setPrimerApellido(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("PrimerApellido = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(13);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setSegundoApellido(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("SegundoApellido = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(14);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setIndEECCMail(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("IndEECCMail = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(15);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setEmail(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("Email = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(16);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setCodDireccion(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("CodDireccion = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(17);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setMotivoRechazo(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("MotivoRechazo = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}

    	valorCelda = fila.getCell(18);
    	svalorCelda = dataFormatter.formatCellValue(valorCelda);
    	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
        	datosLinea.setDescMotivoRechazo(svalorCelda);

        	if (logger.isDebugEnabled()){
    			logger.debug("DescMotivoRechazo = " + svalorCelda);
    		}

    	}else {
    		nullfields++;
    	}


    	if (nullfields<18) {
    		return datosLinea;
    	}else {
    		//Se retorna null para que la línea no se añada a la colección
    		return null;

    	}
    	
		
	}//Fin de readRowCells
	
	public static Map <Integer,UtilityPmtData> readUtilityPmtDataFile (String filepath){

		 Map <Integer,UtilityPmtData> patDesafiliation = new HashMap <Integer,UtilityPmtData> ();


     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in readUtilityPmtDataFile with filepath= " + filepath);
 		}

		
		 patDesafiliation = readUtilityPmtDataFile (filepath, true);
		 
		 return patDesafiliation;
		
	}// fin de readUtilityPmtDataFile


	public static Map <Integer,UtilityPmtData> readUtilityPmtDataFile (String filepath, boolean withHeader){
		
     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in readUtilityPmtDataFile with filepath= " + filepath + "and withHeader = " + withHeader);
 		}

		 Map <Integer,UtilityPmtData> patDesafiliation = new HashMap <Integer,UtilityPmtData> ();
		 
		 Map <Integer,Row> excellines = ReadExcelDataFile (filepath);

		 int numlineas = excellines.size();
		 UtilityPmtData patDesafiliationLine = new UtilityPmtData();
		 
		 if (numlineas > 0) {
			 		
			 for (int i=0;i<numlineas;i++) {
				 
				 Integer posicion = new Integer(i);
				 Row fila = excellines.get(posicion);
				 if (fila!=null) {
					 patDesafiliationLine = readRowCellsDesafiliacion(fila);
					 patDesafiliation.put(posicion, patDesafiliationLine);
				 }
				 
			 }
			 
		 } else {
		     	if (logger.isDebugEnabled()){
		 			logger.debug("Archivo excel de desafiliaciones vacio");
		 		}

		 }
		  
		 return patDesafiliation;
		 
	}// Fin de ReadCustDetailsDataFile con control de cabecera

	private static UtilityPmtData readRowCellsDesafiliacion(Row fila) {
		
		/* Controlo que la fila sea mayor a 0 porque trae cabecera y los datos empiezan en
		la línea 2*/
		
		/*
		Se asume que el orden de las columnas en el archivo es: 
		0: rut
		1: rubro
		*/
		
// Creo un DataFormatter para leer todos los datos como String
	DataFormatter dataFormatter = new DataFormatter();

	
	UtilityPmtData datosLinea = new UtilityPmtData ();
	int nullfields = 0;
	
	Cell valorCelda = fila.getCell(0);
	String svalorCelda = dataFormatter.formatCellValue(valorCelda);
	
	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
    	datosLinea.setRut(svalorCelda);

    	if (logger.isDebugEnabled()){
 			logger.debug("Rut = " + svalorCelda);
 		}

	}else {
		nullfields++;
	}

   	valorCelda = fila.getCell(1);
	svalorCelda = dataFormatter.formatCellValue(valorCelda);
	if ((svalorCelda!=null)&& (!svalorCelda.equalsIgnoreCase(""))){
    	datosLinea.setRubro(svalorCelda);

    	if (logger.isDebugEnabled()){
 			logger.debug("Rubro = " + svalorCelda);
 		}

	}else {
		nullfields++;
	}
	
	if (nullfields==0) { // Se valida que sea cero porque si alguno de los valores de línea es nulo, no se puede usar
		return datosLinea;
	}else {
		//Se retorna null para que la línea no se añada a la colección
    	if (logger.isDebugEnabled()){
 			logger.debug("Se rechaza la línea por tener un valor nulo o vacío que no sirve en la query");
 		}
		return null;

	}

	
}//Fin de readRowCellsDesafiliacion

	private static Map <Integer,Row> ReadExcelDataFile (String filepath){

     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in ReadExcelDataFile with filepath= " + filepath);
 		}

		 Map <Integer,Row> excellines = new HashMap <Integer,Row> ();

		
		 excellines = ReadExcelDataFile (filepath, true);
		 
		 return excellines;
		
	}// fin de sin control de cabecera
	
	
	private static Map <Integer,Row> ReadExcelDataFile (String filepath, boolean withHeader){

     	if (logger.isDebugEnabled()){
 			logger.debug("Entering in ReadExcelDataFile with filepath= " + filepath + " and withHeader = " + withHeader);
 		}

		 Map <Integer,Row> excellines = new HashMap <Integer,Row> ();
	
		 if ((filepath==null)|| (filepath.equals("")))
		 {
			 return null;
		 }else {
			 //creamos un Workbook
			  try {

			     	if (logger.isDebugEnabled()){
			 			logger.debug("filepath al workbook= " + filepath);
			 		}
			  			     	
				  Workbook workbookToRead = WorkbookFactory.create(new File (filepath));
				
				// Recupero la cantidad de hojas en el archivo excel
				int sheetsNumber = workbookToRead.getNumberOfSheets();
				
		     	if (logger.isDebugEnabled()){
		 			logger.debug("File To Read: " + filepath);
		 			logger.debug("Number of Sheets in file: " + sheetsNumber);
		 		}
			
				//Si tiene hojas empiezo a recorrerlas
				if (sheetsNumber > 0)
				{
					/* Itero la colección de hojas del archivo con una expresión lambda de Java8 */
					
						workbookToRead.forEach(sheet -> {
			            
						String sheetName = sheet.getSheetName();
						
				     	if (logger.isDebugEnabled()){
				 			logger.debug("Hoja a cargar => " + sheetName);
				 		}
											
						Sheet hojas =  workbookToRead.getSheet(sheetName);
						
						//Recorro el libro por filas, celda a celda y lo voy guardando en el CustDetailsData
						hojas.forEach(fila -> {
							
								int numFila = fila.getRowNum();
						     	if (logger.isDebugEnabled()){
						 			logger.debug("<===================== leyendo fila " + numFila + "=====================>");
						 		}
								
								if ((withHeader == true) && (numFila>0)){
									
									excellines.put(new Integer(numFila),fila);
																		
								}else if (withHeader == false){
									excellines.put(new Integer(numFila),fila);
								}
								
					        		if ((withHeader == true) && (numFila==0)) {
						        		
								     	if (logger.isDebugEnabled()){
								 			logger.debug("Linea de cabecera que no se añade");
								 		}
					        			
					        		}else {

					        			if (logger.isDebugEnabled()){
								 			logger.debug("Linea vacía no añadida a la colección");
								 		}
					        		
					        		}
							     	if (logger.isDebugEnabled()){
							 			logger.debug("<================= fin de fila "+ fila.getRowNum()+ "==========================>");
							 		}
									


				        });//Fin del iterador de filas
						
			        });//Fin del iterador de hojas del archivo

					// Cierro workbook
					workbookToRead.close();
					
					
				}// fin del bucle que recorre las hojas
				else 
				{
			 		logger.fatal("No existen hojas para leer");
					return null;
				}
				

			  } catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		 }//fin del else
		 
		  
		 return excellines;
		 
	}// Fin de ReadPatDesafiliationDataFile con control de cabecera


	
}
