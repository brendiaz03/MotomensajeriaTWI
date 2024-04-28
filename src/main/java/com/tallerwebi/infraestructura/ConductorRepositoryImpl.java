package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ConductorRepository;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class ConductorRepositoryImpl implements ConductorRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ConductorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Conductor buscarConductorPorEmail(String email) {
        return (Conductor) sessionFactory.getCurrentSession().createCriteria(Conductor.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void actualizarInfoDelConductor(Conductor conductorActual) {
        sessionFactory.getCurrentSession().update(conductorActual);
    }

    @Override
    public void guardarVehiculo(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
    }
}
