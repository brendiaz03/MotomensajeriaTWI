package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerConductorTest {

   private ControllerConductor controllerConductor;
   private IServiceConductor iServiceConductor;

   @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       //this.iRepositoryConductor= new RepositoryConductorImpl(sessionFactory);
       //this.iServiceConductor= new ServiceConductorImpl((RepositoryConductorImpl) iRepositoryConductor);
       this.iServiceConductor= mock(IServiceConductor.class); //con el mock solamente probaria los métodos de controlador y nada más (obvio que depende de en que instancia lo pruebe xd
       this.controllerConductor = new ControllerConductor(this.iServiceConductor);
   }
    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor(){
        ModelAndView mav= this.controllerConductor.mostrarRegistroConductor("");
        assertThat(mav.getViewName(),equalToIgnoringCase("registro-conductor"));
    }

    @Test
    public void queUnConductorCompleteElFormulario() throws Exception {
        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123", "1561639242", "0001002900001234567891");
        when(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)).thenReturn(true);
        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);

        assertEquals("home", modelAndView.getViewName());
        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
    }

//    @Test
//    public void queUnConductorConUnDniInvalidoTireUnaExcepcion() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123", "1561639242", "0001002900001234567891");
//        when(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)).thenReturn(false);
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);
//
//        assertEquals("home", modelAndView.getViewName());
//        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
//    }
//    @Test
//    public void queUnConductorConUnEmailInvalidoTireUnaExcepcion() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123", "1561639242", "0001002900001234567891");
//        when(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)).thenReturn(false);
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);
//
//        assertEquals("home", modelAndView.getViewName());
//        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
//    }
//    @Test
//    public void queUnConductorConUnPasswordlInvalidoTireUnaExcepcion() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123", "1561639242", "0001002900001234567891");
//        when(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)).thenReturn(false);
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);
//
//        assertEquals("home", modelAndView.getViewName());
//        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
//    }
//    @Test
//    public void queUnConductorConUnCVUInvalidoTireUnaExcepcion() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123", "1561639242", "0001002900001234567891");
//        when(iServiceConductor.verificarDatosDeRegistro(nuevoConductor)).thenReturn(false);
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);
//
//        assertEquals("home", modelAndView.getViewName());
//        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
//    }

//    @Test
//    public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome() {
//
//        Conductor nuevoConductor= new Conductor("Piccolo","Daimaku",42952902,"piccolo.daimaku@gmail.com","pico123","Namekian","Pueyrredon 3339","1161639242","1234567890123456789012");
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setSession(new MockHttpSession());
//
//        // Ejecución
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor, request);
//
//        // Validación
//        assertEquals("redirect:/home", modelAndView.getViewName());
//    }

}
