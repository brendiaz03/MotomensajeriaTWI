package com.tallerwebi.dominio;
import com.tallerwebi.dominio.usuario.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.enums.Color;
import com.tallerwebi.dominio.enums.ModeloVehiculo;
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
        //PREPARACION
        Vehiculo vehiculo= new Vehiculo("sfsfsfd", Color.ROJO, ModeloVehiculo.BMW, TipoVehiculo.AUTO, 0.0, 0.0);

        //EJECUCIÓN
        when(this.vehiculoRepositorio.guardarVehiculo(vehiculo)).thenReturn(vehiculo);

        Vehiculo vehiculoObtenido = this.vehiculoServicio.registrarVehiculo(vehiculo);

        //VALIDACIÓN
        assertThat(vehiculo, equalTo(vehiculoObtenido));
        assertThat(vehiculo.getPatente(), equalTo(vehiculoObtenido.getPatente()));

    }

    @Test
    public void queSeEditeConductor() throws UsuarioNoEncontradoException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente("blanco");

        Vehiculo vehiculoEnBD = new Vehiculo();
        vehiculoEnBD.setPatente("blanco");
        when(vehiculoRepositorio.buscarVehiculoPorPatente(vehiculo.getPatente())).thenReturn(vehiculoEnBD);

        vehiculo.setPatente("Kira");
        vehiculoServicio.actualizarVehiculo(vehiculo);

        assertThat(vehiculo.getPatente(), equalTo("Kira"));
    }

    @Test
    public void queSePuedaActualizarUnVehiculo() {

        Vehiculo vehiculoModificado = new Vehiculo("ABC123", Color.AZUL, ModeloVehiculo.FORD, TipoVehiculo.AUTO, 1500.0, 2.5);

        when(vehiculoRepositorio.buscarVehiculoPorPatente("ABC123")).thenReturn(vehiculoModificado);

        doNothing().when(vehiculoRepositorio).editar(any(Vehiculo.class));

        vehiculoServicio.actualizarVehiculo(vehiculoModificado);

        assertThat(vehiculoModificado.getColor(), equalTo(Color.AZUL));
        assertThat(vehiculoModificado.getModelo(), equalTo(ModeloVehiculo.FORD));
        assertThat(vehiculoModificado.getTipoDeVehiculo(), equalTo(TipoVehiculo.AUTO));
        assertThat(vehiculoModificado.getPesoSoportado(), equalTo(1500.0));
        assertThat(vehiculoModificado.getDimensionDisponible(), equalTo(2.5));
    }

}