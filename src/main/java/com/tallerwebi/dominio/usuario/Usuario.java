package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.viaje.Viaje;

import javax.persistence.*;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario")
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "numeroDeDni")
    private Integer numeroDeDni;

    @Column(name = "email")
    private String email;

    @Column(name = "numeroTelefono")
    private String numeroDeTelefono;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @Column(name = "password")
    private String password;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "tipoUsuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "eliminado")
    private Boolean eliminado;

    @Lob
    private byte[] imagenPerfil;

    @OneToMany(mappedBy = "cliente")
    private List<Viaje> viajes;

    public Usuario() {

    }

    public Usuario(String nombre, String apellido, Integer numeroDeDni, String email, String numeroDeTelefono, String nombreUsuario, String password, String domicilio, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDeDni = numeroDeDni;
        this.email = email;
        this.numeroDeTelefono = numeroDeTelefono;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.domicilio = domicilio;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreDeUsuario) {
        this.nombreUsuario = nombreDeUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
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

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
