package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.imagen.ImagenRepositorio;
import com.tallerwebi.dominio.imagen.Imagen;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImagenRepositorioImpl implements ImagenRepositorio {

    private final SessionFactory sessionFactory;
    public ImagenRepositorioImpl(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }

    @Override
    public List<Imagen> getAllImagenes() {
        return this.sessionFactory.getCurrentSession().createCriteria(Imagen.class).list();
    }

}
