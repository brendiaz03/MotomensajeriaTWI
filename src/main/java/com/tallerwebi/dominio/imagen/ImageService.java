package com.tallerwebi.dominio.imagen;

import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import com.tallerwebi.infraestructura.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioImagen")
@Transactional
public class ImageService implements IImageService {

    private final IImagenRepository iImagenRepository;

    @Autowired
    public ImageService(IImagenRepository _IImagenRepository) {
        this.iImagenRepository = _IImagenRepository;
    }

    @Override
    public List<Imagen> obtenerImagenes() {
        return iImagenRepository.getAllImagenes();
    }

    @Override
    public Imagen getImagenByName(String nombre) {
        List<Imagen> imagenes = iImagenRepository.getAllImagenes();
        for (Imagen imagen : imagenes) {
            if (imagen.getNombre().equals(nombre)) {
                return imagen;
            }
        }
        return null;
    }


}