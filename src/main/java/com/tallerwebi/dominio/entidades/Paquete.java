package com.tallerwebi.dominio.entidades;

public class Paquete {

    private String pesoPaquete;
    private Integer id;
    private Integer idCliente;
    private Double anchoDelPaquete;
    private Double altoDelPaquete;
    private Boolean esFragil;
    private String domicilioDeEntrega;
    private Integer codigoPostal;
    private Double valorDelEnvio;

    public Paquete(String pesoPaquete, Integer id, Integer idCliente, Double anchoDelPaquete, Double altoDelPaquete, Boolean esFragil, String domicilioDeEntrega, Integer codigoPostal) {
        this.pesoPaquete = pesoPaquete;
        this.id = id;
        this.idCliente = idCliente;
        this.anchoDelPaquete = anchoDelPaquete;
        this.altoDelPaquete = altoDelPaquete;
        this.esFragil = esFragil;
        this.domicilioDeEntrega = domicilioDeEntrega;
        this.codigoPostal = codigoPostal;
    }

    public String getPesoPaquete() {
        return pesoPaquete;
    }

    public void setPesoPaquete(String pesoPaquete) {
        this.pesoPaquete = pesoPaquete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getAnchoDelPaquete() {
        return anchoDelPaquete;
    }

    public void setAnchoDelPaquete(Double anchoDelPaquete) {
        this.anchoDelPaquete = anchoDelPaquete;
    }

    public Double getAltoDelPaquete() {
        return altoDelPaquete;
    }

    public void setAltoDelPaquete(Double altoDelPaquete) {
        this.altoDelPaquete = altoDelPaquete;
    }

    public Boolean getEsFragil() {
        return esFragil;
    }

    public void setEsFragil(Boolean esFragil) {
        this.esFragil = esFragil;
    }

    public String getDomicilioDeEntrega() {
        return domicilioDeEntrega;
    }

    public void setDomicilioDeEntrega(String domicilioDeEntrega) {
        this.domicilioDeEntrega = domicilioDeEntrega;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Double getValorDelEnvio() {
        return valorDelEnvio;
    }

    public void setValorDelEnvio(Double valorDelEnvio) {
        this.valorDelEnvio = valorDelEnvio;
    }
}
