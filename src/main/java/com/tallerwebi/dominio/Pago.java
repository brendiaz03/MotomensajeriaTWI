package com.tallerwebi.dominio;

public class Pago {

    private Integer idDelPago;
    private Integer idCliente;
    private Integer idConductor;
    private Integer idPaquete;
    private Double valorDelEnvio;

    public Integer getIdDelPago() {
        return idDelPago;
    }

    public void setIdDelPago(Integer idDelPago) {
        this.idDelPago = idDelPago;
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
