package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ConductorRepository;
import com.tallerwebi.dominio.ConductorService;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConductorServiceImpl implements ConductorService {

    private ConductorRepository conductorRepository;

    @Autowired
    public ConductorServiceImpl(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @Override
    public Conductor buscarConductorPorEmail(String email) {
        return this.conductorRepository.buscarConductorPorEmail(email);
    }

    @Override
    public void actualizarInfoDelConductor(Conductor conductorActual) {
        this.conductorRepository.actualizarInfoDelConductor(conductorActual);
    }

    @Override
    public void guardarVehiculo(Vehiculo vehiculo) {
        this.conductorRepository.guardarVehiculo(vehiculo);
    }
}
