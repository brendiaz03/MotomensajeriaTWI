package com.tallerwebi.dominio.paquete;

import com.tallerwebi.dominio.viaje.Viaje;

import javax.persistence.*;

@Entity
@Table(name = "paquete")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "dimension")
    private Double dimension;

    @Column(name = "esFragil")
    private Boolean esFragil;

    public Paquete(Double peso, Double dimension, Boolean esFragil) {
        this.peso = peso;
        this.dimension = dimension;
        this.esFragil = esFragil;
    }

    public Paquete() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getDimension() {
        return dimension;
    }

    public Double getDimensionAlCubo(){
        return dimension*dimension*dimension;
    }

    public void setDimension(Double dimension) {
        this.dimension = dimension;
    }

    public Boolean getEsFragil() {
        return esFragil;
    }

    public void setEsFragil(Boolean esFragil) {
        this.esFragil = esFragil;
    }

}
