package com.tallerwebi.presentacion.Datos;

import com.tallerwebi.dominio.enums.TipoEstado;
import com.tallerwebi.dominio.viaje.Viaje;

public class DatosViaje {

    private Integer idViaje;
    private String domicilioDeSalida;
    private String domicilioDeLlegada;
    private String nombreDelCliente;
    private Double precio;
    private Double latitudDeSalida;
    private Double longitudDeSalida;
    private Double latitudDeLlegada;
    private Double longitudDeLlegada;
    private Double distanciaDelViaje;
    private TipoEstado estado;

    public DatosViaje(Integer idViaje, String domicilioDeSalida, String domicilioDeLlegada, String nombreDelCliente, Double precio, Double latitudDeSalida, Double longitudDeSalida, Double latitudDeLlegada, Double longitudDeLlegada, Double distanciaDelViaje, TipoEstado estado) {
        this.idViaje = idViaje;
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.nombreDelCliente = nombreDelCliente;
        this.precio = precio;
        this.latitudDeSalida = latitudDeSalida;
        this.longitudDeSalida = longitudDeSalida;
        this.latitudDeLlegada = latitudDeLlegada;
        this.longitudDeLlegada = longitudDeLlegada;
        this.distanciaDelViaje = distanciaDelViaje;
        this.estado = estado;
    }

    public DatosViaje(Integer idViaje, String domicilioDeSalida, String domicilioDeLlegada, String nombreDelCliente, Double precio, TipoEstado estado) {
        this.idViaje = idViaje;
        this.domicilioDeSalida = domicilioDeSalida;
        this.domicilioDeLlegada = domicilioDeLlegada;
        this.nombreDelCliente = nombreDelCliente;
        this.precio = precio;
        this.estado = estado;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public Viaje toViaje(DatosViaje viajeActual) {
        Viaje viaje = new Viaje();
        viaje.setDomicilioDeSalida(viajeActual.getDomicilioDeSalida());
        viaje.setDomicilioDeLlegada(viajeActual.getDomicilioDeLlegada());

        return viaje;
    }
}