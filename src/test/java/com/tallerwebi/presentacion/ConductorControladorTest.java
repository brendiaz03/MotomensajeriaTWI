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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.io.IOException;

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

    /*@Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado= "registro-conductor";
        Conductor conductor=new Conductor();
        Integer idConductor=1;
        Imagen nuevaImagen=new Imagen();

        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(nuevaImagen);
        when(this.session.getAttribute("isEditForm")).thenReturn(true);
        when(this.session.getAttribute("IDUSUARIO")).thenReturn(anyInt());
        when(this.conductorServicio.obtenerConductorPorId(idConductor)).thenReturn(conductor);

        ModelAndView mav = this.conductorControlador.mostrarFormConductor("", session);
        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("conductor"), equalTo(conductor));
    }*/

    @Test
    public void queAlSolicitarLaPantallaIrAPerfilSeMuestreElPerfilDelConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "perfil-conductor";
        Integer idUsuario = 1;
        Imagen nuevaImagen=new Imagen();

        Conductor conductor = new Conductor();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        conductor.setVehiculo(vehiculo);

        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("NOMBRE")).thenReturn("facu");
        when(session.getAttribute("APELLIDO")).thenReturn("varela");
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(nuevaImagen);
        when(conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductor);

        ModelAndView mav = this.conductorControlador.irAPerfil(session);

        assertThat(mav.getViewName(), equalToIgnoringCase(nombreEsperado));
        assertThat(mav.getModel().get("nombreUsuario"), equalTo("facu"));
        assertThat(mav.getModel().get("apellidoUsuario"), equalTo("varela"));
    }

    @Test
    public void queSeMuestreElFormularioDeEditarConductor() {
        String nombreEsperado = "redirect:/registro-conductor";

        doNothing().when(session).setAttribute("isEditForm", true);

        ModelAndView mav= this.conductorControlador.mostrarEditarConductor(session);
        assertThat(mav.getViewName(), equalTo(nombreEsperado));
    }

    @Test
    public void queSeMuestreElFormularioDeEditarImagenDePerfilDeConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "foto-perfil";
        Integer idUsuario = 1;
        Imagen nuevaImagen=new Imagen();
        Conductor conductor=new Conductor();

        when(session.getAttribute("isUsuarioLogueado")).thenReturn(true);
        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        when(this.imagenServicio.getImagenByName(anyString())).thenReturn(nuevaImagen);
        when(conductorServicio.obtenerConductorPorId(idUsuario)).thenReturn(conductor);

        ModelAndView mav = conductorControlador.irAEditarFotoPerfil(session);
        assertThat(mav.getViewName(),equalTo(nombreEsperado));
        assertThat(mav.getModel().get("idUsuario"),equalTo(idUsuario));
        assertThat(mav.getModel().get("isUsuarioLogueado"),equalTo(true));
    }

    /*@Test
    public void queSePuedaRegistrarUnConductor() throws Exception {
        Conductor nuevoConductor = new Conductor();
        Conductor conductor = new Conductor();
        conductor.setId(1);

        when(conductorServicio.registrarConductorNoDuplicado(nuevoConductor)).thenReturn(conductor);

        ModelAndView mav = conductorControlador.registrarConductor(nuevoConductor, session);

        assertThat(mav.getViewName(), equalTo("redirect:/vehiculo"));
        verify(session).setAttribute("IDUSUARIO", conductor.getId());
    }*/
    @Test
    public void queSePuedaEditarUnConductor() throws ConductorNoEncontradoException {
        String nombreEsperado = "redirect:/perfil";
        Integer idUsuario = 1;
        Conductor nuevo= new Conductor();

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doNothing().when(conductorServicio).editarConductor(nuevo);
        doNothing().when(session).setAttribute("isEditForm", false);

        ModelAndView mav=this.conductorControlador.editarConductor(session,nuevo);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
    }
    @Test
    public void queUnConductorSubaUnaFotoDesdeElFormulario () throws ConductorNoEncontradoException, IOException {
        String nombreEsperado = "redirect:/perfil";
        Integer idUsuario = 1;
        MultipartFile nuevo =null;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doNothing().when(conductorServicio).ingresarImagen(nuevo,idUsuario);

        ModelAndView mav = this.conductorControlador.subirFoto(nuevo,session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
    }

    @Test
    public void queSeBorreElConductor(){
        String nombreEsperado = "redirect:/cerrar-sesion";
        Integer idUsuario = 1;

        when(session.getAttribute("IDUSUARIO")).thenReturn(idUsuario);
        doNothing().when(conductorServicio).borrarConductor(idUsuario);
        ModelAndView mav = this.conductorControlador.borrarCuenta(session);

        assertThat(mav.getViewName(),equalTo(nombreEsperado));
    }



}
