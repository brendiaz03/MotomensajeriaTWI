package com.tallerwebi.dominio.imagen;

import javax.persistence.*;

@Entity
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    //@Column(columnDefinition = "MEDIUMTEXT")

    @Lob
    private String imagenBase64;

    public Imagen(String nombre, String imagenBase64) {
        this.nombre = nombre;
        this.imagenBase64 = imagenBase64;
    }

    public Imagen() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreImagen) {
        this.nombre = nombreImagen;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }
}
