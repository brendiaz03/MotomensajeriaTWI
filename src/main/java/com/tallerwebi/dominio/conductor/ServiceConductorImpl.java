package com.tallerwebi.dominio.conductor;

import com.tallerwebi.infraestructura.RepositoryConductorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceConductorImpl implements IServiceConductor {

    private final IRepositoryConductor iRepositoryConductor;

    @Autowired
    public ServiceConductorImpl(IRepositoryConductor iRepositoryConductor) {
        this.iRepositoryConductor = iRepositoryConductor;
    }

    @Override
    public Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws  ConductorDuplicadoException {
        try{
            this.iRepositoryConductor.buscarDuplicados(nuevoConductor.getEmail(),nuevoConductor.getNombreUsuario());
            throw new ConductorDuplicadoException("E-mail o Usuario Duplicado");
        }catch(NoResultException e){
            this.iRepositoryConductor.registrar(nuevoConductor);
            return true;
        }
}

    @Override
    public Conductor obtenerConductorPorId(Integer id) throws ConductorNoEncontradoException {
        try{
            return this.iRepositoryConductor.buscarConductor(id);
        }catch(NoResultException e){
            throw new ConductorNoEncontradoException("Usuario no Encontrado");
        }
    }

    @Override
    public void editarConductor(Conductor conductorEditado) {
            this.iRepositoryConductor.editarConductor(conductorEditado);
    }

    @Override
    public void borrarConductor(Integer idusuario) {
        Conductor conductorABorrar= this.iRepositoryConductor.buscarConductor(idusuario);
        this.iRepositoryConductor.borrarConductor(conductorABorrar);
    }

}
