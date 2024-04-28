package com.tallerwebi.dominio.entidades.usuarios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class UsuarioDos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Boolean activo;
    private String nombre;
    private String apellido;
    private Integer numeroDeDni;
    private String domicilio;
    private Integer telefono;
    private String usuario;


    public UsuarioDos(String email, String password, String nombre, String apellido, Integer numeroDeDni, String domicilio, Integer telefono, String usuario) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeDni = numeroDeDni;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.usuario = usuario;
        this.activo = false;
    }

    public UsuarioDos() {

    }

    //Loguearse
    public UsuarioDos(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Registrarse
    public UsuarioDos(String nombre, String apellido, Integer numeroDeDni, String usuario, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeDni = numeroDeDni;
        this.usuario = usuario;
        this.email = email;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getNumeroDeDni() {
        return numeroDeDni;
    }

    public void setNumeroDeDni(Integer numeroDeDni) {
        this.numeroDeDni = numeroDeDni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}