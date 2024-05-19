package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ViajeRepositoryImpl implements ViajeRepository {

    private SessionFactory sessionFactory;

    public ViajeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
    }

    @Override
    @Transactional
    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Viaje.class);
        criteria.add(Restrictions.isNull("idConductor"));

        List<Viaje> viajes = criteria.list();
        return viajes;
    }

    @Override
    @Transactional
    public Viaje actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor) {
        Session session = this.sessionFactory.getCurrentSession();
        Viaje viaje = session.get(Viaje.class, idViaje);

        if (viaje != null) {
            viaje.setIdConductor(idConductor);
            session.saveOrUpdate(viaje);
            return viaje;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Viaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Viaje> criteriaQuery = criteriaBuilder.createQuery(Viaje.class);
        Root<Viaje> viajeRoot = criteriaQuery.from(Viaje.class);

        criteriaQuery.select(viajeRoot);
        criteriaQuery.where(criteriaBuilder.equal(viajeRoot.get("idConductor"), idConductor));

        return session.createQuery(criteriaQuery).getResultList();
    }
}