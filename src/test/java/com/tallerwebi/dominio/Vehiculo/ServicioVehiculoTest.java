package com.tallerwebi.dominio.Vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorNoEncontradoException;
import com.tallerwebi.dominio.enums.TipoVehiculo;
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

    @BeforeEach
    public void init() {
        this.vehiculoRepositorio = mock(VehiculoRepositorio.class);
        this.vehiculoServicio = new VehiculoServicioImpl(this.vehiculoRepositorio);
    }

    @Test
    public void queSePuedaRegistrarUnVehiculoYloDevuelva() {
        //PREPARACION
        Vehiculo vehiculo= new Vehiculo("sfsfsfd", "Rojo", "Ford", TipoVehiculo.AUTO, 0.0, 0.0);

        //EJECUCIÓN
        when(this.vehiculoRepositorio.guardarVehiculo(vehiculo)).thenReturn(vehiculo);

        Vehiculo vehiculoObtenido = this.vehiculoServicio.registrarVehiculo(vehiculo);

        //VALIDACIÓN
        assertThat(vehiculo, equalTo(vehiculoObtenido));
        assertThat(vehiculo.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

    @Test
    public void queSeEditeConductor() throws ConductorNoEncontradoException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente("blanco");

        Vehiculo vehiculoEnBD = new Vehiculo();
        vehiculoEnBD.setPatente("blanco");
        when(vehiculoRepositorio.buscarVehiculoPorPatente(vehiculo.getPatente())).thenReturn(vehiculoEnBD);

        vehiculo.setPatente("Kira");
        vehiculoServicio.actualizarVehiculo(vehiculo);

        assertThat(vehiculo.getPatente(), equalTo("Kira"));
    }

}