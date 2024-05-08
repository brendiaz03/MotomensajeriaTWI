package com.tallerwebi.presentacion.VehiculoTest;

import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IRepositoryVehiculo;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.ServicioVehiculoImpl;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import com.tallerwebi.presentacion.vehiculo.ControladorVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsEqual.equalTo;

@Nested
class ControladorVehiculoTest {

    private IServicioVehiculo iservicioVehiculo;

    private IRepositoryVehiculo irepositoryVehiculo;

    private List<Vehiculo> vehiculos = new ArrayList<>();

    private ControladorVehiculo controladorVehiculo;

    @BeforeEach
    public void init() {
        ServicioVehiculoImpl servicioVehiculo = new ServicioVehiculoImpl(vehiculos, irepositoryVehiculo);
        this.controladorVehiculo = new ControladorVehiculo(servicioVehiculo);
    }

    //FUNCIONA OK
    @Test
    public void queSePuedaAgregarUnVehiculo() {
        //preparación
        // ControladorVehiculo controladorVehiculo = new ControladorVehiculo(this.crearLista());
        Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        //ejecución
        ModelAndView mav = controladorVehiculo.mostrarRegistroDelVehiculo();
        String message = mav.getModel().get("message").toString();
        vehiculos.add(vehiculo1);
        //verificación

        /*Verifica que se pueda agregar un vehiculo, que la vista sea la correcta y que la información que acompaña a esa vista sea la correcta.*/
        assertThat(vehiculos.size(), equalTo(1));
        assertThat(mav.getViewName(), equalToIgnoringCase("registro-vehiculo")); //Verifica que la vista se la correcta
        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo")); //Verifica que se muestre un mensaje de bienvenida
    }

    //FUNCIONA OK
    @Test
    public void queAlIngresarAlaPantallaDelVehiculoMeMuestreTodosLosVehiculos() {
        //preparación

        Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo3 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        //ejecución
        ModelAndView mav = controladorVehiculo.mostrarRegistroDelVehiculo();

        //verificación
        List<Vehiculo> vehiculos = (List<Vehiculo>) mav.getModel().get("vehiculos");
        assertThat(vehiculos.size(), equalTo(3));
    }
}

/*@Test
public void queAlBuscarVehiculosObtengaLosDeTipoAuto() {
    // Preparation

    // Ejecución

    List<Vehiculo> vehiculos= new ArrayList<Vehiculo>();

    Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
    Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
    Vehiculo vehiculo3 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.MOTO, 1500.0, 5.0, 1L);

    vehiculos.add(vehiculo1);
    vehiculos.add(vehiculo2);
    vehiculos.add(vehiculo3);

    // controladorVehiculo;
    ControladorVehiculo controladorVehiculoPrueba = null;

    ModelAndView mav = controladorVehiculoPrueba.filtrarVehiculosPorTipo(TipoVehiculo.AUTO);


    // Verificación
    List<Vehiculo> vehiculos = (List<Vehiculo)mav.getModel().get("vehiculos");

    assertThat(mav.getViewName(), equalToIgnoringCase("buscar-vehiculos")); //Verifica que la vista se la correcta
    assertThat(vehiculos.size(), equalTo(2));
    assertThat(vehiculos.get(0).getTipoDeVehiculo(), equalTo(TipoVehiculo.AUTO));
}*/



