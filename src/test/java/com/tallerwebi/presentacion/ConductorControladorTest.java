package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorDuplicadoException;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ConductorControladorTest {

   private ConductorControlador conductorControlador;
   private ConductorServicio conductorServicio;
   private ImagenServicio imagenServicio;
   private HttpSession session;


    @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
       this.imagenServicio = mock(ImagenServicio.class);
       this.session = mock(HttpSession.class);
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.imagenServicio);

   }

    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor() throws ConductorNoEncontradoException {
        ModelAndView mav = this.conductorControlador.mostrarRegistroConductor("", session);
        assertThat(mav.getViewName(), equalToIgnoringCase("registro-conductor"));
    }

    @Test
    public void queAlSolicitarLaPantallaIrAPerfilSeMuestreElPerfilDelConductor() throws ConductorNoEncontradoException {
        ModelAndView mav = this.conductorControlador.irAPerfil(session);
        assertThat(mav.getViewName(), equalToIgnoringCase("perfil-conductor"));
    }

    @Test
    public void queAlSolicitarLaPantallaEditarSeMuestreElFormularioDeEdicionDelConductor() {
        String resultado = this.conductorControlador.mostrarEditarConductor(session);
        verify(session).setAttribute("isEditForm", true);
        assertThat(resultado, equalToIgnoringCase("redirect:/registro-conductor"));
    }

    @Test
    public void queAlSolicitarLaPantallaFotoPerfilSeMuestreElFormularioDeEdicionDeFotoDePerfilDelConductor() throws ConductorNoEncontradoException {
        ModelAndView mav = this.conductorControlador.irAEditarFotoPerfil(session);
        assertThat(mav.getViewName(), equalToIgnoringCase("foto-perfil"));
    }


    @Test
    public void queSeRedirijaAVehiculoSiElRegistroEsExitoso() throws Exception {
        Conductor nuevoConductor = new Conductor();
        when(conductorServicio.verificarDatosDeRegistro(nuevoConductor)).thenReturn(true);

        ModelAndView mav = this.conductorControlador.registrarConductor(nuevoConductor, session);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/vehiculo"));
    }

    @Test
    public void queSeMuestreMensajeDeErrorSiElConductorEsDuplicado() throws Exception {
        Conductor nuevoConductor = new Conductor();
        doThrow(new ConductorDuplicadoException("Conductor duplicado")).when(conductorServicio).verificarDatosDeRegistro(nuevoConductor);

        ModelAndView mav = this.conductorControlador.registrarConductor(nuevoConductor, session);

        assertThat(mav.getViewName(), equalToIgnoringCase("registro-conductor"));
        assertThat((String) mav.getModel().get("mensajeError"), equalToIgnoringCase("Conductor duplicado"));
    }

    @Test
    public void queEditeElConductorYRedireccioneAlPerfil() throws ConductorNoEncontradoException {
        // Configuración de datos de prueba
        Conductor conductorEditado = new Conductor();
        conductorEditado.setNombre("Facu");
        conductorEditado.setApellido("Varela");
        conductorEditado.setEmail("asd123@gmail.com");

        Integer idUsuario = 1;
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);

        ModelAndView mav = this.conductorControlador.editarConductor(session, conductorEditado);

        verify(conductorServicio, times(1)).editarConductor(conductorEditado);
        verify(session, times(1)).setAttribute("isEditForm", false);
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/perfil"));
    }

    
}
