package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorDuplicadoException;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
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

   private VehiculoServicio vehiculoServicio;
   private HttpSession session;


    @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
       this.imagenServicio = mock(ImagenServicio.class);
       this.vehiculoServicio=mock(VehiculoServicio.class);
       this.session = mock(HttpSession.class);
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.imagenServicio, this.vehiculoServicio);

   }

    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado= "registro-conductor";
        Conductor conductor=new Conductor();

        ModelAndView mav = this.conductorControlador.mostrarFormConductor("", session);
        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void queAlSolicitarLaPantallaIrAPerfilSeMuestreElPerfilDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "perfil-conductor";
        Integer idUsuario = 1;

        Conductor conductor = new Conductor();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        conductor.setVehiculo(vehiculo);

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductor);

        ModelAndView mav = this.conductorControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
    }


//    @Test
//    public void queAlSolicitarLaPantallaEditarSeMuestreElFormularioDeEdicionDelConductor() {
//        String resultado = this.conductorControlador.mostrarEditarConductor(session);
//        verify(session).setAttribute("isEditForm", true);
//        assertThat(resultado, equalToIgnoringCase("redirect:/registro-conductor"));
//    }
//
//    @Test
//    public void queAlSolicitarLaPantallaFotoPerfilSeMuestreElFormularioDeEdicionDeFotoDePerfilDelConductor() throws ConductorNoEncontradoException {
//        ModelAndView mav = this.conductorControlador.irAEditarFotoPerfil(session);
//        assertThat(mav.getViewName(), equalToIgnoringCase("foto-perfil"));
//    }
//
//
//    @Test
//    public void queSeRedirijaAVehiculoSiElRegistroEsExitoso() throws Exception {
//        Conductor nuevoConductor = new Conductor();
//        when(conductorServicio.registrarConductorNoDuplicado(nuevoConductor)).thenReturn(true);
//
//        ModelAndView mav = this.conductorControlador.registrarConductor(nuevoConductor, session);
//
//        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/vehiculo"));
//    }
//
//    @Test
//    public void queSeMuestreMensajeDeErrorSiElConductorEsDuplicado() throws Exception {
//        Conductor nuevoConductor = new Conductor();
//        doThrow(new ConductorDuplicadoException("Conductor duplicado")).when(conductorServicio).registrarConductorNoDuplicado(nuevoConductor);
//
//        ModelAndView mav = this.conductorControlador.registrarConductor(nuevoConductor, session);
//
//        assertThat(mav.getViewName(), equalToIgnoringCase("registro-conductor"));
//        assertThat((String) mav.getModel().get("mensajeError"), equalToIgnoringCase("Conductor duplicado"));
//    }
//
//    @Test
//    public void queEditeElConductorYRedireccioneAlPerfil() throws ConductorNoEncontradoException {
//        // Configuración de datos de prueba
//        Conductor conductorEditado = new Conductor();
//        conductorEditado.setNombre("Facu");
//        conductorEditado.setApellido("Varela");
//        conductorEditado.setEmail("asd123@gmail.com");
//
//        Integer idUsuario = 1;
//        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
//
//        ModelAndView mav = this.conductorControlador.editarConductor(session, conductorEditado);
//
//        verify(conductorServicio, times(1)).editarConductor(conductorEditado);
//        verify(session, times(1)).setAttribute("isEditForm", false);
//        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/perfil"));
//    }


}
