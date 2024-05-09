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
    public void actualizarConductor(Conductor nuevoConductor) {
        // this.sessionFactory.getCurrentSession().saveOrUpdate(nuevoConductor);
        String hql = "UPDATE Conductor SET domicilio=: domicilio WHERE numeroDeDni=:dni";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("dni", nuevoConductor.getNumeroDeDni());
        query.setParameter("domicilio", nuevoConductor.getDomicilio());
        query.executeUpdate(); //Sirve tambien para DELETE
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


}
