package com.drg.tools;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.drg.main.QueryGenerator;

public class CommonTools {

	final static Logger logger = Logger.getLogger(QueryGenerator.class);
	
	public static String specialCharactersCorrector(String input, char specialChar) {
		String output = "";
		
		if ((input==null)||(input.equalsIgnoreCase(""))){
			return input;
		}
		
		if (specialChar==' '){
			return input;
		}
		
		int specialCharPosition = input.indexOf(specialChar);
		int inputlegth = input.length();
		
		if (specialCharPosition>-1) {
			output = input.substring(0, specialCharPosition) + '\'' + input.substring(specialCharPosition, inputlegth);;
		}else {
			output = input;
		}

        if (logger.isDebugEnabled()) {
          	 logger.debug("Entrendo en specialCharactersCorrector: ");
          	 logger.debug("input = " + input);
          	 logger.debug("specialChar = " + specialChar);
          	 logger.debug("output = " + output);
           }

		return output;
	}//Fin de SpecialcharactersCorrector
	
	public static Map <Integer,String> splitMail(String email) {
		
		Map <Integer,String> mailParts = new HashMap <Integer,String> ();

		String email_usuario = null;
		String email_servidor = null;
	
		if ((email!=null)&&(!"".equals(email))) {
			int arrobapos = email.indexOf("@");
			if (arrobapos > 0){

				email_usuario = email.substring(0, arrobapos);
				email_servidor = email.substring(arrobapos+1,email.length());
			}
		}

        if (logger.isDebugEnabled()) {
         	 logger.debug("Entrendo en splitMail: ");
         	 logger.debug("email = " + email);
         	 logger.debug("email_usuario = " + email_usuario);
         	 logger.debug("email_servidor = " + email_servidor);
          }

        mailParts.put(0, email_usuario);
		mailParts.put(1, email_servidor);

		return mailParts;
	}//splitMail

}
