package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ClienteRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private ClienteRepositorio clienteRepositorio;

    @BeforeEach
    public void init(){
        this.clienteRepositorio = new ClienteRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queElClientePuedaCrearUnViaje(){
        // Preparación
        Viaje viaje = dadoQueExisteUnViajeCreadoPorUnCliente();

        // Ejecución
        this.clienteRepositorio.crearViaje(viaje);
        Viaje viajeObtenido = this.clienteRepositorio.buscarViajePorId(viaje.getId());

        // Validación
        assertThat(viajeObtenido.getId(), equalTo(viaje.getId()));
    }

    private Viaje dadoQueExisteUnViajeCreadoPorUnCliente() {
        Cliente cliente = new Cliente();
        Paquete paquete = new Paquete();
        Viaje viaje = new Viaje();
        viaje.setCliente(cliente);
        viaje.setPaquete(paquete);
        viaje.setId(1);
        this.sessionFactory.getCurrentSession().save(viaje);
        return viaje;
    }
}
