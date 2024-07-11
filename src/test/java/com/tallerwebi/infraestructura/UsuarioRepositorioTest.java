package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioRepositorio;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateInfraestructuraTestConfig.class)
public class UsuarioRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void init() {
        this.usuarioRepositorio = new UsuarioRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnUsuarioEnLaBaseDeDatos() {

        Cliente nuevoCliente = new Cliente();

        nuevoCliente.setEmail("testcliente@example.com");

        nuevoCliente.setNombreUsuario("testcliente");

        nuevoCliente.setTipoUsuario(TipoUsuario.cliente);

        Usuario usuarioGuardado = usuarioRepositorio.guardarUsuario(nuevoCliente);

        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("testcliente@example.com", usuarioGuardado.getEmail());
        assertEquals("testcliente", usuarioGuardado.getNombreUsuario());

    }

    @Test
    @Transactional
    @Rollback
    public void queSeBusqueUnUsuarioQueYaSeGuardoYSeEncuentraDuplicadoEnLaBaseDeDatos() {

        Cliente clienteExistente = new Cliente();

        clienteExistente.setEmail("duplicado@example.com");

        clienteExistente.setNombreUsuario("duplicado");

        clienteExistente.setTipoUsuario(TipoUsuario.cliente);

        usuarioRepositorio.guardarUsuario(clienteExistente);

        Usuario duplicado = usuarioRepositorio.buscarDuplicados("duplicado@example.com", "duplicado");

        assertNotNull(duplicado);

        assertThat("duplicado@example.com", equalTo(duplicado.getEmail()));

        assertThat("duplicado", equalTo(duplicado.getNombreUsuario()));

    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEditarUnUsuarioQueYaSeEncuentraGuardadoEnLaBaseDeDatos() {

        Cliente clienteExistente = new Cliente();

        clienteExistente.setEmail("original@example.com");

        clienteExistente.setNombreUsuario("original");

        clienteExistente.setTipoUsuario(TipoUsuario.cliente);

        Usuario usuarioGuardado = usuarioRepositorio.guardarUsuario(clienteExistente);

        usuarioGuardado.setNombreUsuario("modificado");

        usuarioRepositorio.editarUsuario(usuarioGuardado);

        Usuario usuarioModificado = usuarioRepositorio.getUsuarioById(usuarioGuardado.getId());

        assertNotNull(usuarioModificado);
        assertEquals("modificado", usuarioModificado.getNombreUsuario());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObtengaUnUsuarioGuardadoEnLaBaseDeDatosPorSuId() {

        Cliente nuevoCliente = new Cliente();

        nuevoCliente.setEmail("test@example.com");

        nuevoCliente.setNombreUsuario("testuser");

        nuevoCliente.setTipoUsuario(TipoUsuario.cliente);

        Usuario usuarioGuardado = usuarioRepositorio.guardarUsuario(nuevoCliente);

        Usuario usuarioObtenido = usuarioRepositorio.getUsuarioById(usuarioGuardado.getId());

        assertNotNull(usuarioObtenido);
        assertEquals(usuarioGuardado.getId(), usuarioObtenido.getId());
        assertEquals("test@example.com", usuarioObtenido.getEmail());
        assertEquals("testuser", usuarioObtenido.getNombreUsuario());

    }

}
