package com.tallerwebi.dominio.conductor;

import javax.persistence.*;

@Entity
public class Conductor {

    private String nombre;
    private String apellido;
    private Integer numeroDeDni;
    private String email;
    private String password;
    private String nombreUsuario;
    private String domicilio;
    private String nroTelefono;
    private String cvu;

    @Lob
    private byte[] imagenPerfil;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



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

    public Conductor(String email, String password, String nombre, String apellido, Integer numeroDeDni) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeDni = numeroDeDni;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public byte[] getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(byte[] imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
