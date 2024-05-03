package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.IRepositorioConductor;
import com.tallerwebi.dominio.conductor.Conductor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioConductorImpl implements IRepositorioConductor {
    //implementa su propia interfaz y conecta directamente con la base de datos
    //son metodos que hacen cosas sencillas sin tantas validaciones como busqueda, agregar y editar.
    //se le inyecta el sessionfactory que es la parte en donde tenemos configurada nuestra base de datos y dialecto para que la base de datos entienda.

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioConductorImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Conductor buscarConductorPorNroDni(Integer nroDni) {
        final Session session = sessionFactory.getCurrentSession();
        return (Conductor) session.createCriteria(Conductor.class)
                .add(Restrictions.eq("numeroDeDni", nroDni))
                .uniqueResult();
    }

    @Override
    public void ingresarConductor(Conductor conductor) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(conductor);
    }

}
