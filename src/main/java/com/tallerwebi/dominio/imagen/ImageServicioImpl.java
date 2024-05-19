package com.tallerwebi.dominio.imagen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioImagen")
@Transactional
public class ImageServicioImpl implements ImagenServicio {

    private final ImagenRepositorio imagenRepositorio;

    @Autowired
    public ImageServicioImpl(ImagenRepositorio _ImagenRepositorio) {
        this.imagenRepositorio = _ImagenRepositorio;
    }

    @Override
    public List<Imagen> obtenerImagenes() {
        return imagenRepositorio.getAllImagenes();
    }

    @Override
    public Imagen getImagenByName(String nombre) {
        List<Imagen> imagenes = imagenRepositorio.getAllImagenes();
        for (Imagen imagen : imagenes) {
            if (imagen.getNombre().equals(nombre)) {
                return imagen;
            }
        }
        return null;
    }


}