package com.tallerwebi.dominio.dto;

public class DatosPaquete {

    private Integer idPaquete;
    private String domicilioEntrega;
    private String domicilioSalida;
    private String estadoDelPaquete;
    private Integer idCliente;
    private Double valorDelEnvio;
    private Boolean esFragil;
    private Integer codigoPostal;

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

    public String getDomicilioSalida() {
        return domicilioSalida;
    }

    public void setDomicilioSalida(String domicilioSalida) {
        this.domicilioSalida = domicilioSalida;
    }

    public String getEstadoDelPaquete() {
        return estadoDelPaquete;
    }

    public void setEstadoDelPaquete(String estadoDelPaquete) {
        this.estadoDelPaquete = estadoDelPaquete;
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
