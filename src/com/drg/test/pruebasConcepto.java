package com.drg.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class pruebasConcepto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		  LocalDate date = LocalDate.now();

		  String day = new Integer (date.getDayOfMonth()).toString();
		  String month = new Integer (date.getMonth()).toString();
		  String year = new Integer (date.getYear()).toString();
		
		
		String creationdate = year+month+day;
		
		System.out.println("creationdate = "+creationdate);
*/		
		/*
		  LocalDate date = LocalDate.now();
		  DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		  String text = date.format(formatter);
		  LocalDate parsedDate = LocalDate.parse(text, formatter);
		
		  System.out.println("parsedDate = "+parsedDate);
		  
		  testLocalDateTime();
		  */
		  divisor();

	}
	
	 public static void testLocalDateTime() {
	      // Get the current date and time
	      LocalDateTime currentTime = LocalDateTime.now();
	      System.out.println("Current DateTime: " + currentTime);
			
	      LocalDate date1 = currentTime.toLocalDate();
	      System.out.println("date1: " + date1);
			
	      Month month = currentTime.getMonth();
	      int day = currentTime.getDayOfMonth();
	      int seconds = currentTime.getSecond();
			
	      System.out.println("Month: " + month +" day: " + day +"seconds: " + seconds);
			
	      LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
	      System.out.println("date2: " + date2);
			
	      //12 december 2014
	      LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
	      System.out.println("date3: " + date3);
			
	      //22 hour 15 minutes
	      LocalTime date4 = LocalTime.of(22, 15);
	      System.out.println("date4: " + date4);
			
	      //parse a string
	      LocalTime date5 = LocalTime.parse("20:15:30");
	      System.out.println("date5: " + date5);
	   }

	 
	 public static void divisor() {
		 
		 int numero = 300;
		 System.out.println("numero = " + numero);
		 
		 float div = (float)numero%500;
		 System.out.println("div = " + div);
		 if (div==0.0) {
			 System.out.println("es multiplo");
		 }else {
			 System.out.println("es NO multiplo");
		 }
		 
		 numero = 500;
		 System.out.println("numero = " + numero);
		 
		 div = (float)numero%500;
		 System.out.println("div = " + div);
		 
		 if (div==0.0) {
			 System.out.println("es multiplo");
		 }else {
			 System.out.println("es NO multiplo");
		 }

		 numero = 2000;
		 System.out.println("numero = " + numero);
		 if (div==0.0) {
			 System.out.println("es multiplo");
		 }else {
			 System.out.println("es NO multiplo");
		 }

		 div = (float)numero%500;
		 System.out.println("div = " + div);

		 numero = 5000;
		 System.out.println("numero = " + numero);
		 
		 div = (float)numero%500;
		 System.out.println("div = " + div);

		 numero = 10000;
		 System.out.println("numero = " + numero);

		 div = (float)numero%500;
		 System.out.println("div = " + div);

		 numero = 12500;
		 System.out.println("numero = " + numero);
		 
		 div = (float)numero%500;
		 System.out.println("div = " + div);

		 numero = 15550;
		 System.out.println("numero = " + numero);

		 div = (float)numero%500;
		 System.out.println("div = " + div);
		 
		 
	 }
}
