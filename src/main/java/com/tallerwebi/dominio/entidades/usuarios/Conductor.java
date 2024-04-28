package com.tallerwebi.dominio.entidades.usuarios;

import com.tallerwebi.dominio.dto.ConductorDto;
import com.tallerwebi.dominio.entidades.vehiculos.Vehiculo;

import javax.persistence.Entity;

@Entity
public class Conductor extends UsuarioDos {

    private Integer idVehiculo;
    private String cvu;

    public Conductor() {

    }

    public Conductor(String email, String password, String nombre, String apellido, Integer numeroDeDni, String domicilio, Integer telefono, String usuario, Integer idVehiculo, String cvu) {
        super(email, password, nombre, apellido, numeroDeDni, domicilio, telefono, usuario);
        this.idVehiculo = idVehiculo;
        this.cvu = cvu;
    }

    public Conductor(String email, String password){
        super(email, password);
    }

    public Conductor(Vehiculo vehiculo) {
        super();
        this.idVehiculo = vehiculo.getIdVehiculo();
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public void actualizarIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}