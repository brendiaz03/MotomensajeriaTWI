package com.tallerwebi.dominio.Vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.ServicioVehiculoImpl;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ServicioVehiculoTest {

    private IServicioVehiculo iservicioVehiculo;

    private IRepositoryVehiculo irepositoryVehiculo;

    private List<Vehiculo> vehiculos1 = new ArrayList<>();

    private ServicioVehiculoImpl servicioVehiculoImpl;

    @BeforeEach
    public void init(){
        this.iservicioVehiculo = new ServicioVehiculoImpl(vehiculos1, irepositoryVehiculo);
    }

    //FUNCIONA OK
    @Test
    public void queSeRegistreUnNuevoVehiculoConElDniDelConductor(){

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        List<Conductor>conductores = new ArrayList<>();

        conductores.add(nuevoConductor);

        //nuevoConductor.agregarVehiculo();

        Boolean resultado = this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo1, nuevoConductor, conductores);

        assertThat(resultado, equalTo(true));

    }

    //FUNCIONA OK
    @Test
    public void queSePuedanObtenerTodosLosVehiculosDelConductor(){
        //preparación

        List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        //ejecucion

        conductores.add(nuevoConductor);

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo2 = new Vehiculo(4L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo3 = new Vehiculo(3L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo4 = new Vehiculo(2L, "DEF478", "Rojo", "Auto", TipoVehiculo.AUTO, 5000.0, 28.0, 6L);

        vehiculos1.add(vehiculo1);
        vehiculos1.add(vehiculo2);
        vehiculos1.add(vehiculo3);
        vehiculos1.add(vehiculo4);

        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo1, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo2, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo3, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo4, nuevoConductor, conductores);

        //verificación
        assertThat((this.iservicioVehiculo.get()).size(), equalTo(4));
    }

    //FUNCIONA OK
    @Test
    public void queAlBuscarVehiculosPorTipoDeVehiculoAutoDevuelvaLosVehiculosCorrespondientes(){

        //List<Vehiculo> vehiculos = new ArrayList<>();
        //preparación

        List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        //ejecucion

        conductores.add(nuevoConductor);

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo2 = new Vehiculo(4L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo3 = new Vehiculo(3L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo4 = new Vehiculo(2L, "DEF478", "Rojo", "Auto", TipoVehiculo.AUTO, 5000.0, 28.0, 6L);

        vehiculos1.add(vehiculo1);
        vehiculos1.add(vehiculo2);
        vehiculos1.add(vehiculo3);
        vehiculos1.add(vehiculo4);

        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo1, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo2, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo3, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo4, nuevoConductor, conductores);

        //ejecución

        List<Vehiculo> vehiculosEncontrados = this.iservicioVehiculo.getByTipoDeVehiculo(TipoVehiculo.AUTO);

        //verificación
        assertThat(vehiculosEncontrados.size(), equalTo(2));

    }

    @Test
    public void queSePuedaModificarUnVehiculoDeLaListaDelConductor(){

        List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        conductores.add(nuevoConductor);

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo2 = new Vehiculo(4L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo3 = new Vehiculo(3L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculoNuevo = new Vehiculo(2L, "DEF478", "Rojo", "Auto", TipoVehiculo.AUTO, 5000.0, 28.0, 6L);

        vehiculos1.add(vehiculo1);
        vehiculos1.add(vehiculo2);
        vehiculos1.add(vehiculo3);

        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo1, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo2, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo3, nuevoConductor, conductores);

        Boolean resultado = this.iservicioVehiculo.modificarVehiculo(vehiculos1, vehiculoNuevo, nuevoConductor, conductores);

        assertThat(resultado, equalTo(true));

    }

}
