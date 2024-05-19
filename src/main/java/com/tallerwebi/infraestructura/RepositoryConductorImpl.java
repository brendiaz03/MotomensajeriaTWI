package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;

import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class RepositoryConductorImpl implements IRepositoryConductor {

    private SessionFactory sessionFactory;
    public RepositoryConductorImpl(SessionFactory sessionFactory) {

        this.sessionFactory=sessionFactory;
    }
    @Override
    @Transactional
    public void registrar(Conductor nuevoConductor) {
        this.sessionFactory.getCurrentSession().save(nuevoConductor);
    }

    @Override
    @Transactional
    public Conductor buscarConductor(Integer id) {
        String hql= "FROM Conductor WHERE id =: id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Conductor) query.getSingleResult();
    }

    @Override
    @Transactional
    public Boolean editarConductor(Conductor conductorEditado) {
        sessionFactory.getCurrentSession().update(conductorEditado);
        return true;
    }

    @Override
    @Transactional
    public Conductor buscarDuplicados(String email, String nombreUsuario)  {
        String hql= "FROM Conductor WHERE email=: email OR nombreUsuario=: nombreUsuario";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("nombreUsuario", nombreUsuario);

        return (Conductor) query.getSingleResult();
    }

    @Override
    @Transactional
    public void borrarConductor(Conductor conductorABorrar) {
           this.sessionFactory.getCurrentSession().delete(conductorABorrar);
    }


}
