package com.tallerwebi.dominio.conductor;

import javax.persistence.*;

@Entity
@Table(name = "Conductor")
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "apellido", length = 50)
    private String apellido;

    @Column(name = "numeroDeDni")
    private Integer numeroDeDni;

    @Column(name = "domicilio", length = 50)
    private String domicilio;

    @Column(name = "nroTelefono")
    private Integer nroTelefono;

    @Column(name = "nombreUsuario", length = 50)
    private String nombreUsuario;

    @Column(name = "cvu", length = 50)
    private String cvu;

    public Conductor(String email, String password, String nombre, String apellido, String domicilio, Integer numeroDeDni, Integer nroTelefono, String nombreUsuario, String cvu) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.numeroDeDni = numeroDeDni;
        this.nroTelefono = nroTelefono;
        this.nombreUsuario = nombreUsuario;
        this.cvu = cvu;
    }
    public Conductor() {}

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

    public Integer getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(Integer nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
