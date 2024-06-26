package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
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
    public Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException {
        if(paquete==null){
            throw new PaqueteNoEncontradoException(); //NOSE SI VA PERO LO DEJO POR LAS DUDAS
        }
        this.sessionFactory.getCurrentSession().save(paquete);
        return paquete;
    }

    @Override
    @Transactional
    public Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException {

        Paquete paqueteBuscado = this.sessionFactory.getCurrentSession().get(Paquete.class, paqueteId);

        if (paqueteBuscado == null) {
            throw new PaqueteNoEncontradoException();
        }

        return paqueteBuscado;
    }
}
