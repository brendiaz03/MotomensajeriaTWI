package com.tallerwebi.dominio.dto;

public class ConductorYVehiculoDto {

    private ConductorDto conductorDto;
    private VehiculoDto vehiculoDto;

    public ConductorDto getConductorDto() {
        return conductorDto;
    }

    public void setConductorDto(ConductorDto conductorDto) {
        this.conductorDto = conductorDto;
    }

    public VehiculoDto getVehiculoDto() {
        return vehiculoDto;
    }

    public void setVehiculoDto(VehiculoDto vehiculoDto) {
        this.vehiculoDto = vehiculoDto;
    }
}