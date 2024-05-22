package com.tallerwebi.dominio.cliente;

import javax.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private Integer numeroDeDni;

    @Column(nullable = false, length = 100)
    private String domicilio;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String nombreUsuario;

    public Cliente(String email, String password, String nombre, String apellido, Integer numeroDeDni, String domicilio, String telefono, String nombreUsuario) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeDni = numeroDeDni;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
    }

    public Cliente() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
