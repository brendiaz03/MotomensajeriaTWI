package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
    public Boolean registrar(Conductor nuevoConductor){

         if(this.buscarConductor(nuevoConductor.getId())==null){
             this.sessionFactory.getCurrentSession().save(nuevoConductor);
             return true;
        }else{
            return false;
         }
    }

    @Override
    public Conductor buscarConductor(Integer id) {
        String hql= "FROM Conductor WHERE id =: id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        try {
            return (Conductor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Conductor actualizarConductor(Conductor nuevoConductor) {
       // this.sessionFactory.getCurrentSession().saveOrUpdate(nuevoConductor);
        String hql= "UPDATE Conductor SET domicilio=: domicilio WHERE numeroDeDni=:dni";
        Query query= this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("dni", nuevoConductor.getNumeroDeDni());
        query.setParameter("domicilio", nuevoConductor.getDomicilio());
        query.executeUpdate(); //Sirve tambien para DELETE
        return nuevoConductor;

    }



}
