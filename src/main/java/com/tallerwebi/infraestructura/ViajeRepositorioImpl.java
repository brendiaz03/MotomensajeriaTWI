package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ViajeRepositorioImpl implements ViajeRepositorio {

    private final SessionFactory sessionFactory;

    public ViajeRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Viaje> obtenerViajesPorConductor(Conductor conductor) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Viaje.class);

        criteria.add(Restrictions.eq("conductor", conductor));

        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public void editar(Viaje viaje) {
        sessionFactory.getCurrentSession().saveOrUpdate(viaje);
    }

    @Override
    @Transactional
    public Viaje obtenerViajePorId(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Viaje.class, id);
    }

    @Override
    @Transactional
    public List<Viaje> encontrarViajesCercanos(Double latitudConductor, Double longitudConductor, Double distanciaAFiltar) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "FROM Viaje viaje WHERE " +
                "(6371 * acos(cos(radians(:latitudConductor)) * cos(radians(viaje.latitudDeSalida)) * " +
                "cos(radians(viaje.longitudDeSalida) - radians(:longitudConductor)) + " +
                "sin(radians(:latitudConductor)) * sin(radians(viaje.latitudDeSalida)))) < :distanciaAFiltar " +
                "AND viaje.conductor IS NULL AND viaje.descartado = false AND viaje.cancelado = false AND viaje.terminado = false " +
                "AND viaje.aceptado = false " +
                "ORDER BY (6371 * acos(cos(radians(:latitudConductor)) * cos(radians(viaje.latitudDeSalida)) * " +
                "cos(radians(viaje.longitudDeSalida) - radians(:longitudConductor)) + " +
                "sin(radians(:latitudConductor)) * sin(radians(viaje.latitudDeSalida)))) ASC";

        Query<Viaje> query = session.createQuery(hql, Viaje.class)
                .setParameter("latitudConductor", latitudConductor)
                .setParameter("longitudConductor", longitudConductor)
                .setParameter("distanciaAFiltar", distanciaAFiltar);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Viaje> traerTodosLosViajes() {
        return sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
    }
}