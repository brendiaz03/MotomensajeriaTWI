package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class PaqueteRepositorioImpl implements PaqueteRepositorio {

    private SessionFactory sessionFactory;

    public PaqueteRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Paquete guardarPaquete(Paquete paquete){

        this.sessionFactory.getCurrentSession().save(paquete);

        return paquete;
    }

    @Override
    @Transactional
    public Paquete obtenerPaquetePorId(Integer paqueteId) {

        return this.sessionFactory.getCurrentSession().get(Paquete.class, paqueteId);

    }
}
