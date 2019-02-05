package com.drg.data;

public class CustDetailsData {

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
	*/

String rut = null;
String cuenta = null;
String nombreCompleto = null;
String tipoDireccion = null;
String calle = null;
String detalleDir = null;
String numero = null;
String comuna = null;
String region = null;
String codPostal = null;
String primerNombre = null;
String segundoNombre = null;
String primerApellido = null;
String segundoApellido = null;
String indEECCMail = null;
String email = null;
String codDireccion = null;
String motivoRechazo = null;
String descMotivoRechazo = null;



public String getRut() {
	return rut;
}
public void setRut(String rut) {
	this.rut = rut;
}
public String getCuenta() {
	return cuenta;
}
public void setCuenta(String cuenta) {
	this.cuenta = cuenta;
}

public String getNombreCompleto() {
	return nombreCompleto;
}
public void setNombreCompleto(String nombreCompleto) {
	this.nombreCompleto = nombreCompleto;
}
public String getTipoDireccion() {
	return tipoDireccion;
}
public void setTipoDireccion(String tipoDireccion) {
	this.tipoDireccion = tipoDireccion;
}

public String getCalle() {
	return calle;
}
public void setCalle(String calle) {
	this.calle = calle;
}
public String getDetalleDir() {
	return detalleDir;
}
public void setDetalleDir(String detalleDir) {
	this.detalleDir = detalleDir;
}
public String getNumero() {
	return numero;
}
public void setNumero(String numero) {
	this.numero = numero;
}
public String getComuna() {
	return comuna;
}
public void setComuna(String comuna) {
	this.comuna = comuna;
}
public String getRegion() {
	return region;
}
public void setRegion(String region) {
	this.region = region;
}
public String getCodPostal() {
	return codPostal;
}
public void setCodPostal(String codPostal) {
	this.codPostal = codPostal;
}
public String getPrimerNombre() {
	return primerNombre;
}
public void setPrimerNombre(String primerNombre) {
	this.primerNombre = primerNombre;
}
public String getSegundoNombre() {
	return segundoNombre;
}
public void setSegundoNombre(String segundoNombre) {
	this.segundoNombre = segundoNombre;
}
public String getPrimerApellido() {
	return primerApellido;
}
public void setPrimerApellido(String primerApellido) {
	this.primerApellido = primerApellido;
}
public String getSegundoApellido() {
	return segundoApellido;
}
public void setSegundoApellido(String segundoApellido) {
	this.segundoApellido = segundoApellido;
}
public String getIndEECCMail() {
	return indEECCMail;
}
public void setIndEECCMail(String indEECCMail) {
	this.indEECCMail = indEECCMail;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCodDireccion() {
	return codDireccion;
}
public void setCodDireccion(String codDireccion) {
	this.codDireccion = codDireccion;
}
public String getMotivoRechazo() {
	return motivoRechazo;
}
public void setMotivoRechazo(String motivoRechazo) {
	this.motivoRechazo = motivoRechazo;
}
public String getDescMotivoRechazo() {
	return descMotivoRechazo;
}
public void setDescMotivoRechazo(String descMotivoRechazo) {
	this.descMotivoRechazo = descMotivoRechazo;
}


}//End of CustDetailsData
