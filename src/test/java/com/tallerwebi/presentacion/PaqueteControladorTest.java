package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.paquete.PaqueteServicio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

public class PaqueteControladorTest {

    private HttpSession httpSession;

    private PaqueteControlador paqueteControlador;

    private PaqueteServicio paqueteServicio;

    @BeforeEach
    public void init(){

        this.httpSession = mock(HttpSession.class);

        this.paqueteControlador = new PaqueteControlador(paqueteServicio);

        this.paqueteServicio = mock(PaqueteServicio.class);

    }

    //@Test
    //public void queSePuedaMostrarElFormDelPaquete(){


    //}



}
