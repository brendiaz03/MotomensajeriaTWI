package com.tallerwebi.models;

import com.tallerwebi.dominio.UsuarioDos;
import com.tallerwebi.dto.ConductorDto;

import javax.persistence.Entity;

@Entity
public class Conductor extends UsuarioDos {

    private Vehiculo vehiculo;

    public Conductor(ConductorDto conductorDto) {
        super(conductorDto.getEmail(), conductorDto.getPassword(), conductorDto.getNombre(), conductorDto.getApellido(), conductorDto.getNumeroDeDni(), conductorDto.getDomicilio(), conductorDto.getTelefono(), conductorDto.getUsuario());
    }

    public Conductor(Conductor conductorDto, Vehiculo vehiculo) {
        super(conductorDto.getEmail(), conductorDto.getPassword(), conductorDto.getNombre(), conductorDto.getApellido(), conductorDto.getNumeroDeDni(), conductorDto.getDomicilio(), conductorDto.getTelefono(), conductorDto.getUsuario());
        this.vehiculo = vehiculo;
    }

    public Conductor() {

    }
}