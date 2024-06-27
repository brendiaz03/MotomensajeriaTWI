package com.tallerwebi.dominio;
import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.enums.ModeloVehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.VehiculoServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.fail;

public class VehiculoServicioTest {

    private VehiculoServicio vehiculoServicio;
    private VehiculoRepositorio vehiculoRepositorio;

    @BeforeEach
    public void init() {
        this.vehiculoRepositorio = mock(VehiculoRepositorio.class);
        this.vehiculoServicio = new VehiculoServicioImpl(this.vehiculoRepositorio);
    }

    @Test
    public void queSePuedaRegistrarUnVehiculoYloDevuelva() {

        Vehiculo vehiculo = new Vehiculo();

        Vehiculo vehiculoObtenido = null;

        vehiculo.setPatente("Cami123");

        try{
            when(this.vehiculoServicio.registrarVehiculo(vehiculo)).thenReturn(vehiculo);

            vehiculoObtenido = this.vehiculoServicio.registrarVehiculo(vehiculo);

            verify(vehiculoRepositorio, times(1)).guardarVehiculo(vehiculo);

            assertThat(vehiculo.getPatente(), equalTo(vehiculoObtenido.getPatente()));

            assertThat(vehiculo, equalTo(vehiculoObtenido));

        } catch (VehiculoDuplicadoException e) {

            throw new RuntimeException(e);

        }

    }

   /* @Test
    public void dadoQueSeEditeElVehiculoDeConductorQueSeVeaReflejadoElCambio() throws UsuarioNoEncontradoException {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente("blanco");

        Vehiculo vehiculoEnBD = new Vehiculo();
        vehiculoEnBD.setPatente("blanco");
        when(vehiculoRepositorio.buscarVehiculoPorPatente(vehiculo.getPatente())).thenReturn(vehiculoEnBD);

        vehiculo.setPatente("Kira");
        vehiculoServicio.actualizarVehiculo(vehiculo);

        assertThat(vehiculo.getPatente(), equalTo("Kira"));
    }*/

    @Test
    public void queSePuedaActualizarUnVehiculo() {

        Vehiculo vehiculoActual = new Vehiculo("ABC123", Color.AZUL, ModeloVehiculo.FORD, TipoVehiculo.AUTO, 1500.0, 2.5);

        vehiculoActual.setPatente("Cami123");

        vehiculoServicio.actualizarVehiculo(vehiculoActual);

        verify(vehiculoRepositorio, times(1)).editar(vehiculoActual);

        assertThat(vehiculoActual.getPatente(), equalTo("Cami123"));

    }

    @Test
    public void queNoLanceExcepcionSiLaPatenteDelVehiculoNoEstaDuplicada() {

        when(vehiculoRepositorio.buscarVehiculoPorPatente("ABC123")).thenReturn(null);

        try {

            vehiculoServicio.verificarDuplicados("ABC123");

        } catch (VehiculoDuplicadoException e) {

            fail("No se espera VehiculoDuplicadoException");

        }

        verify(vehiculoRepositorio, times(1)).buscarVehiculoPorPatente("ABC123");

    }

    @Test
    public void queLanceVehiculoDuplicadoExceptionSiLaPatenteDelVehiculoEstaDuplicada() {

        Vehiculo vehiculoDuplicado = new Vehiculo("ABC123", Color.AZUL, ModeloVehiculo.FORD, TipoVehiculo.AUTO, 1500.0, 2.5);

        when(vehiculoRepositorio.buscarVehiculoPorPatente("ABC123")).thenReturn(vehiculoDuplicado);


        assertThrows(VehiculoDuplicadoException.class, () -> {
            vehiculoServicio.verificarDuplicados("ABC123");
        });

        verify(vehiculoRepositorio, times(1)).buscarVehiculoPorPatente("ABC123");

    }

}