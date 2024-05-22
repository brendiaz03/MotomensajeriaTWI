package com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.conductor.ConductorRepositorio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.vehiculo.VehiculoRepositorio;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.VehiculoServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioVehiculoTest {

    private VehiculoServicio vehiculoServicio;
    private VehiculoRepositorio vehiculoRepositorio;
    private ConductorRepositorio conductorRepositorio;

    @BeforeEach
    public void init() {
        this.conductorRepositorio = mock(ConductorRepositorio.class);
        this.vehiculoRepositorio = mock(VehiculoRepositorio.class);
        this.vehiculoServicio = new VehiculoServicioImpl(this.vehiculoRepositorio, this.conductorRepositorio);
    }

    @Test
    public void queSePuedaRegistrarUnVehiculoYloDevuelva() {
        //PREPARACION
        Vehiculo vehiculoNuevo = new Vehiculo(5L, "4578956");

        //EJECUCIÓN
        when(this.vehiculoRepositorio.guardarVehiculo(vehiculoNuevo)).thenReturn(vehiculoNuevo);

        Vehiculo vehiculoObtenido = this.vehiculoServicio.registrarVehiculo(vehiculoNuevo);

        //VALIDACIÓN
        assertThat(vehiculoNuevo, equalTo(vehiculoObtenido));
        assertThat(vehiculoNuevo.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

    @Test
    public void queSePuedaEditarUnVehiculo() {
        //PREPARACION
        Vehiculo vehiculoNuevo = new Vehiculo(5L, "4578956");

        Vehiculo vehiculoEsperado = new Vehiculo(5L, "Cami123");

        doNothing().when(vehiculoRepositorio).editar(vehiculoNuevo);

        // EJECUCIÓN
        vehiculoServicio.actualizarVehiculo(vehiculoNuevo);

        when(vehiculoRepositorio.buscarVehiculoPorPatente(vehiculoNuevo.getPatente())).thenReturn(vehiculoEsperado);

        Vehiculo vehiculoObtenido = vehiculoServicio.buscarVehiculoPorPatente(vehiculoNuevo.getPatente());

        // VALIDACIÓN
        assertThat(vehiculoObtenido.getPatente(), equalTo("Cami123"));

    }

}