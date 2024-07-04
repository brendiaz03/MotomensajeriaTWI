package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class VehiculoControladorTest {

    private VehiculoServicio vehiculoServicioMock;
    private ConductorServicio conductorServicioMock;
    private VehiculoControlador vehiculoControlador;

    @BeforeEach
    public void setUp() {
        vehiculoServicioMock = mock(VehiculoServicio.class);
        conductorServicioMock = mock(ConductorServicio.class);
        vehiculoControlador = new VehiculoControlador(vehiculoServicioMock, conductorServicioMock);
    }

    @Test
    public void testMostrarEditarVehiculo() {
        // Preparación
        HttpSession sessionMock = mock(HttpSession.class);

        // Ejecución
        String viewName = vehiculoControlador.mostrarEditarVehiculo(sessionMock);

        // Verificación
        assertThat(viewName, equalTo("redirect:/form-vehiculo"));
        verify(sessionMock, times(1)).setAttribute("isEditForm", true);
    }

}