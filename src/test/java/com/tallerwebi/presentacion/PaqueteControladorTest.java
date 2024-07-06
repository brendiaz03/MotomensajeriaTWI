package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.paquete.Paquete;
import com.tallerwebi.dominio.paquete.PaqueteServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaqueteControladorTest {

    private HttpSession httpSession;

    private PaqueteControlador paqueteControlador;

    private PaqueteServicio paqueteServicio;

    @BeforeEach
    public void init(){
        this.httpSession = mock(HttpSession.class);
        this.paqueteServicio = mock(PaqueteServicio.class);
        this.paqueteControlador = new PaqueteControlador(paqueteServicio);
    }

    @Test
    public void queSeRendericeElPasoUnoEnElFormDeViajeElCualRefiereALaCrecionOEdicionDelPaquete() {
        String viewName= "redirect:/form-viaje";

        ModelAndView mav = paqueteControlador.mostrarFormEditorPaquete(httpSession);

        verify(httpSession).setAttribute("isEditPackage", true);
        verify(httpSession).setAttribute("pasoActual", 1);
        assertThat(viewName, equalTo(mav.getViewName()));
    }

    @Test
    public void queSeGuardeElPaqueteLocalmenteParaAvanzarAlSiguientePasoQueRefiereALaCreacionDelViaje() {
        String viewName= "redirect:/form-viaje";
        Paquete paquete=mock(Paquete.class);

        ModelAndView mav = paqueteControlador.guardarPaqueteLocalmente(paquete,httpSession);

        verify(httpSession).setAttribute("paqueteActual", paquete);
        verify(httpSession).setAttribute("pasoActual", 2);
        assertThat(viewName, equalTo(mav.getViewName()));
    }

    @Test
    public void queSeEditeCorrectamenteElPaqueteLocalmenteYAvanceAlPasoTresQueRefiereAlDetalleCompletoDelEnvio() {
        String viewName= "redirect:/form-viaje";
        Paquete paquete=mock(Paquete.class);

        ModelAndView mav = paqueteControlador.editarPaquete(paquete,httpSession);

        verify(httpSession).setAttribute("isEditPackage", false);
        verify(httpSession).setAttribute("paqueteActual", paquete);
        verify(httpSession).setAttribute("pasoActual", 3);
        assertThat(viewName, equalTo(mav.getViewName()));
    }

}