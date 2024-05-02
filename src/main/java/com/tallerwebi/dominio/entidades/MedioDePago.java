package com.tallerwebi.dominio.entidades;

import javax.persistence.Id;
import java.util.Date;

public class MedioDePago {

    private Integer id;
    private Integer idMedioDePago;
    private Boolean esPagoElectronico;
    private String nombreDelTitularDeLaTarjeta;
    private Integer numeroDeTarjeta;
    private Date fechaDeVencimiento;
    private Integer codigoDeSeguridad;
    private Integer idCliente;

    public Integer getIdMedioDePago() {
        return id;
    }

    public void setIdMedioDePago(Integer idMedioDePago) {
        this.id = idMedioDePago;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getCodigoDeSeguridad() {
        return codigoDeSeguridad;
    }

    public void setCodigoDeSeguridad(Integer codigoDeSeguridad) {
        this.codigoDeSeguridad = codigoDeSeguridad;
    }

    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(Date fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Integer getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }

    public void setNumeroDeTarjeta(Integer numeroDeTarjeta) {
        this.numeroDeTarjeta = numeroDeTarjeta;
    }

    public String getNombreDelTitularDeLaTarjeta() {
        return nombreDelTitularDeLaTarjeta;
    }

    public void setNombreDelTitularDeLaTarjeta(String nombreDelTitularDeLaTarjeta) {
        this.nombreDelTitularDeLaTarjeta = nombreDelTitularDeLaTarjeta;
    }

    public Boolean getEsPagoElectronico() {
        return esPagoElectronico;
    }

    public void setEsPagoElectronico(Boolean esPagoElectronico) {
        this.esPagoElectronico = esPagoElectronico;
    }
}
