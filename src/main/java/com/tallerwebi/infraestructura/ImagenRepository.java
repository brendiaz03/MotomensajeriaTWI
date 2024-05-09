package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.imagen.IImagenRepository;
import com.tallerwebi.dominio.imagen.Imagen;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImagenRepository implements IImagenRepository {

    private final SessionFactory sessionFactory;
    public ImagenRepository(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }

    @Override
    public List<Imagen> getAllImagenes() {
        return this.sessionFactory.getCurrentSession().createCriteria(Imagen.class).list();
    }

//    @Override
//    public Imagen imagenPorId(String nombre) {
//        return (Imagen) sessionFactory.getCurrentSession().createCriteria(Imagen.class)
//                .add(Restrictions.eq("nombre", nombre)).uniqueResult();
//    }


}
