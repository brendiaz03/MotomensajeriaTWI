/*package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.dominio.Viaje;
import com.tallerwebi.punta_a_punta.vistas.VistaViaje;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class VistaViajeTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaViaje vistaViaje;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaViaje = new VistaViaje(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    public void queElTituloDigaViajes(){
        String titulo = this.vistaViaje.obtenerTitulo();
        assertThat(titulo, equalToIgnoringCase("viajes"));
    }


    // VER DESPUES
    /*

    @Test
    public void queLaDireccionDeSalidaDelViajeDigaMiami(){
        String direccionDeSalida = this.vistaViaje.obtenerDireccionDeSalidaDelViaje();
        assertThat(direccionDeSalida, equalToIgnoringCase("Miami"));
    }

    @Test
    public void queLaDireccionDeLlegadaDelViajeDigaMiami(){
        String direccionDeLlegada = this.vistaViaje.obtenerDireccionDeLlegadaDelViaje();
        assertThat(direccionDeLlegada, equalToIgnoringCase("New York"));
    }

    @Test
    public void queElNombreDelClienteDigaJoaquin(){
        String nombreEsperado = this.vistaViaje.obtenerNombreDelCliente();
        assertThat(nombreEsperado, equalToIgnoringCase("Joaquin"));
    }

    @Test
    public void queSeMuestrenLosViajesConLasDireccionesDeSalidaYLlegada(){
        Viaje viaje1 = new Viaje("Miami", "New York");
        Viaje viaje2 = new Viaje("Argentina", "Brasil");
        List<Viaje> viajes = new ArrayList<>();
        viajes.add(viaje1);
        viajes.add(viaje2);

        String nombreEsperado = this.vistaViaje.obtenerNombreDelCliente();
        assertThat(nombreEsperado, equalToIgnoringCase("Joaquin"));
    }

}*/