package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ViajeRepositoryImpl implements ViajeRepository {

    private SessionFactory sessionFactory;


    @Autowired
    public ViajeRepositoryImpl (SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Conductor buscarConductor(Integer idConductor) {

        return (Conductor) this.sessionFactory.getCurrentSession().createCriteria(Conductor.class).add(Restrictions.eq("idConductor", "idConductor")).uniqueResult();

    }
}
