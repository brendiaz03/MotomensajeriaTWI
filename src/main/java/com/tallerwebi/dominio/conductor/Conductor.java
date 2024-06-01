package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conductor extends Usuario {

    @Column(name = "cvu")
    private String cvu;

    @OneToMany(mappedBy = "conductor")
    private List<Viaje> viajes;

    @OneToOne
    @JoinColumn(name = "idVehiculo")
    private Vehiculo vehiculo;

    public Conductor() {

    }

    public Conductor(String nombre, String apellido, Integer numeroDeDni, String email, String numeroDeTelefono, String nombreUsuario, String password, String domicilio, TipoUsuario tipoUsuario) {
        super(nombre, apellido, numeroDeDni, email, numeroDeTelefono, nombreUsuario, password, domicilio, tipoUsuario);
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }
}