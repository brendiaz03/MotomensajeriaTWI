package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.IServiceConductor;
import com.tallerwebi.dominio.imagen.IImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerConductorTest {

   private ControllerConductor controllerConductor;
   private IServiceConductor iServiceConductor;

   private IImageService iimageService;
 
   @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.iServiceConductor= mock(IServiceConductor.class);
       this.iimageService= mock(IImageService.class);//con el mock solamente probaria los métodos de controlador y nada más (obvio que depende de en que instancia lo pruebe xd
       this.controllerConductor = new ControllerConductor(this.iServiceConductor, this.iimageService);

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

        assertThat(modelAndView.getViewName(),equalToIgnoringCase("redirect:/home"));


        verify(iServiceConductor, times(1)).verificarDatosDeRegistro(nuevoConductor);
    }

}
