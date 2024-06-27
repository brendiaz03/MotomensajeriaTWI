package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class VehiculoControladorTest {

    private VehiculoControlador vehiculoControlador;
    private VehiculoServicio vehiculoServicio;
    private ConductorServicio conductorServicio;
    private HttpSession httpSession;

    @BeforeEach
    public void init() {

        this.vehiculoServicio = mock(VehiculoServicio.class);

        this.conductorServicio = mock(ConductorServicio.class);

        this.httpSession = mock(HttpSession.class);

        this.vehiculoControlador = new VehiculoControlador(this.vehiculoServicio, this.conductorServicio);

    }

    @Test
    public void queSePuedaVerElFormularioDelVehiculo() throws UsuarioNoEncontradoException {

        Integer idUsuario = 1;

        Conductor conductorNuevo = new Conductor ();

        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("isEditForm")).thenReturn(true);
        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductorNuevo);

        ModelAndView mav = vehiculoControlador.mostrarRegistroDelVehiculo(vehiculo, null, httpSession);

        assertThat(mav.getViewName(), equalToIgnoringCase("form-vehiculo"));
        assertThat((Boolean) mav.getModel().get("isEditForm"), equalTo(true));
        assertThat(mav.getModel().get("vehiculo"), equalTo(conductorNuevo.getVehiculo()));

    }

    @Test
    public void queSePuedaRegistrarVehiculoExitosamente() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {
        Vehiculo nuevoVehiculo = new Vehiculo();
        Vehiculo vehiculoRegistrado = new Vehiculo();
        Integer idUsuario = 1;

        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(vehiculoServicio.registrarVehiculo(nuevoVehiculo)).thenReturn(vehiculoRegistrado);

        ModelAndView mav = vehiculoControlador.registrarVehiculo(nuevoVehiculo, httpSession);

        assertEquals("redirect:/home", mav.getViewName());
        verify(vehiculoServicio, times(1)).registrarVehiculo(nuevoVehiculo);
        verify(conductorServicio, times(1)).RelacionarVehiculoAConductor(idUsuario, vehiculoRegistrado);
    }

    @Test
    public void queLanceExcepcionUsuarioNoEncontrado() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {
        Vehiculo nuevoVehiculo = new Vehiculo();
        Integer idUsuario = 1;
        String mensajeError = "Usuario no encontrado";

        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(vehiculoServicio.registrarVehiculo(nuevoVehiculo)).thenThrow(new UsuarioNoEncontradoException(mensajeError));

        ModelAndView mav = vehiculoControlador.registrarVehiculo(nuevoVehiculo, httpSession);

        assertEquals("form-vehiculo", mav.getViewName());
        assertEquals(mensajeError, mav.getModel().get("mensajeError"));
        verify(vehiculoServicio, times(1)).registrarVehiculo(nuevoVehiculo);
        verify(conductorServicio, never()).RelacionarVehiculoAConductor(anyInt(), any(Vehiculo.class));
    }

    @Test
    public void queLanceExcepcionVehiculoDuplicado() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {
        Vehiculo nuevoVehiculo = new Vehiculo();
        Integer idUsuario = 1;
        String mensajeError = "Vehiculo duplicado";

        when(httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(vehiculoServicio.registrarVehiculo(nuevoVehiculo)).thenThrow(new VehiculoDuplicadoException(mensajeError));

        ModelAndView mav = vehiculoControlador.registrarVehiculo(nuevoVehiculo, httpSession);

        assertEquals("form-vehiculo", mav.getViewName());
        assertEquals(mensajeError, mav.getModel().get("mensajeError"));
        verify(vehiculoServicio, times(1)).registrarVehiculo(nuevoVehiculo);
        verify(conductorServicio, never()).RelacionarVehiculoAConductor(anyInt(), any(Vehiculo.class));
    }
}
  /*      @PostMapping("/registrar-vehiculo")
        public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws UsuarioNoEncontradoException {
            try{
                Vehiculo vehiculo = vehiculoServicio.registrarVehiculo(nuevoVehiculo);
                conductorServicio.RelacionarVehiculoAConductor((Integer)session.getAttribute("IDUSUARIO"), vehiculo);
                return new ModelAndView("redirect:/home");
            }catch(UsuarioNoEncontradoException | VehiculoDuplicadoException e){
                return this.mostrarRegistroDelVehiculo(nuevoVehiculo,e.getMessage(),session);
            }
        }
*/
   // }

 /*   @Test
    public void queAlEditarUnVehiculoVayaAlPerfil() throws UsuarioNoEncontradoException {

        Integer idUsuario = 1;
        Long idVehiculoA = 1L;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(true);
        when(this.httpSession.getAttribute("idVehiculo")).thenReturn(idVehiculoA);

        doNothing().when(vehiculoServicio).actualizarVehiculo(vehiculo);
        doNothing().when(this.httpSession).setAttribute("isEditForm", false);
        when(this.conductorServicio.RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo)).thenReturn(true);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).actualizarVehiculo(vehiculo);
        verify(conductorServicio, times(1)).RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo);
        verify(httpSession, times(1)).setAttribute("isEditForm", false);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/perfil"));
    }

    @Test
    public void queAlEditarUnVehiculoVayaAlHome() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {

        Integer idUsuario = 1;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(false);
        when(this.vehiculoServicio.registrarVehiculo(vehiculo)).thenReturn(vehiculo);

        when(this.conductorServicio.RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo)).thenReturn(true);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).registrarVehiculo(vehiculo);
        verify(conductorServicio, times(1)).RelacionarVehiculoAConductor(conductorNuevo.getId(), vehiculo);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void queAlRegistrarUnVehiculoConPatenteRepetidaMuestreError() throws UsuarioNoEncontradoException, VehiculoDuplicadoException {

        Integer idUsuario = 1;
        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(idUsuario);
        Vehiculo vehiculo = new Vehiculo();

        when(this.httpSession.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductorNuevo);
        when(this.httpSession.getAttribute("isEditForm")).thenReturn(false);
        when(this.vehiculoServicio.registrarVehiculo(vehiculo)).thenReturn(null);

        ModelAndView mav = this.vehiculoControlador.registrarVehiculo(vehiculo, httpSession);

        verify(vehiculoServicio, times(1)).registrarVehiculo(vehiculo);
        verify(conductorServicio, never()).RelacionarVehiculoAConductor(anyInt(), any(Vehiculo.class));

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/registro-vehiculo"));
        assertNotNull(mav.getModel().get("error"));
        assertEquals("Patente Repetida", mav.getModel().get("error"));
    }

    @Test
    public void queAlMostrarEditarVehiculoSeEstablezcaIsEditFormYRedirijaAlaVistaVehiculo() {

            String viewName = this.vehiculoControlador.mostrarEditarVehiculo(httpSession);

           verify(httpSession).setAttribute("isEditForm", true);

            assertThat(viewName, equalToIgnoringCase("redirect:/vehiculo"));
        }*/

