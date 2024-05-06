package com.tallerwebi.presentacion;

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
   //private IRepositoryConductor iRepositoryConductor;
   // private SessionFactory sessionFactory;

   @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       //this.iRepositoryConductor= new RepositoryConductorImpl(sessionFactory);
       //this.iServiceConductor= new ServiceConductorImpl((RepositoryConductorImpl) iRepositoryConductor);
       this.iServiceConductor= mock(IServiceConductor.class); //con el mock solamente probaria los métodos de controlador y nada más (obvio que depende de en que instancia lo pruebe xd
       this.controllerConductor = new ControllerConductor(this.iServiceConductor);

   }

    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor(){

        ModelAndView mav= this.controllerConductor.mostrarRegistroConductor();
        String message = mav.getModel().get("message").toString();
        assertThat(mav.getViewName(),equalToIgnoringCase("registro-conductor"));
        assertThat(message, equalToIgnoringCase("Bienvenido"));
    }


//    @Test
//    public void queUnConductorCompleteElFormulario() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
//
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor);
//        when(iServiceConductor.verificarDatosDeRegistro(any(Conductor.class))).thenReturn("Datos cargados con éxito");
//
//        assertEquals("redirect:/home", modelAndView.getViewName());
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
