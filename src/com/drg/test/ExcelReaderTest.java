package com.drg.test;

import com.drg.tools.ExcelReader;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.drg.data.CustDetailsData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

public class ExcelReaderTest {

	   @Test
	   public void uploadDataWithHeader() {
		   
		   ExcelReader excelReader = new ExcelReader();
		   

		   Map <Integer,CustDetailsData> customersDetails = new HashMap <Integer,CustDetailsData> ();
			
			customersDetails = excelReader.ReadCustDetailsDataFile("/Users/david/OneDrive/JavaProjects/ScriptCreator/Input/textExcel.xlsx");
//		    customersDetails = excelReader.ReadCustDetailsDataFile("/Users/david/OneDrive/JavaProjects/ScriptCreator/Input/Direcciones Incidente 7805.xlsx");


			
			System.out.println("En el test el customersDetails.size() = " + customersDetails.size());
			
			assertNotEquals(0,customersDetails.size());
	   }
	   
	   @Test
	   public void uploadDataWithOutHeader() {
		   
		   ExcelReader excelReader = new ExcelReader();
		   

		   Map <Integer,CustDetailsData> customersDetails = new HashMap <Integer,CustDetailsData> ();
			
			customersDetails = excelReader.readCustDetailsDataFile("/Users/david/OneDrive/JavaProjects/ScriptCreator/Input/textExcel.xlsx",false);
//		    customersDetails = excelReader.ReadCustDetailsDataFile("/Users/david/OneDrive/JavaProjects/ScriptCreator/Input/Direcciones Incidente 7805.xlsx",false);

			System.out.println("En ell test el customersDetails.size() = " + customersDetails.size());
			
			assertNotEquals(0,customersDetails.size());
	   }
	   
	
}// Fin de ExcelReaderTest
