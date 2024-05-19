package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Datos.DatosViaje;
import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ViajeRepositoryImpl implements ViajeRepository {

    private SessionFactory sessionFactory;

    @Autowired
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
    public List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
        List<Viaje> todosLosViaje = this.sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
        List<Cliente> clientes = this.sessionFactory.getCurrentSession().createQuery("FROM Cliente", Cliente.class).list();
        List<DatosViaje> datosViajesSolicitado = new ArrayList<>();

        for (Viaje viaje : todosLosViaje) {
            if(viaje.getIdConductor() == idConductor){
                for (Cliente cliente : clientes) {
                        DatosViaje datosCliente = new DatosViaje(cliente.getNombre(), viaje.getDomicilioDeSalida(), viaje.getDomicilioDeLlegada());
                        datosViajesSolicitado.add(datosCliente);

                }
            }
        }
        return datosViajesSolicitado;
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