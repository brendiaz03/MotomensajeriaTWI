package com.tallerwebi.dominio.Vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;
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

    private IServicioVehiculo servicioVehiculo;

    @BeforeEach
    public void init(){
        this.servicioVehiculo = new ServicioVehiculoImpl();
    }

    @Test
    public void queSePuedanObtenerTodosLosVehiculos(){
        //preparación

        List<Vehiculo> vehiculos = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo3 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);


        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        //ejecución

        List<Vehiculo> vehiculosEncontrados = this.servicioVehiculo.getByTipoDeVehiculo(TipoVehiculo.AUTO);

        //verificación
        assertThat(vehiculosEncontrados.size(), equalTo(3));
    }

    @Test
    public void queAlBuscarVehiculosPorTipoDeVehiculoAutoDevuelvaLosVehiculosCorrespondientes(){

        List<Vehiculo> vehiculos = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo3 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);


        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        //ejecución

        List<Vehiculo> vehiculosEncontrados = this.servicioVehiculo.getByTipoDeVehiculo(TipoVehiculo.AUTO);

        //verificación
        assertThat(vehiculosEncontrados.size(), equalTo(2));

    }

}
