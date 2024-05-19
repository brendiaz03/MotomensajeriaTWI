package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Query<Viaje> query = session.createQuery("FROM Viaje", Viaje.class);
        List<Viaje> viajes = query.list();
        return viajes;
    }

    @Override
    @Transactional
    public Boolean actualizarViajeAceptadoPorElConductor(Integer idViaje, Integer idConductor) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("UPDATE Viaje SET Conductor.id = :idConductor WHERE Viaje.id = :idViaje");
        query.setParameter("idConductor", idConductor);
        query.setParameter("idViaje", idViaje);
        query.executeUpdate();
        return true;
    }
}