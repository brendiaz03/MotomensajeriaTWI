package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Datos.DatosViaje;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.dominio.ViajeRepository;
import com.tallerwebi.dominio.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeServiceImpl implements ViajeService {

    private ViajeRepository viajeRepository;

    @Autowired
    public ViajeServiceImpl(ViajeRepository viajeRepository){
        this.viajeRepository = viajeRepository;
    }

    @Override
    public List<Viaje> obtenerTodosLosViajesDeLaBaseDeDatos() {
        return this.viajeRepository.obtenerTodosLosViajesDeLaBaseDeDatos();
    }

    @Override
    public List<DatosViaje> obtenerLosViajesAceptadosPorElConductor(Integer idCliente) {
        return this.viajeRepository.obtenerLosViajesAceptadosPorElConductor(idCliente);
    }

    @Override
    public List<Viaje> obtenerLasSolicitudesDeViajesPendientes() {
        List<Viaje> viajesObtenidos = this.viajeRepository.obtenerLasSolicitudesDeViajesPendientes();
        return viajesObtenidos;
    }

    @Override
    public String actualizarViajeConElIdDelConductorQueAceptoElViaje(Integer idViaje, Integer idConductor) {
        Boolean resultado = this.viajeRepository.actualizarViajeAceptadoPorElConductor(idViaje, idConductor);
        String mensaje = "No se pudo aceptar el viaje";
        if(resultado){
            mensaje = "VIAJE ACEPTADO!";
        }

        return mensaje;
    }
}