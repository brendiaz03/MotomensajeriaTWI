package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteNoEncontradoException;
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
    public Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException {

        if(paquete==null){

            throw new PaqueteNoEncontradoException();

        }

        this.sessionFactory.getCurrentSession().save(paquete);

        return paquete;
    }

    @Override
    public void editarPaquete(Paquete paquete) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(paquete);
    }

    @Override
    public Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException {

        Paquete paqueteBuscado = this.sessionFactory.getCurrentSession().get(Paquete.class, paqueteId);

        if (paqueteBuscado == null) {
            throw new PaqueteNoEncontradoException();
        }

        return paqueteBuscado;

    }

}
