package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Conductor extends Usuario {


    @Column(name = "isPenalizado")
    private Boolean isPenalizado;
    @Column(name = "horaPenalizacion")
    private LocalDateTime horaPenalizacion;

    @Column(name = "cantPenalizacion")
    private Integer cantPenalizacion;

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


    public Boolean getPenalizado() {
        return isPenalizado;
    }

    public void setPenalizado(Boolean penalizado) {
        isPenalizado = penalizado;
    }

    public LocalDateTime getHoraPenalizacion() {
        return horaPenalizacion;
    }

    public void setHoraPenalizacion(LocalDateTime horaPenalizacion) {
        this.horaPenalizacion = horaPenalizacion;
    }

    public Integer getCantPenalizacion() {
        return cantPenalizacion;
    }

    public void setCantPenalizacion(Integer cantPenalizacion) {
        this.cantPenalizacion = cantPenalizacion;
    }
}