package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaRegistrarUsuario extends VistaWeb{

    public VistaRegistrarUsuario(Page page) {
        super(page);
        page.navigate("localhost:8080/login");
    }

    public void darClickEnRegistrarse() {
        this.darClickEnElElemento("#boton_reg");
    }

    public void escribirNombre(String nombre) {
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void escribirApellido(String apellido) {
        this.escribirEnElElemento("#apellido", apellido);

    }

    public void escribirDomicilio(String domicilio) {
        this.escribirEnElElemento("#domicilio", domicilio);

    }

    public void escribirNumeroDeTelefono(String nroTelefono) {
        this.escribirEnElElemento("#nroTelefono", nroTelefono);
    }

    public void escribirNumeroDeDni(String numeroDeDni) {
        this.escribirEnElElemento("#numeroDeDni", numeroDeDni);
    }

    public void escribirNombreDeUsuario(String nombreUsuario) {
        this.escribirEnElElemento("#nombreUsuario", nombreUsuario);

    }

    public void escribirEmail(String email) {
        this.escribirEnElElemento("#email", email);

    }

    public void escribirContrasenia(String password) {
        this.escribirEnElElemento("#password", password);

    }

    public void escribirConfirmarContrasenia(String confirmarPassword) {
        this.escribirEnElElemento("#confirmarPassword", confirmarPassword);

    }

    public void seleccionarTipoDeUsuario(String tipoUsuario) {
        this.seleccionarElementoDeEnum("#tipoUsuario",tipoUsuario);
    }

    public void darClickEnRegistrarUsuario() {
        this.darClickEnElElemento("#boton_reg_usuario");
    }

    public void seleccionarTipoDeVehiculo(String tipoDeVehiculo) {
        this.seleccionarElementoDeEnum("#tipoVehiculo",tipoDeVehiculo);

    }

    public void seleccionarModeloDeVehiculo(String modelo) {
        this.seleccionarElementoDeEnum("#modelo",modelo);

    }

    public void seleccionarColorDeVehiculo(String color) {
        this.seleccionarElementoDeEnum("#color",color);

    }

    public void escribirPatente(String patente) {
        this.escribirEnElElemento("#patente", patente);
    }

    public void escribirPesoSoportado(String pesoSoportado) {
        this.escribirEnElElemento("#pesoSoportado", pesoSoportado);

    }

    public void escribirDimensionDisponible(String dimensionDisponible) {
        this.escribirEnElElemento("#dimensionDisponible", dimensionDisponible);

    }

    public void darClickEnRegistrarVehiculo() {
        this.darClickEnElElemento("#boton_reg_vehiculo");
    }
}
