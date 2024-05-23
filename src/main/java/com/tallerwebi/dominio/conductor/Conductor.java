package com.tallerwebi.dominio.conductor;

import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.dominio.viaje.Viaje;

import javax.persistence.*;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "conductor")
public class Conductor {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "numeroDeDni")
    private Integer numeroDeDni;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "nroTelefono")
    private String nroTelefono;
    @Column(name = "cvu")
    private String cvu;

    @Lob
    private byte[] imagenPerfil;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "conductor")
    private List<Viaje> viajes;

    @OneToOne
    @JoinColumn(name = "idVehiculo")
    private Vehiculo vehiculo;

    public Conductor(String nombre, String apellido, Integer numeroDeDni, String email, String password, String nombreUsuario, String domicilio, String nroTelefono, String cvu) {
        this.nombre=nombre;
        this.apellido=apellido;
        this.numeroDeDni=numeroDeDni;
        this.email=email;
        this.password=password;
        this.nombreUsuario=nombreUsuario;
        this.domicilio=domicilio;
        this.nroTelefono=nroTelefono;
        this.cvu=cvu;
    }

    public Conductor() {

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getNumeroDeDni() {
        return numeroDeDni;
    }

    public void setNumeroDeDni(Integer numeroDeDni) {
        this.numeroDeDni = numeroDeDni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(byte[] imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getImageDataBase64(){
        return Base64.getEncoder().encodeToString(Base64.getDecoder().decode(this.imagenPerfil));
    }

}
