package com.tallerwebi.dto;

import com.tallerwebi.resources.TipoVehiculo;

public class VehiculoDto {

    private String marca;
    private String modelo;
    private String patente;
    private String color;
    private Double pesoSoportado;
    private Double dimensionDisponible;
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

    public Double getDimensionDisponible() {
        return dimensionDisponible;
    }

    public void setDimensionDisponible(Double dimensionDisponible) {
        this.dimensionDisponible = dimensionDisponible;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
}