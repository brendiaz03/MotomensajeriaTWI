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
import javax.persistence.NoResultException;
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
    public void queSeGuardeUnUsuario() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setEmail("testcliente@example.com");
        nuevoCliente.setNombreUsuario("testcliente");
        nuevoCliente.setTipoUsuario(TipoUsuario.Cliente);

        Usuario usuarioGuardado = usuarioRepositorio.guardarUsuario(nuevoCliente);

        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("testcliente@example.com", usuarioGuardado.getEmail());
        assertEquals("testcliente", usuarioGuardado.getNombreUsuario());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeBusqueDuplicados() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setEmail("duplicado@example.com");
        clienteExistente.setNombreUsuario("duplicado");
        clienteExistente.setTipoUsuario(TipoUsuario.Cliente);
        usuarioRepositorio.guardarUsuario(clienteExistente);

        Usuario duplicado = usuarioRepositorio.buscarDuplicados("duplicado@example.com", "duplicado");

        assertNotNull(duplicado);
        assertEquals("duplicado@example.com", duplicado.getEmail());
        assertEquals("duplicado", duplicado.getNombreUsuario());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeEditeUnUsuario() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setEmail("original@example.com");
        clienteExistente.setNombreUsuario("original");
        clienteExistente.setTipoUsuario(TipoUsuario.Cliente);
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
    public void queSeObtengaUnUsuarioPorId() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setEmail("test@example.com");
        nuevoCliente.setNombreUsuario("testuser");
        nuevoCliente.setTipoUsuario(TipoUsuario.Cliente);
        Usuario usuarioGuardado = usuarioRepositorio.guardarUsuario(nuevoCliente);

        Usuario usuarioObtenido = usuarioRepositorio.getUsuarioById(usuarioGuardado.getId());

        assertNotNull(usuarioObtenido);
        assertEquals(usuarioGuardado.getId(), usuarioObtenido.getId());
        assertEquals("test@example.com", usuarioObtenido.getEmail());
        assertEquals("testuser", usuarioObtenido.getNombreUsuario());
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentrenDuplicadosCuandoNoHayUsuarios() {
        Usuario duplicado = usuarioRepositorio.buscarDuplicados("nonexistent@example.com", "nonexistent");

        assertNull(duplicado);
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSeEncuentrenDuplicadosConDatosIncorrectos() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setEmail("existent@example.com");
        clienteExistente.setNombreUsuario("existent");
        clienteExistente.setTipoUsuario(TipoUsuario.Cliente);
        usuarioRepositorio.guardarUsuario(clienteExistente);

        Usuario duplicado = usuarioRepositorio.buscarDuplicados("wrong@example.com", "wrong");

        assertNull(duplicado);
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSePuedaObtenerUnUsuarioPorIdInexistente() {
        assertThrows(NoResultException.class, () -> {
            usuarioRepositorio.getUsuarioById(999);
        });
    }

    @Test
    @Transactional
    @Rollback
    public void queNoSePuedaEditarUnUsuarioInexistente() {
        Cliente clienteInexistente = new Cliente();
        clienteInexistente.setId(999);
        clienteInexistente.setEmail("nonexistent@example.com");
        clienteInexistente.setNombreUsuario("nonexistent");

        assertThrows(Exception.class, () -> {
            usuarioRepositorio.editarUsuario(clienteInexistente);
        });
    }
}
