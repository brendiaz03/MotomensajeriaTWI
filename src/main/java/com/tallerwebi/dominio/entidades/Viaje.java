package com.tallerwebi.dominio.entidades;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Viaje {

   private String lugarDeSalida="";
   private String lugarDeLlegada="";
   private Integer idConductor=0;
   private Integer metrosDeToleranciaPorCercania=800;

    @Id
    private Integer id;

    public Viaje (String lugarDeSalida, String lugarDeLlegada, Integer idConductor){
        this.lugarDeSalida=lugarDeSalida;
        this.lugarDeLlegada=lugarDeLlegada;
        this.idConductor=idConductor;
    }
    public Viaje() {

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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
