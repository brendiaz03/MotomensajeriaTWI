package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.ConductorNoEncontradoException;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.dom4j.rule.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ConductorControladorTest {

   private ConductorControlador conductorControlador;
   private ConductorServicio conductorServicio;
   private ViajeServicio viajeServicio;
   private HttpSession session;


    @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
       this.viajeServicio=mock(ViajeServicio.class);
       this.session = mock(HttpSession.class);
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.viajeServicio);
   }

    @Test
    public void queSeRendericeElHomeDelConductorConLosViajesPendientesDisponibles() throws UsuarioNoEncontradoException {
        String nombreEsperado = "home-conductor";
        Conductor conductor=mock(Conductor.class);
        List<DatosViaje> viajesPendientes = mock(List.class);
        Double distanciaAFiltrar = 5.0;
        Double latitud = 5.0;
        Double longitud = 3.0;
        Boolean isPenalizado = false;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(session.getAttribute("distancia")).thenReturn(distanciaAFiltrar);
        when(session.getAttribute("latitud")).thenReturn(latitud);
        when(session.getAttribute("longitud")).thenReturn(longitud);
        when(conductor.getPenalizado()).thenReturn(isPenalizado);
        when(viajeServicio.filtrarViajesPorDistanciaDelConductor(latitud, longitud, distanciaAFiltrar, conductor)).thenReturn(viajesPendientes);

        ModelAndView mav = conductorControlador.mostrarHomeConductor(session);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("isPenalizado"), equalTo(isPenalizado));
        assertThat(mav.getModel().get("viajes"), equalTo(viajesPendientes));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).filtrarViajesPorDistanciaDelConductor(latitud, longitud, distanciaAFiltrar, conductor);
    }

    @Test
    public void queSeRendericeElHistorialDelConductorConElHistorialDeViajesRealizadosPorElMismo() throws UsuarioNoEncontradoException {
        String nombreEsperado = "historial-viajes";
        Conductor conductor=mock(Conductor.class);
        List<DatosViaje> historialViajes = mock(List.class);


        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerHistorialDeViajesConductor(conductor)).thenReturn(historialViajes);

        ModelAndView mav= conductorControlador.mostrarHistorial(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("viajesObtenidos"), equalTo(historialViajes));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).obtenerHistorialDeViajesConductor(conductor);
    }


    @Test
    public void queSeMuestreUnErrorEnElHistorialDelConductorSiNoSeEncuentraAlConductor() throws UsuarioNoEncontradoException {
        String nombreEsperado = "historial-viajes";
        String mensajeError="Conductor no encontrado";

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenThrow(UsuarioNoEncontradoException.class);

        ModelAndView mav= conductorControlador.mostrarHistorial(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("error"), equalTo(mensajeError));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio, never()).obtenerHistorialDeViajesConductor(any(Conductor.class));
    }

    @Test
    public void queSeRendericenTodosLosViajesEnProcesoDelConductor() throws UsuarioNoEncontradoException {
        String nombreEsperado = "viajes-aceptados";
        Conductor conductor=mock(Conductor.class);
        List<Viaje> viajesEnProceso = mock(List.class);

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajesEnProceso(conductor)).thenReturn(viajesEnProceso);

        ModelAndView mav= conductorControlador.verViajesEnProceso(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("viajesObtenidos"), equalTo(viajesEnProceso));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).obtenerViajesEnProceso(conductor);
    }

    @Test
    public void queSeMuestreUnErrorEnElApartadoDeViajesEnProcesoDelConductorSiNoSeEncuentraAlConductor() throws UsuarioNoEncontradoException {
        String nombreEsperado = "viajes-aceptados";
        String mensajeError="Conductor no encontrado";

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenThrow(UsuarioNoEncontradoException.class);

        ModelAndView mav= conductorControlador.verViajesEnProceso(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("error"), equalTo(mensajeError));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio, never()).obtenerHistorialDeViajesConductor(any(Conductor.class));
    }

@Test
public void queSeRendericeElViajeAceptadoPorElConductor() throws UsuarioNoEncontradoException {
    String nombreEsperado = "viaje";
    Conductor conductor = mock(Conductor.class);
    DatosViaje viaje = mock(DatosViaje.class);
    Integer idViaje = 123;

    when(session.getAttribute("IDUSUARIO")).thenReturn(1);
    when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
    when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

    ModelAndView mav = conductorControlador.verViaje(session, idViaje);

    assertThat(mav.getViewName(), equalTo(nombreEsperado));
    assertThat(mav.getModel().get("conductor"), equalTo(conductor));
    assertThat(mav.getModel().get("viaje"), equalTo(viaje));

    verify(conductorServicio).obtenerConductorPorId(1);
    verify(viajeServicio).obtenerViajeAceptadoPorId(idViaje);
}

    @Test
    public void queUnConductorCanceleUnViajePreviamenteAceptado() throws UsuarioNoEncontradoException {
        String nombreEsperado = "redirect:/homeConductor";
        Conductor conductor = mock(Conductor.class);
        DatosViaje viaje = mock(DatosViaje.class);
        Integer idViaje = 123;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        ModelAndView mav = conductorControlador.cancelarViaje(session, idViaje);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).cancelarViaje(viaje);
        verify(conductorServicio).estaPenalizado(conductor);
    }
    @Test
    public void queUnConductorTermineUnViajePreviamenteAceptado() {
        String nombreEsperado = "redirect:/homeConductor";
        DatosViaje viaje = mock(DatosViaje.class);
        Integer idViaje = 123;

        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);
        ModelAndView mav = conductorControlador.terminarViaje(idViaje);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        verify(viajeServicio).terminarViaje(viaje);
    }

    @Test
    public void queUnConductorVuelvaAlHome() {
        String nombreEsperado = "redirect:/ubicacion";

        ModelAndView mav = conductorControlador.volverAlHome();

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
    }

    @Test
    public void queUnConductorDescarteUnViajeDeLaListaDeViajesPendientesDelHomeConductor() throws UsuarioNoEncontradoException {
        String nombreEsperado = "redirect:/homeConductor";
        Conductor conductor = mock(Conductor.class);
        Viaje viaje = mock(Viaje.class);
        Integer idViaje = 123;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajePorId(idViaje)).thenReturn(viaje);

        ModelAndView mav = conductorControlador.descartarViaje(session, idViaje);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).duplicarViajeDescartado(viaje,conductor);
        verify(conductorServicio).estaPenalizado(conductor);
}

    @Test
    public void queSeMuestreElDetalleDeLosViajesAceptadosDelConductor() throws UsuarioNoEncontradoException {
        String nombreEsperado = "detalle-viaje";
        Conductor conductor=mock(Conductor.class);
        DatosViaje viaje = mock(DatosViaje.class);
        Integer idViaje= 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajeAceptadoPorId(idViaje)).thenReturn(viaje);

        ModelAndView mav= conductorControlador.VerDetalleDelViaje(session, idViaje);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("viaje"), equalTo(viaje));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).obtenerViajeAceptadoPorId(idViaje);
    }

    @Test
    public void queUnConductorVuelvaAlHistorial() {
    String nombreEsperado = "redirect:/historial";

    ModelAndView mav = conductorControlador.volverAlHistorial();

    assertThat(mav.getViewName(), equalTo(nombreEsperado));
}

    @Test
    public void queSeElConductorFiltrePorDistanciaLosViajesPendientesDisponibles() throws UsuarioNoEncontradoException {
        Double distancia = 10.0;

        ModelAndView mav = conductorControlador.filtrarPorDistancia(session, distancia);

        verify(session).setAttribute("distancia", distancia);
        assertThat(mav.getViewName(), equalTo("redirect:/homeConductor"));
    }

    @Test
    public void queSeCargueLaUbicacionActualDelConductor() {
        String nombreEsperado = "ubicacion";

        ModelAndView mav = conductorControlador.ubicacion();

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
    }

@Test
public void queSeDespenaliceAUnConductorPreviamentePenalizado() throws UsuarioNoEncontradoException {
    String nombreEsperado = "redirect:/homeConductor";
    Integer idConductor=1;
    Conductor conductor= mock(Conductor.class);

    when(session.getAttribute("IDUSUARIO")).thenReturn(idConductor);
    when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);

    ModelAndView mav = conductorControlador.despenalizarConductor(session,idConductor);

    assertThat(mav.getViewName(), equalTo(nombreEsperado));
    verify(conductorServicio).despenalizarConductor(conductor);
}

}
