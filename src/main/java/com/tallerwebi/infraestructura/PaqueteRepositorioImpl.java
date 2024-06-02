package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteRepositorio;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PaqueteRepositorioImpl implements PaqueteRepositorio {

    private SessionFactory sessionFactory;

    public PaqueteRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarPaquete(Paquete paquete) {
        this.sessionFactory.getCurrentSession().save(paquete);
    }
}
