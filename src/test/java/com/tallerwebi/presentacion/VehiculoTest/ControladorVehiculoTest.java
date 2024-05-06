package com.tallerwebi.presentacion.VehiculoTest;

import com.tallerwebi.dominio.vehiculo.ServicioVehiculoImpl;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.vehiculo.ControladorVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;

public class ControladorVehiculoTest {

    private ControladorVehiculo controladorVehiculo;

    @BeforeEach
    public void init() {
        ServicioVehiculoImpl servicioVehiculo = new ServicioVehiculoImpl();
        this.controladorVehiculo = new ControladorVehiculo(servicioVehiculo);
    }

    @Test
    public void queSePuedaAgregarUnVehiculo(){
        //preparación
        // ControladorVehiculo controladorVehiculo = new ControladorVehiculo(this.crearLista());

        //ejecución
        ModelAndView mav = controladorVehiculo.irAlVehiculo();
        String message = mav.getModel().get("message").toString();

        //verificación
        /*Verifica que la vista sea la correcta y que la información que acompaña a esa vista sea la correcta.*/
        assertThat(mav.getViewName(), equalToIgnoringCase("vehiculo")); //Verifica que la vista se la correcta
        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo")); //Verifica que se muestre un mensaje de bienvenida
    }

    @Test
    public void queAlIngresarAlaPantallaDelVehiculoMeMuestreTodosLosVehiculos() {
        //preparación
        //ControladorVehiculo controladorVehiculo = new ControladorVehiculo();
        //ControladorVehiculo controladorVehiculo = new ControladorVehiculo(this.crearLista());

        //ejecución
        ModelAndView mav = controladorVehiculo.irAlVehiculo();

        //verificación
        List<Vehiculo> vehiculos = (List<Vehiculo>) mav.getModel().get("vehiculos");
        assertThat(vehiculos.size(), equalTo(3));
    }

  /*@Test
    public void queAlBuscarVehiculosObtengaLosDeTipoAuto() {
        // Preparación
        //ControladorVehiculo controladorVehiculo = new ControladorVehiculo(this.crearLista2());

        // Ejecución
        ModelAndView mav = controladorVehiculo.buscarVehiculos(AUTO);

        // Verificación
        List<VehiculoDto> vehiculos = (List<VehiculoDto>)mav.getModel().get("vehiculos");

       assertThat(mav.getViewName(), equalToIgnoringCase("buscar-vehiculos")); //Verifica que la vista se la correcta
       assertThat(vehiculos.size(), equalTo(2));
       assertThat(vehiculos.get(0).getTipoVehiculo(), equalTo(AUTO));
    }*/

/*
    private List<VehiculoDto> crearLista(){

        List<VehiculoDto> vehiculos= new ArrayList<VehiculoDto>();
        vehiculos.add(new VehiculoDto());
        vehiculos.add(new VehiculoDto());
        vehiculos.add(new VehiculoDto());
        return vehiculos;
    }

    private List<VehiculoDto> crearLista2(){

        List<VehiculoDto> vehiculos= new ArrayList<VehiculoDto>();

        VehiculoDto vehiculo = new VehiculoDto("Fiat", "Duna", "ABC123", "Rojo", 100.05, 25.15, 10.10, 15.00, TipoVehiculo.AUTO);
        VehiculoDto vehiculo2 = new VehiculoDto("Ford", "Fiesta", "ABC122", "Turquesa", 100.05, 25.15, 10.10, 15.00, TipoVehiculo.AUTO);
        VehiculoDto vehiculo3 = new VehiculoDto("MarcaMoto", "ModeloMoto", "ABC456", "Negro", 80.0, 20.0, 5.0, 10.0, TipoVehiculo.MOTO);

        vehiculos.add(vehiculo);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        return vehiculos;
    }*/
