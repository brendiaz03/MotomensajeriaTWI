package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;

import com.tallerwebi.dominio.conductor.ConductorRepositorio;
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
    public Conductor registrar(Conductor nuevoConductor) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(nuevoConductor);
        Integer idConductorGuardado = (Integer) session.getIdentifier(nuevoConductor);
        return session.get(Conductor.class, idConductorGuardado);
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

    @Transactional
    public void agregarVehiculoAConductor(Integer conductorId, Vehiculo vehiculo) {
        Conductor conductor = sessionFactory.getCurrentSession().get(Conductor.class, conductorId);
        if (conductor != null) {
            conductor.setVehiculo(vehiculo);
            sessionFactory.getCurrentSession().saveOrUpdate(conductor);
        } else {
            throw new IllegalArgumentException("Conductor no encontrado con el ID: " + conductorId);
        }
    }
}
