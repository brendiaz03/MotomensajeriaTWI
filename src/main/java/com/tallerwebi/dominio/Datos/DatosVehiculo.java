package com.tallerwebi.dominio.Datos;

public class DatosVehiculo {

    private Integer idConductor;
    private String patente;
    private Long idVehiculo;
    private Integer numeroDeDni;

    public DatosVehiculo(Integer idConductor, String patente, Long idVehiculo, Integer numeroDeDni) {
        this.idConductor = idConductor;
        this.patente = patente;
        this.idVehiculo = idVehiculo;
        this.numeroDeDni = numeroDeDni;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getNumeroDeDni() {
        return numeroDeDni;
    }

    public void setNumeroDeDni(Integer numeroDeDni) {
        this.numeroDeDni = numeroDeDni;
    }
}
