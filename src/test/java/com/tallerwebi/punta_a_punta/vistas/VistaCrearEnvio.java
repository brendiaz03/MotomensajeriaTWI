package com.tallerwebi.punta_a_punta.vistas;//package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaCrearEnvio extends VistaWeb {

    public VistaCrearEnvio(Page page) {
        super(page);
        page.navigate("localhost:8080/login");
    }


    public String obtenerMensajeDeErrorPaqueteDestinatario(){
        return this.obtenerTextoDelElemento("#destinatarioError");
    }
    public String obtenerMensajeDeErrorPaqueteFragilidad(){
        return this.obtenerTextoDelElemento("#esFragilError");
    }

    public String obtenerMensajeDeErrorPaquetePeso(){
        return this.obtenerTextoDelElemento("#pesoError");
    }

    public String obtenerMensajeDeErrorPaqueteDimension(){        return this.obtenerTextoDelElemento("#dimensionError");
    }

    public String obtenerMensajeDeErrorViajeDireccionSalida() {
        return this.obtenerTextoDelElemento("#direccionSalidaError");
    }

    public String obtenerMensajeDeErrorViajeDireccionLlegada() {
            return this.obtenerTextoDelElemento("#direccionLlegadaError");
    }

    public void escribirNombreUsuario(String nombreUsuario){
        this.escribirEnElElemento("#usuario", nombreUsuario);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento(".btn-aceptar");
    }

    public void darClickEnCrearEnvio() {
        this.darClickEnElElemento(".crearEnvio");
    }

    public void escribirPesoPaquete(String peso) {
        this.escribirEnElElemento("#peso", peso);
    }

    public void escribirDimensionPaquete(String dimension) {
        this.escribirEnElElemento("#dimension", dimension);
    }

    public void escribirDescripcionPaquete(String descripcion) {
        this.escribirEnElElemento("#descripcion", descripcion);
    }

    public void escribirDestinatarioPaquete(String destinatario) {
        this.escribirEnElElemento("#destinatario", destinatario);
    }

    public void seleccionarFragilidadPaquete() {
        this.darClickEnElElemento("#esFragilSi");

    }

    public void darClickEnCrearPaquete() {
        this.darClickEnElElemento("#boton_crear_paquete");
    }

    public void escribirDireccionSalida(String direccion) {
        this.escribirEnElElemento("#direccionSalida", direccion);
        this.seleccionarPrimeraOpcionAutocompletado();
    }

    public void escribirDireccionLlegada(String direccion) {
        this.escribirEnElElemento("#direccionLlegada", direccion);
        this.seleccionarPrimeraOpcionAutocompletado();
    }

    public void darClickEnCrearViaje() {
        this.darClickEnElElemento("#boton_crear_viaje");
    }
    public void darClickEnIconoParaEditarPaquete() {
        this.darClickEnElElemento("#boton_ir_editar_paquete");
    }
    public void darClickEnEditarPaquete() {
        this.darClickEnElElemento("#boton_editar_paquete");
    }
    public void darClickEnPagarEnvio() {
        this.darClickEnElElemento("#boton_pagar_envio");
    }
    public void volverAEscribirPesoPaquete(String peso) {
        this.volverAEscribirEnElElemento("#peso", peso);
    }
    public void darClickEnBotonCancelar() {
        this.darClickEnElElemento(".salir");
    }}


