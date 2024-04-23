package com.tallerwebi.dominio;

public class Cuenta {

    private Integer idCuenta;
    private Integer numeroDeCuenta;
    private Double dineroEnCuenta;
    private Integer cbu;
    private Integer idConductor;

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Double getDineroEnCuenta() {
        return dineroEnCuenta;
    }

    public void setDineroEnCuenta(Double dineroEnCuenta) {
        this.dineroEnCuenta = dineroEnCuenta;
    }

    public Integer getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(Integer numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public Integer getCbu() {
        return cbu;
    }

    public void setCbu(Integer cbu) {
        this.cbu = cbu;
    }
}
