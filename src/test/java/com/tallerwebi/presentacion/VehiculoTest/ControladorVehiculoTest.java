package com.tallerwebi.presentacion.VehiculoTest;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.ServicioVehiculoImpl;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.infraestructura.RepositoryVehiculoImpl;
import com.tallerwebi.presentacion.vehiculo.ControladorVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Nested
class ControladorVehiculoTest {

    //private static final Logger log = LoggerFactory.getLogger(ControladorVehiculoTest.class);

    private ControladorVehiculo controladorVehiculo;

    private IServicioVehiculo iservicioVehiculo;

    //private IRepositoryVehiculo irepositoryVehiculo;

    private List<Vehiculo> vehiculos = new ArrayList<>();

    @BeforeEach
    public void init() {
        //this.irepositoryVehiculo = new RepositoryVehiculoImpl();
        //this.iservicioVehiculo = new ServicioVehiculoImpl(this.irepositoryVehiculo);

        this.iservicioVehiculo = mock(IServicioVehiculo.class);

        this.controladorVehiculo = new ControladorVehiculo(this.iservicioVehiculo);
    }

    //FUNCIONA OK
    @Test
    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor(){

        ModelAndView mav= this.controladorVehiculo.mostrarRegistroDelVehiculo();
        String message = mav.getModel().get("message").toString();
        assertThat(mav.getViewName(),equalToIgnoringCase("registro-vehiculo"));
        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo"));
    }

    //FUNCIONA OK
    @Test
    public void queSePuedaAgregarUnVehiculo() {
        // Preparación
      Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        // Ejecución
        ModelAndView mav = controladorVehiculo.mostrarRegistroDelVehiculo();
        String message = mav.getModel().get("message").toString();

        vehiculos.add(vehiculo1);

        when(this.iservicioVehiculo.get()).thenReturn(vehiculos);

        // Verificación
        assertThat(vehiculos.size(), equalTo(1));
        assertThat(mav.getViewName(), equalToIgnoringCase("registro-vehiculo"));
        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo"));
    }

    //FUNCIONA OK
    @Test
    public void queAlIngresarAlaPantallaDelVehiculoMeMuestreTodosLosVehiculos() {
        // Preparación

        List<Vehiculo> vehiculos = dadoQueExistenVehiculos();

        mockearVehiculos(vehiculos);

        // Ejecución

        List<Vehiculo>vehiculosObtenidos = this.controladorVehiculo.obtenerTodosLosVehiculos();

        // Verificación

        assertThat(vehiculosObtenidos.size(), equalTo(vehiculos.size()));
    }

    private void mockearVehiculos(List<Vehiculo> vehiculos) {
        when(this.iservicioVehiculo.obtenerTodosLosVehiculos()).thenReturn(vehiculos);
    }

    private static List<Vehiculo> dadoQueExistenVehiculos() {
        List<Vehiculo>vehiculos = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo("123ABC", "Rosa", "Vw");
        Vehiculo vehiculo2 = new Vehiculo("789QRS", "Azul", "FIat");
        Vehiculo vehiculo3 = new Vehiculo("456", "Violeta", "Renault");

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);
        return vehiculos;
    }

}


