package com.drg.data;

public class AddrsBlockData {
	
	/*
	Se asume que el orden de las columnas en el archivo es: 
	0: rut
	1: codDireccion
	2: causalDevol
	*/

String rut = null;
String codDireccion = null;
String causalDevol = null;

public String getRut() {
	return rut;
}
public void setRut(String rut) {
	this.rut = rut;
}
public String getCodDireccion() {
	return codDireccion;
}
public void setCodDireccion(String codDireccion) {
	this.codDireccion = codDireccion;
}
public String getCausalDevol() {
	return causalDevol;
}
public void setCausalDevol(String causalDevol) {
	this.causalDevol = causalDevol;
}


}//Fin de AddrsBlockData
