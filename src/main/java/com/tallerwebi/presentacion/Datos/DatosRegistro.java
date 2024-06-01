package com.tallerwebi.presentacion.Datos;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoUsuario;

public class DatosRegistro {

    private String nombre;
    private String apellido;
    private Integer numeroDeDni;
    private String email;
    private String numeroDeTelefono;
    private String nombreUsuario;
    private String password;
    private String domicilio;
    private TipoUsuario tipoUsuario;

    public DatosRegistro(String nombre, String apellido, Integer numeroDeDni, String email, String numeroDeTelefono, String nombreUsuario, String password, String domicilio, TipoUsuario tipoUsuario) {
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

    public DatosRegistro() {
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

    public Conductor toConductor(DatosRegistro datosConductor) {
        Conductor conductor = new Conductor();
        conductor.setNombre(datosConductor.getNombre());
        conductor.setApellido(datosConductor.getApellido());
        conductor.setNumeroDeDni(datosConductor.getNumeroDeDni());
        conductor.setEmail(datosConductor.getEmail());
        conductor.setNumeroDeTelefono(datosConductor.getNumeroDeTelefono());
        conductor.setNombreUsuario(datosConductor.getNombreUsuario());
        conductor.setPassword(datosConductor.getPassword());
        conductor.setDomicilio(datosConductor.getDomicilio());
        conductor.setTipoUsuario(datosConductor.getTipoUsuario());
        return conductor;
    }

    public Cliente toCliente(DatosRegistro datosCliente) {
        Cliente cliente = new Cliente();
        cliente.setNombre(datosCliente.getNombre());
        cliente.setApellido(datosCliente.getApellido());
        cliente.setNumeroDeDni(datosCliente.getNumeroDeDni());
        cliente.setEmail(datosCliente.getEmail());
        cliente.setNumeroDeTelefono(datosCliente.getNumeroDeTelefono());
        cliente.setNombreUsuario(datosCliente.getNombreUsuario());
        cliente.setPassword(datosCliente.getPassword());
        cliente.setDomicilio(datosCliente.getDomicilio());
        cliente.setTipoUsuario(datosCliente.getTipoUsuario());
        return cliente;
    }
}
