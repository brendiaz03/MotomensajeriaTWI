package com.tallerwebi.presentacion.Datos;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.usuario.Usuario;

import javax.persistence.Lob;

public class DatosUsuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private Integer numeroDeDni;
    private String email;
    private String numeroDeTelefono;
    private String nombreUsuario;
    private String password;
    private String domicilio;
    private TipoUsuario tipoUsuario;

    public Integer getId() {
        return id;
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

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Conductor toConductor() {
        Conductor conductor = new Conductor();
        conductor.setId(this.id);
        conductor.setNombre(this.getNombre());
        conductor.setApellido(this.getApellido());
        conductor.setNumeroDeDni(this.getNumeroDeDni());
        conductor.setEmail(this.getEmail());
        conductor.setNumeroDeTelefono(this.getNumeroDeTelefono());
        conductor.setNombreUsuario(this.getNombreUsuario());
        conductor.setPassword(this.getPassword());
        conductor.setDomicilio(this.getDomicilio());
        conductor.setTipoUsuario(this.getTipoUsuario());
        conductor.setTipoUsuario(this.getTipoUsuario());
        return conductor;
    }

    public Cliente toCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNombre(this.getNombre());
        cliente.setApellido(this.getApellido());
        cliente.setNumeroDeDni(this.getNumeroDeDni());
        cliente.setEmail(this.getEmail());
        cliente.setNumeroDeTelefono(this.getNumeroDeTelefono());
        cliente.setNombreUsuario(this.getNombreUsuario());
        cliente.setPassword(this.getPassword());
        cliente.setDomicilio(this.getDomicilio());
        cliente.setTipoUsuario(this.getTipoUsuario());
        return cliente;
    }

    public DatosUsuario usuarioToDTO(Usuario usuario){
        DatosUsuario datosUsuario = new DatosUsuario();
        datosUsuario.setNombre(usuario.getNombre());
        datosUsuario.setApellido(usuario.getApellido());
        datosUsuario.setNumeroDeDni(usuario.getNumeroDeDni());
        datosUsuario.setEmail(usuario.getEmail());
        datosUsuario.setNumeroDeTelefono(usuario.getNumeroDeTelefono());
        datosUsuario.setNombreUsuario(usuario.getNombreUsuario());
        datosUsuario.setPassword(usuario.getPassword());
        datosUsuario.setDomicilio(usuario.getDomicilio());
        datosUsuario.setTipoUsuario(usuario.getTipoUsuario());
        return datosUsuario;
    }
}
