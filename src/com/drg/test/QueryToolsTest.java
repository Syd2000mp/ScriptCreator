package com.drg.test;

import java.util.HashMap;
import java.util.Map;

import com.drg.tools.QueryTools;
import com.drg.tools.ExcelReader;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import com.drg.data.CustDetailsData;

public class QueryToolsTest {
	
	@Test
	public void custDetailsQueryTest(){
		
		   Map <Integer,CustDetailsData> custDetailsData = new HashMap <Integer,CustDetailsData> ();
		   ExcelReader excelReader = new ExcelReader();
			
			//customersDetails = excelReader.ReadCustDetailsDataFile("/Users/david/OneDrive/JavaProjects/ScriptCreator/textExcel.xlsx");
		   custDetailsData = excelReader.readCustDetailsDataFile("D:\\David\\OneDrive\\JavaProjects\\ScriptCreator\\Direcciones Incidente 7805.xlsx");

			QueryTools queytool = new QueryTools();
       	
		Map <Integer,String> custDetailsQueryLines = queytool.createCustDetailsUpdateQuery (custDetailsData);
	
	}//Fin de custDetailsQueryTest

	
}
