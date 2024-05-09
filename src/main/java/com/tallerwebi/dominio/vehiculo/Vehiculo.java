package com.tallerwebi.dominio.vehiculo;

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
    private String color;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "tipoDeVehiculo")
    private TipoVehiculo tipoDeVehiculo;

    @Column(name = "pesoSoportado")
    private double pesoSoportado;

    @Column(name = "dimensionDisponible")
    private double dimensionDisponible;

    @Column(name = "idConductor")
    private Long idConductor;

    @Column(name = "idConductorTest")
    private Integer idConductorTest;

    public Vehiculo(Long id, String patente, String color, String modelo, TipoVehiculo tipoDeVehiculo, double pesoSoportado, double dimensionDisponible, Long idConductor) {
        this.id = id;
        this.patente = patente;
        this.color = color;
        this.modelo = modelo;
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.pesoSoportado = pesoSoportado;
        this.dimensionDisponible = dimensionDisponible;
        this.idConductor = idConductor;
    }

    public Vehiculo(String patente, String color, String modelo, Integer idConductorTest) {
        this.patente = patente;
        this.color = color;
        this.modelo = modelo;
        this.idConductorTest = idConductorTest;
    }

    public Vehiculo() {
    }

    public Vehiculo(long l, TipoVehiculo auto) {

    }

    public Vehiculo(Vehiculo vehiculo) {

    }

    public Vehiculo(String patente, String color, String modelo) {
        this.patente = patente;
        this.color = color;
        this.modelo = modelo;
     //   this.idConductor = idConductor;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdConductorTest() {
        return idConductorTest;
    }

    public void setIdConductorTest(Integer idConductorTest) {
        this.idConductorTest = idConductorTest;
    }
}

