package com.tallerwebi.dominio;

public class Viaje {

   private String lugarDeSalida="";
   private String lugarDeLlegada="";
   private Integer idConductor=0;
   private Integer metrosDeToleranciaPorCercania=1000;

    public Viaje (String lugarDeSalida, String lugarDeLlegada, Integer idConductor){
        this.lugarDeSalida=lugarDeSalida;
        this.lugarDeLlegada=lugarDeLlegada;
        this.idConductor=idConductor;
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

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }
}
