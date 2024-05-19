/*package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ViajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ViajeControllerTest {

    private ViajeController viajeController;
    private ViajeService viajeService;

    @BeforeEach
    public void init(){
        this.viajeService = mock(ViajeService.class);
        viajeController = new ViajeController(viajeService);
    }

    @Test
    public void queSeMuestreLaVistaViajes(){
        // Preparación
        ModelAndView mav = this.viajeController.mostrarVistaViaje();

        //Ejecución
        String nombreDeLaVistaEsperado = mav.getViewName();

        // Verificación
        assertThat(nombreDeLaVistaEsperado, equalToIgnoringCase("viajes"));
    }
}*/