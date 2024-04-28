package com.tallerwebi.dominio.dto;

import com.tallerwebi.dominio.enums.TipoVehiculo;

public class VehiculoDto {

    private String marca;
    private String modelo;
    private String patente;
    private String color;
    private Double pesoSoportado;
    private Double dimensionDisponibleAloAlto;
    private Double dimensionDisponibleAloAncho;
    private Double profundidad;
    private TipoVehiculo tipoVehiculo;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPesoSoportado() {
        return pesoSoportado;
    }

    public void setPesoSoportado(Double pesoSoportado) {
        this.pesoSoportado = pesoSoportado;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getDimensionDisponibleAloAlto() {
        return dimensionDisponibleAloAlto;
    }

    public void setDimensionDisponibleAloAlto(Double dimensionDisponibleAloAlto) {
        this.dimensionDisponibleAloAlto = dimensionDisponibleAloAlto;
    }

    public Double getDimensionDisponibleAloAncho() {
        return dimensionDisponibleAloAncho;
    }

    public void setDimensionDisponibleAloAncho(Double dimensionDisponibleAloAncho) {
        this.dimensionDisponibleAloAncho = dimensionDisponibleAloAncho;
    }
}