package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.enums.ModeloVehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;

import javax.persistence.*;

@Entity
@Table(name = "Vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Patente")
    private String patente;

    @Column(name = "Color")
    private Color color;

    @Column(name = "modelo")
    private ModeloVehiculo modeloVehiculo;

    @Column(name = "tipoDeVehiculo")
    private TipoVehiculo tipoDeVehiculo;

    @Column(name = "pesoSoportado")
    private double pesoSoportado;

    @Column(name = "dimensionDisponible")
    private double dimensionDisponible;

    @OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Conductor Conductor;

    public Vehiculo(String patente, Color color, ModeloVehiculo modeloVehiculo, TipoVehiculo tipoDeVehiculo, double pesoSoportado, double dimensionDisponible) {
        this.patente = patente;
        this.color = color;
        this.modeloVehiculo = modeloVehiculo;
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.pesoSoportado = pesoSoportado;
        this.dimensionDisponible = dimensionDisponible;
    }

    public Vehiculo() {

    }

    public Vehiculo(Long id, String patente) {
        this.id = id;
        this.patente = patente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ModeloVehiculo getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public TipoVehiculo getTipoDeVehiculo() {
        return tipoDeVehiculo;
    }

    public void setTipoDeVehiculo(TipoVehiculo tipoDeVehiculo) {
        this.tipoDeVehiculo = tipoDeVehiculo;
    }

    public double getPesoSoportado() {
        return pesoSoportado;
    }

    public void setPesoSoportado(double pesoSoportado) {
        this.pesoSoportado = pesoSoportado;
    }

    public double getDimensionDisponible() {
        return dimensionDisponible;
    }

    public void setDimensionDisponible(double dimensionDisponible) {
        this.dimensionDisponible = dimensionDisponible;
    }

    public double getDimensionCuadrada() {
        return Math.pow(this.dimensionDisponible, 2);
    }

    public com.tallerwebi.dominio.conductor.Conductor getConductor() {
        return Conductor;
    }

    public void setConductor(com.tallerwebi.dominio.conductor.Conductor conductor) {
        Conductor = conductor;
    }
}

