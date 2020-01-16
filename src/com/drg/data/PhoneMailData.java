package com.drg.data;

public class PhoneMailData {

	/*
	Se asume que el orden de las columnas en el archivo es: 
	0: rut
	1: phoneNum
	2: mailId
	3: serverId
	*/

String rut = null;
String phoneNum = null;
String mailId = null;
String serverId = null;

public String getRut() {
	return rut;
}
public void setRut(String rut) {
	this.rut = rut;
}
public String getPhoneNum() {
	return phoneNum;
}
public void setPhoneNum(String phoneNum) {
	this.phoneNum = phoneNum;
}
public String getMailId() {
	return mailId;
}
public void setMailId(String mailId) {
	this.mailId = mailId;
}
public String getServerId() {
	return serverId;
}
public void setServerId(String serverId) {
	this.serverId = serverId;
}


}//Fin de PhoneMailData
