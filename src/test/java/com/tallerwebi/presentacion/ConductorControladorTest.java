package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConductorControladorTest {

   private ConductorControlador conductorControlador;
   private ConductorServicio conductorServicio;
   private ImagenServicio iimageService;
    private VehiculoServicio vehiculoServicio;


    @BeforeEach //antes que ejecuten los test, se ejecute este método (como un constructor de test)
   public void init() throws Exception {
       this.conductorServicio = mock(ConductorServicio.class);
        this.vehiculoServicio = mock(VehiculoServicio.class);
        this.iimageService= mock(ImagenServicio.class);//con el mock solamente probaria los métodos de controlador y nada más (obvio que depende de en que instancia lo pruebe xd
       this.conductorControlador = new ConductorControlador(this.conductorServicio, this.iimageService, this.vehiculoServicio);

   }

//    @Test
//    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor(){
//        ModelAndView mav= this.controllerConductor.mostrarRegistroConductor("",);
//        assertThat(mav.getViewName(),equalToIgnoringCase("registro-conductor"));
//    }


//    @Test
//    public void queUnConductorCompleteElFormulario() throws Exception {
//        Conductor nuevoConductor = new Conductor("Juan", "Perez", 42952902, "juan@example.com", "password1", "juanito", "Calle Falsa 123",
//                "1561639242", "0001002900001234567891",null);
//        when(conductorServicio.verificarDatosDeRegistro(nuevoConductor)).thenReturn(true);
//
//        ModelAndView modelAndView = this.controllerConductor.registrarConductor(nuevoConductor, );
//
//        assertThat(modelAndView.getViewName(),equalToIgnoringCase("redirect:/vehiculo"));
//
//
//        verify(conductorServicio, times(1)).verificarDatosDeRegistro(nuevoConductor);
//    }

}
