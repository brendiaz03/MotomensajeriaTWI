package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Viaje {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lugarDeSalida;
    private String lugarDeLlegada;
    private Integer idConductor;
    private Integer idPaquete;
    private Integer idCliente;

    public Viaje(Integer idCliente, Integer idConductor, Integer idPaquete) {
        this.idCliente = idCliente;
        this.idConductor = idConductor;
        this.idPaquete = idPaquete;
    }

    public Viaje() {

    }

    public Viaje(String direccionDeSalida, String direccionDeLlegada) {
        this.lugarDeSalida = direccionDeSalida;
        this.lugarDeLlegada = direccionDeLlegada;
    }

    public Viaje(Integer idConductor, Integer idPaquete, Integer idCliente, String lugarDeSalida, String lugarDeLlegada) {
        this.idConductor = idConductor;
        this.idPaquete = idPaquete;
        this.idCliente = idCliente;
        this.lugarDeSalida = lugarDeSalida;
        this.lugarDeLlegada = lugarDeLlegada;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getLugarDeSalida() {
        return lugarDeSalida;
    }

    public void setLugarDeSalida(String lugarDeSalida) {
        this.lugarDeSalida = lugarDeSalida;
    }

    public String getLugarDeLlegada() {
        return lugarDeLlegada;
    }

    public void setLugarDeLlegada(String lugarDeLlegada) {
        this.lugarDeLlegada = lugarDeLlegada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}