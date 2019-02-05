package com.drg.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.drg.data.CustDetailsData;

public class TestRunner {
   public static void main(String[] args) {
      
	   System.out.println("Ejecutando test de Excereader ");
	   
	   Result result = JUnitCore.runClasses(ExcelReaderTest.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println("resutado: "+ result.wasSuccessful());

	   System.out.println("Ejecutando test de QuearyTools ");
	   
	   result = JUnitCore.runClasses(QueryToolsTest.class);
		
     for (Failure failure : result.getFailures()) {
        System.out.println(failure.toString());
     }
		
     System.out.println("resutado: "+ result.wasSuccessful());

   }
}//End de TestRunner