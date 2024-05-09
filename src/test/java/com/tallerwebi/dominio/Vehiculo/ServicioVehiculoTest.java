package com.tallerwebi.dominio.Vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.Datos.DatosVehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
//import com.tallerwebi.dominio.vehiculo;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioVehiculoTest {

    private IServicioVehiculo iservicioVehiculo;

    private IRepositoryVehiculo irepositoryVehiculo;

    private ServicioVehiculoImpl servicioVehiculoImpl;

    @BeforeEach
    public void init(){
        this.irepositoryVehiculo = mock(IRepositoryVehiculo.class);
        this.iservicioVehiculo = new ServicioVehiculoImpl(irepositoryVehiculo);
    }

    //FUNCIONA OK
    @Test
    public void queSeRegistreUnNuevoVehiculoConElIdDelConductor() {

        //List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("LALALA@LA.COM", "123", "Joaquin", "Perez", 1234);

        //List<Vehiculo> vehiculos1 = new ArrayList<>();

        Vehiculo vehiculo = new Vehiculo("123ABC", "Rosa", "Vw", nuevoConductor.getId());

        //conductores.add(nuevoConductor);

        //vehiculos1.add(vehiculo);

        //List<DatosVehiculo> conductoresConVehiculos = new ArrayList<>();

        when(this.irepositoryVehiculo.obtenerVehiculoPorElIdDelConductor(nuevoConductor.getId())).thenReturn(vehiculo);

        //when(this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo, nuevoConductor, conductores)).thenReturn(conductorConVehiculoAgregado);
        //(iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo, nuevoConductor, conductores)).thenReturn(true);

        Vehiculo vehiculoObtenido = this.iservicioVehiculo.obtenerVehiculoPorIdDelConductor(nuevoConductor.getId());

        //Boolean resultado = iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos1, vehiculo, nuevoConductor, conductores);

        //assertThat(resultado, equalTo(true));

        System.out.println(vehiculoObtenido.getPatente());
        System.out.println(vehiculo.getPatente());

        assertThat(vehiculoObtenido.getPatente(), equalTo(vehiculo.getPatente()));
    }

    //FUNCIONA OK
    @Test
    public void queSePuedanObtenerTodosLosVehiculosDelConductor(){
        //preparación

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        nuevoConductor.setId(1);

        System.out.println(nuevoConductor.getId());

        List<Vehiculo>vehiculos = new ArrayList<>();
        Vehiculo vehiculo1 = new Vehiculo("123ABC", "Rosa", "Vw", nuevoConductor.getId());
        Vehiculo vehiculo2 = new Vehiculo("789QRS", "Azul", "FIat", nuevoConductor.getId());
        Vehiculo vehiculo3 = new Vehiculo("456", "Violeta", "Renault", nuevoConductor.getId());

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        //ejecucion
        when(this.irepositoryVehiculo.obtenerTodosLosVehiculosDelConductor(nuevoConductor.getId())).thenReturn(vehiculos);

        List<Vehiculo> vehiculosObtenidosDelConductor = this.iservicioVehiculo.obtenerTodosLosVehiculosDelConductor(2);

        System.out.println(vehiculosObtenidosDelConductor.size());
        System.out.println(vehiculos.size());

        assertThat(vehiculosObtenidosDelConductor.size(), equalTo(vehiculos.size()));

        /*this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo1, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo2, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo3, nuevoConductor, conductores);
        this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo4, nuevoConductor, conductores);

        when(this.irepositoryVehiculo.get()).thenReturn(vehiculos);

        //verificación
        assertThat((this.iservicioVehiculo.get()).size(), equalTo(4));*/
    }

    //NO FUNCIONA OK
    /*@Test
    public void queAlBuscarVehiculosPorTipoDeVehiculoAutoDevuelvaLosVehiculosCorrespondientes(){

        //preparación

        IServicioVehiculo servicioVehiculoMock = mock(IServicioVehiculo.class);

       List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        //ejecucion

        conductores.add(nuevoConductor);

        List<Vehiculo> vehiculosMock = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo2 = new Vehiculo(4L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo3 = new Vehiculo(3L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo4 = new Vehiculo(2L, "DEF478", "Rojo", "Auto", TipoVehiculo.AUTO, 5000.0, 28.0, 6L);

        vehiculosMock.add(vehiculo1);
        vehiculosMock.add(vehiculo2);
        vehiculosMock.add(vehiculo3);
        vehiculosMock.add(vehiculo4);

        servicioVehiculoMock.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculosMock, vehiculo1, nuevoConductor, conductores);
        servicioVehiculoMock.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculosMock, vehiculo2, nuevoConductor, conductores);
        servicioVehiculoMock.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculosMock, vehiculo3, nuevoConductor, conductores);
        servicioVehiculoMock.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculosMock, vehiculo4, nuevoConductor, conductores);

        //ejecución

        //when(servicioVehiculoMock.getByTipoDeVehiculo(TipoVehiculo.AUTO)).thenReturn(vehiculosMock);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculoMock.getByTipoDeVehiculo(TipoVehiculo.AUTO);

        //verificación
        assertThat(vehiculosEncontrados.size(), equalTo(1));
    }*/
/*
    @Test
    public void queSePuedaModificarUnVehiculoDeLaListaDelConductor(){

        IServicioVehiculo servicioVehiculoMock = mock(IServicioVehiculo.class);

        List<Conductor>conductores = new ArrayList<>();

        Conductor nuevoConductor = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        conductores.add(nuevoConductor);

        List<Vehiculo> vehiculos = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo2 = new Vehiculo(4L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculo3 = new Vehiculo(3L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);
        Vehiculo vehiculoNuevo = new Vehiculo(2L, "DEF478", "Rojo", "Auto", TipoVehiculo.AUTO, 5000.0, 28.0, 6L);

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

       // this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo1, nuevoConductor, conductores);
       // this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo2, nuevoConductor, conductores);
       // this.iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo3, nuevoConductor, conductores);

        when(servicioVehiculoMock.modificarVehiculo(vehiculos, vehiculoNuevo, nuevoConductor, conductores)).thenReturn(true);

        Boolean resultado = servicioVehiculoMock.modificarVehiculo(vehiculos, vehiculoNuevo, nuevoConductor, conductores);

        //assertThat(resultado, equalTo(true));
        //Boolean resultado = this.iservicioVehiculo.modificarVehiculo(vehiculos, vehiculoNuevo, nuevoConductor, conductores);

        assertThat(resultado, equalTo(true));

    }*/

}
