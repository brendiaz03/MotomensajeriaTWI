package com.tallerwebi.dominio.imagen;

import java.util.List;

public interface IImageService {
    List<Imagen> obtenerImagenes();
    Imagen getImagenByName(String nombre);
}
