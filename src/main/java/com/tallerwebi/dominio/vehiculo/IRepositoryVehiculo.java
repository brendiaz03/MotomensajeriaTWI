package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositoryVehiculo {

    List<Vehiculo> get();
    List<Vehiculo> getByTipoVehiculo(TipoVehiculo tipoVehiculo);

}
