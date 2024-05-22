package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ViajeRepositorioImpl implements ViajeRepositorio {

    private SessionFactory sessionFactory;

    public ViajeRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
    }

    @Override
    @Transactional
    public List<Viaje> obtenerViajesPorConductor(Conductor conductor) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Viaje.class);

        // Agregar la restricción para el conductor específico
        criteria.add(Restrictions.eq("conductor", conductor));

        List<Viaje> viajes = criteria.list();
        return viajes;
    }

    @Override
    @Transactional
    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Viaje.class);

        // Agregar la restricción para que el conductor sea null
        criteria.add(Restrictions.isNull("conductor"));

        List<Viaje> viajes = criteria.list();
        return viajes;
    }

    @Override
    @Transactional
    public void actualizarViaje(Viaje viaje) {
        sessionFactory.getCurrentSession().saveOrUpdate(viaje);
    }

    @Override
    @Transactional
    public Viaje obtenerViajePorId(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Viaje viaje = session.get(Viaje.class, id); // Usa get para obtener el viaje por su id
        return viaje;
    }


//    @Override
//    @Transactional
//    public Viaje actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Viaje viaje = session.get(Viaje.class, idViaje);
//
//        if (viaje != null) {
//            viaje.setId(idConductor);
//            session.saveOrUpdate(viaje);
//            return viaje;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    @Transactional
//    public List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
//        Session session = this.sessionFactory.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Viaje> criteriaQuery = criteriaBuilder.createQuery(Viaje.class);
//        Root<Viaje> viajeRoot = criteriaQuery.from(Viaje.class);
//
//        criteriaQuery.select(viajeRoot);
//        criteriaQuery.where(criteriaBuilder.equal(viajeRoot.get("idConductor"), idConductor));
//
//        return session.createQuery(criteriaQuery).getResultList();
//    }
//
//    @Override
//    @Transactional
//    public Viaje actualizarViajeConElIdDelConductorQueAceptoElViajeYDespuesLoRechaza(Integer idViaje, Integer idConductor) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Viaje viaje = session.get(Viaje.class, idViaje);
//
//        if (viaje != null) {
//            viaje.setId(null);
//            session.saveOrUpdate(viaje);
//            return viaje;
//        } else {
//            return null;
//        }
//    }

}