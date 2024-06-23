package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;
import com.tallerwebi.dominio.viaje.ViajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConductorServicioImpl implements ConductorServicio {

    private final ConductorRepositorio conductorRepositorio;
    private final ViajeRepositorio viajeRepositorio;

    @Autowired
    public ConductorServicioImpl(ConductorRepositorio conductorRepositorio, ViajeRepositorio viajeRepositorio) {
        this.conductorRepositorio = conductorRepositorio;
        this.viajeRepositorio=viajeRepositorio;
    }


    @Override
    public Conductor obtenerConductorPorId(Integer id) throws UsuarioNoEncontradoException {
        try{
            return this.conductorRepositorio.buscarConductorPorId(id);
        }catch(NoResultException e){
            throw new UsuarioNoEncontradoException("Conductor no Encontrado");
        }
    }

    @Override
    public Boolean RelacionarVehiculoAConductor(Integer idConductor, Vehiculo vehiculo) throws UsuarioNoEncontradoException {
        try{
            Conductor conductor = this.obtenerConductorPorId(idConductor);
            conductor.setVehiculo(vehiculo);
            conductorRepositorio.editarConductor(conductor);
            return true;
        }catch(NoResultException e){
            throw new UsuarioNoEncontradoException("No se pudo realizar la relación entre el Vehiculo y el Conductor ya que el Conductor no existe.");
        }
    }

    @Override
    public Boolean estaPenalizado(Conductor conductor, List<Viaje> cantidadDescartados) {
        conductor.setCantPenalizacion((conductor.getCantPenalizacion())+1);
        this.conductorRepositorio.editarConductor(conductor);
        if(conductor.getCantPenalizacion()>=2){
            conductor.setPenalizado(true);
            conductor.setHoraPenalizacion(LocalDateTime.now());
            this.conductorRepositorio.editarConductor(conductor);
            for(Viaje descartado:cantidadDescartados){
                this.viajeRepositorio.editar(descartado);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void editarConductor(Conductor conductor) {
        this.conductorRepositorio.editarConductor(conductor);
    }

    @Override
    public void despenalizarConductor(Conductor conductor) {
        conductor.setPenalizado(false);
        conductor.setHoraPenalizacion(null);
        conductor.setCantPenalizacion(0);
        this.editarConductor(conductor);
    }


}
