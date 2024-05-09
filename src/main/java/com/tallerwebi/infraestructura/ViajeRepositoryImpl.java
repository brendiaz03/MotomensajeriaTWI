package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.Datos.DatosViaje;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ViajeRepositoryImpl implements ViajeRepository {

    private SessionFactory sessionFactory;

    public ViajeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarViaje(Viaje viaje) {
        this.sessionFactory.getCurrentSession().save(viaje);
    }





















    @Override
    @Transactional
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
    }

    @Override
    public Viaje ObtenerViajePorIdPaquete(Integer idPaquete) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Viaje viaje WHERE viaje.id = :id");
        query.setParameter("id", idPaquete);
        Viaje viaje = (Viaje) query.uniqueResult();
        return viaje;
    }

    @Override
    public Viaje obtenerViajePorId(Integer id) {
        return (Viaje) this.sessionFactory.getCurrentSession().createCriteria(Viaje.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public List<Viaje> obtenerLosViajesDeUnCliente(Integer idCliente) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Viaje viaje WHERE viaje.idCliente = :id");
        query.setParameter("id", idCliente);
        return (List<Viaje>) query.list();
    }

    @Override
    public List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idConductor) {
        List<Viaje> todosLosViaje = this.sessionFactory.getCurrentSession().createQuery("FROM Viaje", Viaje.class).list();
        List<Cliente> clientes = this.sessionFactory.getCurrentSession().createQuery("FROM Cliente", Cliente.class).list();
        List<DatosViaje> datosViajesSolicitado = new ArrayList<>();

        for (Viaje viaje : todosLosViaje) {
            if(viaje.getIdConductor() == idConductor){
                for (Cliente cliente : clientes) {
                    if(cliente.getId() == viaje.getIdCliente()){
                        DatosViaje datosCliente = new DatosViaje(cliente.getNombre(), viaje.getLugarDeSalida(), viaje.getLugarDeLlegada());
                        datosViajesSolicitado.add(datosCliente);
                    }
                }
            }
        }
        return datosViajesSolicitado;
    }
}
