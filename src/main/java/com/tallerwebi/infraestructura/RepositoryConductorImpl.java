package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IRepositoryConductor;
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
//        System.out.println(nuevoConductor.getNombre());
//        System.out.println(nuevoConductor.getApellido());
//        System.out.println(nuevoConductor.getNumeroDeDni());
//        System.out.println(nuevoConductor.getEmail());
//        System.out.println(nuevoConductor.getPassword());
//        System.out.println(nuevoConductor.getNombreUsuario());
//        System.out.println(nuevoConductor.getNroTelefono());
//        System.out.println(nuevoConductor.getDomicilio());
//        System.out.println(nuevoConductor.getCvu());

//        try{
         if(this.buscarConductor(nuevoConductor.getId())==null){
             this.sessionFactory.getCurrentSession().save(nuevoConductor);
             return true;
        }else{
            return false;
         }
//        }catch(HibernateException e){
//            throw new HibernateException("No se guardo el conductor");
//        }

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
