package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.enums.TipoVehiculo;

import java.util.ArrayList;
import java.util.List;

public class ServicioVehiculoImpl implements IServicioVehiculo {

    //private List<Vehiculo> vehiculos;

   // private VehiculoRepository vehiculoRepository;

    public ServicioVehiculoImpl() {

        //this.vehiculoRepository = vehiculoRepository;

       /* this.vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo(1L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(2L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(3L, TipoVehiculo.MOTO));
*/
    }

    @Override
    public List<Vehiculo> get() {
        //return this.convertToVehiculoDto(this.vehiculos);
        return null;
    }
/*
    public List<Vehiculo> convertToVehiculo(List<Vehiculo> vehiculos){
        List<Vehiculo> Vehiculo = new ArrayList<>();
        for(Vehiculo vehiculo : vehiculos){
            Vehiculo.add(new Vehiculo(vehiculo));
        }
        return Vehiculo;
    }


    public List<Vehiculo> get(List<Vehiculo> vehiculos) {

        List<Vehiculo> datosVehiculos = new ArrayList<>();

        for(Vehiculo vehiculo : vehiculos){
            datosVehiculos.add(new Vehiculo(vehiculo));
        }

        return datosVehiculos;

    }*/


    @Override
    public List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo) {

        List<Vehiculo> vehiculos = new ArrayList<>();
        List<Vehiculo>vehiculosEncontrados = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo3 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        for(Vehiculo vehiculo : vehiculos) {

            if (vehiculo.getTipoDeVehiculo().equals(TipoVehiculo.AUTO)) {
                vehiculosEncontrados.add(vehiculo);
            }

        }
        return vehiculosEncontrados;
    }
}
