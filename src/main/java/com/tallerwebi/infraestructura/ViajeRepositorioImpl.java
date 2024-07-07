package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoEstado;
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
                "AND viaje.conductor IS NULL AND viaje.estado = :estado " +
                "AND viaje.afectaPenalizacion IS NULL " +
                "ORDER BY (6371 * acos(cos(radians(:latitudConductor)) * cos(radians(viaje.latitudDeSalida)) * " +
                "cos(radians(viaje.longitudDeSalida) - radians(:longitudConductor)) + " +
                "sin(radians(:latitudConductor)) * sin(radians(viaje.latitudDeSalida)))) ASC";

        Query<Viaje> query = session.createQuery(hql, Viaje.class)
                .setParameter("latitudConductor", latitudConductor)
                .setParameter("longitudConductor", longitudConductor)
                .setParameter("distanciaAFiltar", distanciaAFiltar)
                .setParameter("estado", TipoEstado.PENDIENTE);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Viaje> traerTodosLosViajesPendientes() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Viaje.class);
        criteria.add(Restrictions.eq("estado", TipoEstado.PENDIENTE));
        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public List<Viaje> traerTodosLosViajesDescartadosPorConductor(Conductor conductor) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Viaje.class);
        criteria.add(Restrictions.eq("estado", TipoEstado.DESCARTADO));
        criteria.add(Restrictions.eq("conductor", conductor));
        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public List<Viaje> traerTodosLosViajesDescartadosQueAfectanPenalizacionPorConductor(Conductor conductor) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Viaje.class);
        criteria.add(Restrictions.eq("estado", TipoEstado.DESCARTADO));
        criteria.add(Restrictions.eq("conductor", conductor));
        criteria.add(Restrictions.eq("afectaPenalizacion", true));
        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public List<Viaje> traerTodosLosViajesCanceladosPorConductor(Conductor conductor) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Viaje.class);
        criteria.add(Restrictions.eq("estado", TipoEstado.CANCELADO));
        criteria.add(Restrictions.eq("conductor", conductor));
        criteria.add(Restrictions.eq("afectaPenalizacion", true));
        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public Viaje guardarViaje(Viaje viaje) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(viaje);
        return this.obtenerViajePorId(viaje.getId());
    }

    @Override
    @Transactional
    public List<Viaje> obtenerViajesPorCliente(Integer idUsuario) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Viaje.class, "viaje");
        criteria.createAlias("viaje.cliente", "cliente");
        criteria.add(Restrictions.eq("cliente.id", idUsuario));
        return (List<Viaje>) criteria.list();
    }

    @Override
    @Transactional
    public void guardarViajeDuplicado(Viaje viajeObtenido) {
        this.sessionFactory.getCurrentSession().save(viajeObtenido);
    }
}