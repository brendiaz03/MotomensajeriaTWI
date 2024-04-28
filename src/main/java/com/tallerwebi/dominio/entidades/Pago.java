package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pago {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idCliente;
    private Integer idConductor;
    private Integer idPaquete;
    private Double valorDelEnvio;

    public Integer getIdPago() {
        return id;
    }

    public void setIdPago(Integer idDelPago) {
        this.id = idDelPago;
    }

    public Double getValorDelEnvio() {
        return valorDelEnvio;
    }

    public void setValorDelEnvio(Double valorDelEnvio) {
        this.valorDelEnvio = valorDelEnvio;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}
