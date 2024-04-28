package com.tallerwebi.dominio.entidades.usuarios;

import com.tallerwebi.dominio.dto.ConductorDto;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;

import javax.persistence.Entity;

@Entity
public class Conductor extends UsuarioDos {

    private Long idVehiculo;
    private String cvu;

    public Conductor() {

    }

    public Conductor(ConductorDto conductorDto) {
        super(conductorDto.getEmail(), conductorDto.getPassword(), conductorDto.getNombre(), conductorDto.getApellido(), conductorDto.getNumeroDeDni(), conductorDto.getDomicilio(), conductorDto.getTelefono(), conductorDto.getUsuario());
        this.cvu = conductorDto.getCvu();
    }

    public Conductor(String email, String password){
        super(email, password);
    }

    public Conductor(Vehiculo vehiculo) {
        super();
        this.idVehiculo = vehiculo.getIdVehiculo();
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public void actualizarIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}