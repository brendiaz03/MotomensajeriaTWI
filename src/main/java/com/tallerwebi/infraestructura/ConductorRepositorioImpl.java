package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;

import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class ConductorRepositorioImpl implements ConductorRepositorio {

    private SessionFactory sessionFactory;
    public ConductorRepositorioImpl(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }

    @Override
    @Transactional
    public Conductor buscarConductorPorId(Integer id) {
        String hql = "FROM Conductor WHERE id =:id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Conductor) query.getSingleResult();
    }

    @Override
    @Transactional
    public void editarConductor(Conductor conductor) {
            sessionFactory.getCurrentSession().saveOrUpdate(conductor);
    }
}
