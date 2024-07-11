package com.tallerwebi.punta_a_punta;//package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaCrearEnvio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
public class VistaCrearEnvioE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaCrearEnvio vistaCrearEnvio;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(800));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaCrearEnvio = new VistaCrearEnvio(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void unUsuarioClienteIniciaSesionYCreaUnEnvioExitosamente() {
        vistaCrearEnvio.escribirNombreUsuario("bren");
        vistaCrearEnvio.escribirClave("4215");
        vistaCrearEnvio.darClickEnIniciarSesion();
        vistaCrearEnvio.darClickEnCrearEnvio();
        vistaCrearEnvio.escribirPesoPaquete("10.0");
        vistaCrearEnvio.escribirDimensionPaquete("25.0");
        vistaCrearEnvio.escribirDescripcionPaquete("Paquete fragil");
        vistaCrearEnvio.escribirDestinatarioPaquete("Son Gohan");
        vistaCrearEnvio.seleccionarFragilidadPaquete();
        vistaCrearEnvio.darClickEnCrearPaquete();
        vistaCrearEnvio.escribirDireccionSalida("Pueyrredon 3339");
        vistaCrearEnvio.escribirDireccionLlegada("Cerrito 2345");
        vistaCrearEnvio.darClickEnCrearViaje();
        vistaCrearEnvio.darClickEnIconoParaEditarPaquete();
        vistaCrearEnvio.volverAEscribirPesoPaquete("25.0");
        vistaCrearEnvio.darClickEnEditarPaquete();
        vistaCrearEnvio.darClickEnPagarEnvio();

        String url = vistaCrearEnvio.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/home-cliente"));
    }

    @Test
    void unUsuarioClienteIniciaSesionYCancelaLaCreacionEnElTercerPaso() {
        vistaCrearEnvio.escribirNombreUsuario("bren");
        vistaCrearEnvio.escribirClave("4215");
        vistaCrearEnvio.darClickEnIniciarSesion();
        vistaCrearEnvio.darClickEnCrearEnvio();
        vistaCrearEnvio.escribirPesoPaquete("10.0");
        vistaCrearEnvio.escribirDimensionPaquete("25.0");
        vistaCrearEnvio.escribirDescripcionPaquete("Paquete fragil");
        vistaCrearEnvio.escribirDestinatarioPaquete("Son Gohan");
        vistaCrearEnvio.seleccionarFragilidadPaquete();
        vistaCrearEnvio.darClickEnCrearPaquete();
        vistaCrearEnvio.escribirDireccionSalida("Pueyrredon 3339");
        vistaCrearEnvio.escribirDireccionLlegada("Cerrito 2345");
        vistaCrearEnvio.darClickEnCrearViaje();
        vistaCrearEnvio.darClickEnBotonCancelar();

        String url = vistaCrearEnvio.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/home-cliente"));
    }

    @Test
    void unUsuarioClienteIniciaSesionYSeDirigeACrearPaqueteYEnviaElFormularioVacioPorEndeSalenTodosLosErroresDeValidacion() {
        vistaCrearEnvio.escribirNombreUsuario("bren");
        vistaCrearEnvio.escribirClave("4215");
        vistaCrearEnvio.darClickEnIniciarSesion();
        vistaCrearEnvio.darClickEnCrearEnvio();
        vistaCrearEnvio.darClickEnCrearPaquete();


        String pesoError = vistaCrearEnvio.obtenerMensajeDeErrorPaquetePeso();
        String dimensionError = vistaCrearEnvio.obtenerMensajeDeErrorPaqueteDimension();
        String esFragilError = vistaCrearEnvio.obtenerMensajeDeErrorPaqueteFragilidad();
        String destinatarioError = vistaCrearEnvio.obtenerMensajeDeErrorPaqueteDestinatario();


        assertThat("Ingrese un peso válido (mayor a 0).", containsStringIgnoringCase(pesoError));
        assertThat("Seleccione si el paquete es frágil o no.", containsStringIgnoringCase(esFragilError));
        assertThat("Ingrese una dimensión válida (mayor a 0).", containsStringIgnoringCase(dimensionError));
        assertThat("Ingrese el nombre del destinatario.", containsStringIgnoringCase(destinatarioError));

    }

    @Test
    void unUsuarioClienteIniciaSesionYCreaUnPaqueteYEnviaElFormularioDeViajeVacioPorEndeSalenTodosLosErroresDeValidacion() {
        vistaCrearEnvio.escribirNombreUsuario("bren");
        vistaCrearEnvio.escribirClave("4215");
        vistaCrearEnvio.darClickEnIniciarSesion();
        vistaCrearEnvio.darClickEnCrearEnvio();
        vistaCrearEnvio.escribirPesoPaquete("10.0");
        vistaCrearEnvio.escribirDimensionPaquete("25.0");
        vistaCrearEnvio.escribirDescripcionPaquete("Paquete fragil");
        vistaCrearEnvio.escribirDestinatarioPaquete("Son Gohan");
        vistaCrearEnvio.seleccionarFragilidadPaquete();
        vistaCrearEnvio.darClickEnCrearPaquete();
        vistaCrearEnvio.darClickEnCrearViaje();

        String dirreccionSalidaError = vistaCrearEnvio.obtenerMensajeDeErrorViajeDireccionSalida();
        String dirreccionLlegadaError = vistaCrearEnvio.obtenerMensajeDeErrorViajeDireccionLlegada();

        assertThat("Debe seleccionar una dirección de salida válida del autocompletado.", containsStringIgnoringCase(dirreccionSalidaError));
        assertThat("Debe seleccionar una dirección de llegada válida del autocompletado.", containsStringIgnoringCase(dirreccionLlegadaError));
    }
}