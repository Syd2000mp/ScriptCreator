package com.drg.test;

import com.drg.tools.CommonTools;
import com.drg.tools.ExcelReader;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class CommonToolsTest {
	
	final static Logger logger = Logger.getLogger(CommonToolsTest.class);

	
	   @Test
	   public void correctChracters() {
		   		   
		   String input = "O'Higgins";
		   
		   String testOutput = CommonTools.specialCharactersCorrector(input,'\'');

		   logger.debug("input = " + input);
		   logger.debug("testOutput = " + testOutput);
		   
			
			assertNotEquals(0,testOutput.indexOf('\''));
	   }

	   @Test
	   public void correctChracterAsterisc() {
		   
		   String input = "O'Higgins";
		   
		   String testOutput = CommonTools.specialCharactersCorrector(input,'*');

		   logger.debug("input = " + input);
		   logger.debug("testOutput = " + testOutput);
		   
			
			assertNotEquals(0,testOutput.indexOf('\''));
	   }

	   @Test
	   public void emptyInput() {
		   
		   String input = "";
		   
		   String testOutput = CommonTools.specialCharactersCorrector(input,'*');

		   logger.debug("input = " + input);
		   logger.debug("testOutput = " + testOutput);
		   
			
			assertNotEquals(0,testOutput.indexOf('\''));
	   }

	   @Test
	   public void splitMailTest1() {
		   
		//String email = "prueba1@telnet.com";
		//String email = "prueba1.telnet.com";
		//String email = "@prueba1.telnet.com";
		String email = "prueba1.telnet.com@";
		//String email = "";
		//String email = null;

		Map <Integer,String> mailParts = CommonTools.splitMail(email);			
				
		logger.debug("email = " + email);
		logger.debug("email_usuario = " + mailParts.get(0));
		logger.debug("email_servidor = " + mailParts.get(1));
		   
	   }


}
