package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IRepositoryVehiculo {

    void registrarVehiculo(Vehiculo vehiculo);
    Vehiculo buscarVehiculoPorPatente(String patente);

}
