package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import java.util.List;

public interface IRepositoryVehiculo {

    List<Vehiculo> get();
    List<Vehiculo> getByTipoVehiculo(TipoVehiculo tipoVehiculo);

}
