package com.tallerwebi.dominio.entidades.vehiculos;

import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idVehiculo;
    private String marca;
    private String modelo;
    private String patente;
    private String color;
    private Double pesoSoportado;
    private Double dimensionDisponible;
    private TipoVehiculo tipoVehiculo;

    public Vehiculo(String marca, String modelo, String patente, String color, Double pesoSoportado, Double dimensionDisponible, TipoVehiculo tipoVehiculo) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.color = color;
        this.pesoSoportado = pesoSoportado;
        this.dimensionDisponible = dimensionDisponible;
        this.tipoVehiculo = tipoVehiculo;
    }

    public Vehiculo() {

    }

    public void setIdVehiculo(Long id) {
        this.idVehiculo = id;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

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