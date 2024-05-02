package com.tallerwebi.dominio;

import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Paquete {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idPaquete;
    private String domicilioEntrega;
    private String domicilioSalida;
    private String estadoDelPaquete;
    private Integer idCliente;
    private Double valorDelEnvio;
    private Boolean esFragil;
    private Integer codigoPostal;

    public Paquete(Integer idPaquete, String domicilioEntrega, String domicilioSalida, String estadoDelPaquete, Integer idCliente, Double valorDelEnvio, Boolean esFragil, Integer codigoPostal) {
        this.idPaquete = idPaquete;
        this.domicilioEntrega = domicilioEntrega;
        this.domicilioSalida = domicilioSalida;
        this.estadoDelPaquete = estadoDelPaquete;
        this.idCliente = idCliente;
        this.valorDelEnvio = valorDelEnvio;
        this.esFragil = esFragil;
        this.codigoPostal = codigoPostal;
    }

    public Paquete(String domicilioSalida, String domicilioEntrega, Integer codigoPostal, Boolean esFragil){
        this.domicilioSalida = domicilioSalida;
        this.domicilioEntrega = domicilioEntrega;
        this.codigoPostal = codigoPostal;
        this.esFragil = esFragil;
    }

    public Paquete(String domicilioSalida, String domicilioEntrega, Integer codigoPostal){
        this.domicilioSalida = domicilioSalida;
        this.domicilioEntrega = domicilioEntrega;
        this.codigoPostal = codigoPostal;
    }

    public Paquete(){

    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public String getEstadoDelPaquete() {
        return estadoDelPaquete;
    }

    public void setEstadoDelPaquete(String estadoDelPaquete) {
        this.estadoDelPaquete = estadoDelPaquete;
    }

    public String getDomicilioSalida() {
        return domicilioSalida;
    }

    public void setDomicilioSalida(String domicilioSalida) {
        this.domicilioSalida = domicilioSalida;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getValorDelEnvio() {
        return valorDelEnvio;
    }

    public void setValorDelEnvio(Double valorDelEnvio) {
        this.valorDelEnvio = valorDelEnvio;
    }

    public Boolean getEsFragil() {
        return esFragil;
    }

    public void setEsFragil(Boolean esFragil) {
        this.esFragil = esFragil;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
