package com.tallerwebi.presentacion.VehiculoTest;

import static org.hamcrest.MatcherAssert.assertThat;
///*
//@Nested
//class ControladorVehiculoTest {
//
///*
//    private VehiculoControlador controladorVehiculo;
//
//    private VehiculoServicio iservicioVehiculo;
//
//    private ImagenServicio iimageService;
//
//    //private VehiculoRepositorio irepositoryVehiculo;
//
//    private List<Vehiculo> vehiculos = new ArrayList<>();
//
//    @BeforeEach
//    public void init() {
//        //this.irepositoryVehiculo = new VehiculoRepositorioImpl();
//        //this.iservicioVehiculo = new VehiculoServicioImpl(this.irepositoryVehiculo);
//
//        this.iservicioVehiculo = mock(VehiculoServicio.class);
//
//        this.iimageService = mock(ImagenServicio.class);
//
//        this.controladorVehiculo = new VehiculoControlador(this.iservicioVehiculo, this.iimageService);
//    }
//
//    //FUNCIONA OK
//    @Test
//    public void queAlSolicitarLaPantallaRegistrarmeSeMuestreElFormularioDeRegistroDelConductor() {
//
//        ModelAndView mav = this.controladorVehiculo.mostrarRegistroDelVehiculo();
//        String message = mav.getModel().get("message").toString();
//        assertThat(mav.getViewName(), equalToIgnoringCase("registro-vehiculo"));
//        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo"));
//    }
//}
//
//    //FUNCIONA OK
//  /*  @Test
//    public void queSePuedaAgregarUnVehiculo() {
//        // Preparación
//       /* Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 22);
//
//        Conductor nuevoConductor = new Conductor("Juan","Pérez",12345678,"juan.perez@example.com","passwordSeguro123","juanperez","Calle Falsa 123",                "1122334455",                "12345678901234567890");
//
//        // Ejecución
//        ModelAndView mav = controladorVehiculo.registrarVehiculo(vehiculo1, nuevoConductor);
//        String message = mav.getModel().get("message").toString();
//
//      //  vehiculos.add(vehiculo1);
//
//        when(this.iservicioVehiculo.registrarVehiculo(vehiculo1, nuevoConductor).thenReturn(true));
//
//        // Verificación
//        assertThat(vehiculos.size(), equalTo(1));
//        assertThat(mav.getViewName(), equalToIgnoringCase("registro-vehiculo"));
//        assertThat(message, equalToIgnoringCase("Bienvenido a su vehiculo"));*/
//
//        // Preparación
//      /*  Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 22);
//        Conductor nuevoConductor = new Conductor("Juan", "Pérez", 12345678, "juan.perez@example.com", "passwordSeguro123", "juanperez", "Calle Falsa 123", "1122334455", "12345678901234567890");
//
//        when(iservicioVehiculo.queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductorServicio(vehiculo1, nuevoConductor)).thenReturn(true);
//
//        // Ejecución
//        ModelAndView mav = controladorVehiculo.registrarVehiculo(vehiculo1, nuevoConductor);
//
//        // Verificación
//        verify(iservicioVehiculo).queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductorServicio(vehiculo1, nuevoConductor); // Verifica que el servicio fue llamado
//        assertThat(mav.getViewName(), equalToIgnoringCase("registro-vehiculo"));
//    }
//    }*/
//
//    //FUNCIONA OK
//    /*@Test
//    public void queAlIngresarAlaPantallaDelVehiculoMeMuestreTodosLosVehiculos() {
//        // Preparación
//
//        List<Vehiculo> vehiculos = dadoQueExistenVehiculos();
//
//        mockearVehiculos(vehiculos);
//
//        // Ejecución
//
//        //List<Vehiculo>vehiculosObtenidos = this.controladorVehiculo.obtenerTodosLosVehiculos();
//
//        // Verificación
//
//        assertThat(vehiculosObtenidos.size(), equalTo(vehiculos.size()));
//    }*/
///*
//    private void mockearVehiculos(List<Vehiculo> vehiculos) {
//        when(this.iservicioVehiculo.obtenerTodosLosVehiculos()).thenReturn(vehiculos);
//    }
//
//    private static List<Vehiculo> dadoQueExistenVehiculos() {
//        List<Vehiculo>vehiculos = new ArrayList<>();
//
//        Vehiculo vehiculo1 = new Vehiculo("123ABC", "Rosa", "Vw");
//        Vehiculo vehiculo2 = new Vehiculo("789QRS", "Azul", "FIat");
//        Vehiculo vehiculo3 = new Vehiculo("456", "Violeta", "Renault");
//
//        vehiculos.add(vehiculo1);
//        vehiculos.add(vehiculo2);
//        vehiculos.add(vehiculo3);
//        return vehiculos;
//    }
//*/
//}
//
//
