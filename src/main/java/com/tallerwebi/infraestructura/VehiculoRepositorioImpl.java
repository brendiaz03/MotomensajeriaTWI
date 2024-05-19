package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
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
    public void registrarVehiculo(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
    }

    @Override
    @Transactional
    public Vehiculo buscarVehiculoPorPatente(String patente) {
        return (Vehiculo) sessionFactory.getCurrentSession().createCriteria(Vehiculo.class)
                .add(Restrictions.eq("patente", patente)).uniqueResult();
    }
}