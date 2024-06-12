package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class VehiculoRepositorioImpl implements VehiculoRepositorio {
    private SessionFactory sessionFactory;
    public VehiculoRepositorioImpl(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }

    @Override
    @Transactional
    public Vehiculo buscarVehiculoPorPatente(String patente) {
        return (Vehiculo) sessionFactory.getCurrentSession().createCriteria(Vehiculo.class)
                .add(Restrictions.eq("patente", patente)).uniqueResult();
    }

    @Override
    @Transactional
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(vehiculo);
        Integer idVehiculoGuardado = (Integer) session.getIdentifier(vehiculo);
        return session.get(Vehiculo.class, idVehiculoGuardado);
    }

    @Override
    @Transactional
    public void editar(Vehiculo vehiculo) {
        this.sessionFactory.getCurrentSession().update(vehiculo);
    }
}