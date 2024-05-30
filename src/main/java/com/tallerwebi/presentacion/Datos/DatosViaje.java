package com.tallerwebi.presentacion.Datos;

public class DatosViaje {

    private Integer idViaje;
    private String domicilioDeSalida;
    private String domicilioDeLlegada;
    private String nombreDelCliente;
    private String precio;
    private String codigoPostal;
    private Double latitudDeSalida;
    private Double longitudDeSalida;
    private Double latitudDeLlegada;
    private Double longitudDeLlegada;
    private Double distanciaDelViaje;
    private Boolean terminado;
    private Boolean cancelado;
    private Boolean aceptado;
    private Boolean descartado;

    public DatosViaje(Integer idViaje, String domicilioDeSalida, String domicilioDeLlegada, String nombreDelCliente, String precio, String codigoPostal, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, Double distanciaDelViaje, Boolean terminado, Boolean cancelado, Boolean aceptado, Boolean descartado) {
        this.idViaje = idViaje;
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.nombreDelCliente = nombreDelCliente;
        this.precio = precio;
        this.codigoPostal = codigoPostal;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.distanciaDelViaje = distanciaDelViaje;
        this.terminado = terminado;
        this.cancelado = cancelado;
        this.aceptado = aceptado;
        this.descartado = descartado;
    }

    public DatosViaje(Integer idViaje, String domicilioDeSalida, String domicilioDeLlegada, String nombreDelCliente, String precio, String codigoPostal, Boolean terminado, Boolean cancelado) {
        this.idViaje = idViaje;
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.nombreDelCliente = nombreDelCliente;
        this.precio = precio;
        this.codigoPostal = codigoPostal;
        this.terminado = terminado;
        this.cancelado = cancelado;
    }

    public DatosViaje() {

    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public String getDomicilioDeSalida() {
        return domicilioDeSalida;
    }

    public void setDomicilioDeSalida(String domicilioDeSalida) {
        this.domicilioDeSalida = domicilioDeSalida;
    }

    public String getDomicilioDeLlegada() {
        return domicilioDeLlegada;
    }

    public void setDomicilioDeLlegada(String domicilioDeLlegada) {
        this.domicilioDeLlegada = domicilioDeLlegada;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Double getLatitudDeSalida() {
        return latitudDeSalida;
    }

    public void setLatitudDeSalida(Double latitudDeSalida) {
        this.latitudDeSalida = latitudDeSalida;
    }

    public Double getLongitudDeSalida() {
        return longitudDeSalida;
    }

    public void setLongitudDeSalida(Double longitudDeSalida) {
        this.longitudDeSalida = longitudDeSalida;
    }

    public Double getLatitudDeLlegada() {
        return latitudDeLlegada;
    }

    public void setLatitudDeLlegada(Double latitudDeLlegada) {
        this.latitudDeLlegada = latitudDeLlegada;
    }

    public Double getLongitudDeLlegada() {
        return longitudDeLlegada;
    }

    public void setLongitudDeLlegada(Double longitudDeLlegada) {
        this.longitudDeLlegada = longitudDeLlegada;
    }

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public Double getDistanciaDelViaje() {
        return distanciaDelViaje;
    }

    public void setDistanciaDelViaje(Double distanciaDelViaje) {
        this.distanciaDelViaje = distanciaDelViaje;
    }

    public Boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }

    public Boolean getDescartado() {
        return descartado;
    }

    public void setDescartado(Boolean descartado) {
        this.descartado = descartado;
    }
}