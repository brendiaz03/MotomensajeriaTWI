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

    public DatosViaje toDatosViaje(Viaje viaje) {
        DatosViaje datosViaje = new DatosViaje();
        datosViaje.setIdViaje(viaje.getId());
        datosViaje.setDomicilioDeSalida(viaje.getDomicilioDeSalida());
        datosViaje.setDomicilioDeLlegada(viaje.getDomicilioDeLlegada());
        datosViaje.setNombreDelCliente(viaje.getCliente().getNombre());
        datosViaje.setPrecio(viaje.getPrecio());
        datosViaje.setLatitudDeSalida(viaje.getLatitudDeSalida());
        datosViaje.setLongitudDeSalida(viaje.getLongitudDeSalida());
        datosViaje.setLatitudDeLlegada(viaje.getLatitudDeLlegada());
        datosViaje.setLongitudDeLlegada(viaje.getLongitudDeLlegada());
        datosViaje.setDistanciaDelViaje(viaje.getDistanciaDelViaje());
        datosViaje.setEstado(viaje.getEstado());
        return datosViaje;
    }

    public DatosViaje toDatosViajeHistorial(Viaje viaje) {
        DatosViaje datosViaje = new DatosViaje();
        datosViaje.setDomicilioDeSalida(viaje.getDomicilioDeSalida());
        datosViaje.setDomicilioDeLlegada(viaje.getDomicilioDeLlegada());
        datosViaje.setNombreDelCliente(viaje.getCliente().getNombre());
        datosViaje.setPrecio(viaje.getPrecio());
        datosViaje.setEstado(viaje.getEstado());
        return datosViaje;
    }
}