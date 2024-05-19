package com.tallerwebi.dominio.imagen;

import java.util.List;

public interface ImagenServicio {
    List<Imagen> obtenerImagenes();
    Imagen getImagenByName(String nombre);
}
