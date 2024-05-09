package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Datos.DatosViaje;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.dominio.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ViajeServiceImpl implements ViajeService {

    private ViajeRepository viajeRepository;

    @Autowired
    public ViajeServiceImpl(ViajeRepository viajeRepository){
        this.viajeRepository = viajeRepository;
    }

    @Override
    @Transactional
    public List<Viaje> obtenerViajes() {
        return this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos();
    }

    @Override
    public Viaje obtenerViajePorIdPaquete(Integer idPaquete) {
        return this.viajeRepository.ObtenerViajePorIdPaquete(idPaquete);
    }

    @Override
    public Viaje obtenerViajePorId(Integer id) {
        return this.viajeRepository.obtenerViajePorId(id);
    }

    @Override
    @Transactional
    public List<Viaje> obtenerViajesPorIdCliente(Integer idCliente) {
        return this.viajeRepository.obtenerLosViajesDeUnCliente(idCliente);
    }

    @Override
    @Transactional
    public List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente) {
        return this.viajeRepository.obtenerLosViajesAceptadosPorElConductor(idCliente);
    }
}