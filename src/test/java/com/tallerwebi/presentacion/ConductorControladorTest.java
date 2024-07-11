package com.tallerwebi.presentacion;

import static org.hamcrest.Matchers.startsWith;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.*;
import com.tallerwebi.dominio.mercadoPago.MercadoPagoServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import com.tallerwebi.presentacion.Datos.DatosViaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ConductorControladorTest {

   private ConductorControlador conductorControlador;
   private ConductorServicio conductorServicio;
   private ViajeServicio viajeServicio;
   private HttpSession session;
   private MercadoPagoServicio mercadoPagoServicio;
   private RedirectAttributes redirectAttributes;

    @BeforeEach
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
       this.viajeServicio=mock(ViajeServicio.class);
       this.session = mock(HttpSession.class);
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.viajeServicio);
        this.mercadoPagoServicio = mock(MercadoPagoServicio.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
   }

    @Test
    public void queSeRendericeElHomeDelConductorNoPenalizadoConLosViajesPendientesDisponiblesFiltradosPorLaUbicacionDelConductor() throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException {
        String nombreEsperado = "home-conductor";
        Conductor conductor=mock(Conductor.class);
        Vehiculo vehiculo=mock(Vehiculo.class);
        List<DatosViaje> viajesPendientes = mock(List.class);
        Double distanciaAFiltrar = 5.0;
        Double latitud = 5.0;
        Double longitud = 3.0;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(session.getAttribute("noVehiculo")).thenReturn(false);
        when(conductor.getVehiculo()).thenReturn(vehiculo);
        when(session.getAttribute("distancia")).thenReturn(distanciaAFiltrar);
        when(session.getAttribute("latitud")).thenReturn(latitud);
        when(session.getAttribute("longitud")).thenReturn(longitud);
        when(viajeServicio.filtrarViajesPorDistanciaDelConductor(latitud, longitud, distanciaAFiltrar, conductor)).thenReturn(viajesPendientes);

        ModelAndView mav = conductorControlador.mostrarHomeConductor(session);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("viajes"), equalTo(viajesPendientes));
        assertFalse(mav.getModel().containsKey("isPenalizado"));

        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).filtrarViajesPorDistanciaDelConductor(latitud, longitud, distanciaAFiltrar, conductor);
    }

    @Test
    public void queSeRendericeElHomeDelConductorConElMensajeDePenalizacionEnCasoDeQueElMismoEstePenalizado() throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException {
        String nombreEsperado = "home-conductor";
        Conductor conductor=mock(Conductor.class);
        Boolean isPenalizado = true;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(conductor.getPenalizado()).thenReturn(isPenalizado);

        ModelAndView mav = conductorControlador.mostrarHomeConductor(session);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
        assertThat(mav.getModel().get("isPenalizado"), equalTo(isPenalizado));
        assertFalse(mav.getModel().containsKey("viajes"));
        assertFalse(mav.getModel().containsKey("cantidadDeViajes"));

        verify(conductorServicio).obtenerConductorPorId(1);
    }

    @Test
    public void queSeRendericeElHomeDelConductorConUnaAdvertenciaSiElConductorNoTieneUnVehiculoAsociado() throws UsuarioNoEncontradoException, CoordenadasNoEncontradasException {
            String nombreEsperado = "home-conductor";
            Conductor conductor=mock(Conductor.class);

            when(session.getAttribute("IDUSUARIO")).thenReturn(1);
            when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
            when(conductor.getPenalizado()).thenReturn(false);
            when(conductor.getVehiculo()).thenReturn(null);

            ModelAndView mav = conductorControlador.mostrarHomeConductor(session);

            assertThat(mav.getViewName(), equalTo(nombreEsperado));
            assertThat(mav.getModel().get("conductor"), equalTo(conductor));
            assertThat(mav.getModel().get("noVehiculo"), equalTo(true));
            assertFalse(mav.getModel().containsKey("isPenalizado"));
            assertFalse(mav.getModel().containsKey("viajes"));
            assertFalse(mav.getModel().containsKey("cantidadDeViajes"));

            verify(conductorServicio).obtenerConductorPorId(1);
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
    public void queSeMuestreUnErrorEnElHistorialDelConductorSiNoSeEncuentraAlConductorPorMedioDeUnaUsuarioNoEncontradoException() throws UsuarioNoEncontradoException {
        String nombreEsperado = "redirect:/*";
        String mensajeError="Conductor no encontrado Por favor, vuelva a intentarlo.";

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenThrow(new UsuarioNoEncontradoException("Conductor no encontrado"));
        ModelAndView mav= conductorControlador.mostrarHistorial(session);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo(mensajeError));
        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio, never()).obtenerHistorialDeViajesConductor(any(Conductor.class));
    }

    @Test
    public void queSeRendericenTodosLosViajesEnProcesoDelConductorPorMedioDeUnaPreviaBusqueda() throws UsuarioNoEncontradoException {
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
        String nombreEsperado = "redirect:/*";
        String mensajeError="Conductor no encontrado Por favor, vuelva a intentarlo.";

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenThrow(new UsuarioNoEncontradoException("Conductor no encontrado"));
        ModelAndView mav= conductorControlador.verViajesEnProceso(session);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        assertThat(mav.getModel().get("mensajeError"), equalTo(mensajeError));
        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio, never()).obtenerHistorialDeViajesConductor(any(Conductor.class));
    }

@Test
public void queSeRendericeLaVistaQueMuestraElViajeAceptadoSeleccionadoPorElConductor() throws UsuarioNoEncontradoException, ViajeNoEncontradoException {
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
    public void queUnConductorCanceleUnViajePreviamenteAceptado() throws UsuarioNoEncontradoException, ViajeNoEncontradoException {
        String nombreEsperado = "redirect:/home-conductor";
        Conductor conductor = mock(Conductor.class);
        Viaje viaje = mock(Viaje.class);
        Integer idViaje = 123;

        when(session.getAttribute("IDUSUARIO")).thenReturn(1);
        when(conductorServicio.obtenerConductorPorId(1)).thenReturn(conductor);
        when(viajeServicio.obtenerViajePorId(idViaje)).thenReturn(viaje);
        ModelAndView mav = conductorControlador.cancelarViaje(session, idViaje);

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
        verify(conductorServicio).obtenerConductorPorId(1);
        verify(viajeServicio).cancelarViaje(viaje);
        verify(conductorServicio).estaPenalizado(conductor);
    }
    @Test
    public void queUnConductorTermineUnViajePreviamenteAceptado() throws ViajeNoEncontradoException {
        String nombreEsperado = "redirect:/home-conductor";
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
    public void queUnConductorDescarteUnViajeDeLaListaDeViajesPendientesDelHomeConductor() throws UsuarioNoEncontradoException, ViajeNoEncontradoException, ClienteNoEncontradoException {
        String nombreEsperado = "redirect:/home-conductor";
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
    public void queSeMuestreElDetalleDeUnViajesAceptadoDelConductor() throws UsuarioNoEncontradoException, ViajeNoEncontradoException {
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
    public void queUnConductorVuelvaAlHistorialDeViajesRealizados() {
    String nombreEsperado = "redirect:/historial";

    ModelAndView mav = conductorControlador.volverAlHistorial();

    assertThat(mav.getViewName(), equalTo(nombreEsperado));
}

    @Test
    public void queSeElConductorFiltrePorUnaDistanciaEspecificaLosViajesPendientesDisponibles() {
        Double distancia = 10.0;

        ModelAndView mav = conductorControlador.filtrarPorDistancia(session, distancia);

        verify(session).setAttribute("distancia", distancia);
        assertThat(mav.getViewName(), equalTo("redirect:/home-conductor"));
    }

    @Test
    public void queSeCargueLaUbicacionActualDelConductor() {
        String nombreEsperado = "ubicacion";

        ModelAndView mav = conductorControlador.ubicacion();

        assertThat(mav.getViewName(), equalTo(nombreEsperado));
    }

    @Test
    public void queSeDespenaliceAUnConductorPenalizadoConUnMontoValidoParaMotomensajeria() throws IOException {

        Double montoPenalizacion = 5000.0;

        String redirectUrl = "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=1867816013-7583c110-0184-4937-ad14-b76d76a35160";

        when(mercadoPagoServicio.pagarPenalizacionMp(montoPenalizacion)).thenReturn(redirectUrl);

        ModelAndView mav = conductorControlador.despenalizarConductor(montoPenalizacion, redirectAttributes, session);

        assertThat(mav.getViewName(), startsWith("redirect:https://www.mercadopago.com.ar/checkout/v1/redirect"));
    }

    @Test
    public void queNoSeDespenaliceUnConductorPenalizadoConUnMontoInvalidoMenorQueCincoMil() {

        Double montoPenalizacion = 4999.0;

        ModelAndView mav = conductorControlador.despenalizarConductor(montoPenalizacion, redirectAttributes, session);

        assertThat(mav.getViewName(), equalTo("redirect:/home-conductor"));

        verify(redirectAttributes).addFlashAttribute("error", "Monto de penalización inválido.");
    }

}
