package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.cliente.ClienteRepositorio;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class ClienteRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private ClienteRepositorio clienteRepositorio;

    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void init(){
        this.clienteRepositorio = new ClienteRepositorioImpl(sessionFactory);
        this.usuarioRepositorio = new UsuarioRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void creoUnClienteYLoGuardoEnLaBaseDeDatosParaLuegoPoderObtenerloPorSuId() {
        Cliente cliente =new Cliente();
        cliente.setId(1);
        cliente.setNombre("Marcos");
        usuarioRepositorio.guardarUsuario(cliente);

        Cliente clienteObtenido = clienteRepositorio.obtenerClientePorId(cliente.getId());
        assertNotNull(clienteObtenido);
        assertEquals("Marcos", clienteObtenido.getNombre());
    }

}
