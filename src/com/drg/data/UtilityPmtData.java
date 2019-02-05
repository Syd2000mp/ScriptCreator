package com.drg.data;

public class UtilityPmtData {

	/*
	Se asume que el orden de las columnas en el archivo es: 
	0: rut
	1: numcertificado
	2: codProducto
	3: rubro
	4: producto
	5: fechaEmision
	6: fechaDesafiliacion
	7: numtarjeta
	8: nombreAsegurado
	9: rutCia
	*/

String rut = null;
String numCertificado = null;
String codProducto = null;
String rubro = null;
String producto = null;
String fechaEmision = null;
String fechaDesafiliacion = null;
String numtarjeta = null;
String nombreAsegurado = null;
String rutCia = null;


public String getRut() {
	return rut;
}
public void setRut(String rut) {
	this.rut = rut;
}
public String getNumCertificado() {
	return numCertificado;
}
public void setNumCertificado(String numCertificado) {
	this.numCertificado = numCertificado;
}
public String getCodProducto() {
	return codProducto;
}
public void setCodProducto(String codProducto) {
	this.codProducto = codProducto;
}
public String getRubro() {
	return rubro;
}
public void setRubro(String rubro) {
	this.rubro = rubro;
}
public String getProducto() {
	return producto;
}
public void setProducto(String producto) {
	this.producto = producto;
}
public String getFechaEmision() {
	return fechaEmision;
}
public void setFechaEmision(String fechaEmision) {
	this.fechaEmision = fechaEmision;
}
public String getFechaDesafiliacion() {
	return fechaDesafiliacion;
}
public void setFechaDesafiliacion(String fechaDesafiliacion) {
	this.fechaDesafiliacion = fechaDesafiliacion;
}
public String getNumtarjeta() {
	return numtarjeta;
}
public void setNumtarjeta(String numtarjeta) {
	this.numtarjeta = numtarjeta;
}
public String getNombreAsegurado() {
	return nombreAsegurado;
}
public void setNombreAsegurado(String nombreAsegurado) {
	this.nombreAsegurado = nombreAsegurado;
}
public String getRutCia() {
	return rutCia;
}
public void setRutCia(String rutCia) {
	this.rutCia = rutCia;
}


}//Fin de PatDesafiliationData
