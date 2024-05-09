package com.tallerwebi.dominio.vehiculo;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioVehiculoImpl implements IServicioVehiculo {

    private List<Vehiculo> vehiculos;

    private IRepositoryVehiculo irepositoryVehiculo;

    private List<Conductor>conductores;

    private EntityManager entityManager;

    @Autowired
    public ServicioVehiculoImpl(IRepositoryVehiculo irepositoryVehiculo) {
        this.vehiculos = vehiculos;

        this.irepositoryVehiculo = irepositoryVehiculo;
    }

    @Override
    public List<Vehiculo> get() {
        //return vehiculos;
        return this.convertToDatosVehiculos(this.irepositoryVehiculo.get());
    }

    @Override
    public List<Vehiculo> setVehiculos() {
        return this.vehiculos = vehiculos;
    }

//    public IRepositoryVehiculo getIrepositoryVehiculo() {
//        return irepositoryVehiculo;
//    }
//
//    public void setIrepositoryVehiculo(IRepositoryVehiculo irepositoryVehiculo) {
//        this.irepositoryVehiculo = irepositoryVehiculo;
//    }

    @Override
    public List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo) {
        List<Vehiculo> resultado = new ArrayList<>();

        Vehiculo nuevo = new Vehiculo();
        Vehiculo nuevo1 = new Vehiculo();
        Vehiculo nuevo2 = new Vehiculo();

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getTipoDeVehiculo().equals(tipoVehiculo)) {
                resultado.add(vehiculo);
            }
        }
        return resultado;
    }
    /*public List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo) {

        List<Vehiculo> vehiculos = new ArrayList<>();
        List<Vehiculo>vehiculosEncontrados = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo2 = new Vehiculo(1L, "ABC123", "Rojo", "Sedán", TipoVehiculo.AUTO, 1500.0, 5.0, 1L);
        Vehiculo vehiculo3 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        vehiculos.add(vehiculo3);

        for(Vehiculo vehiculo : vehiculos) {

            if (vehiculo.getTipoDeVehiculo().equals(TipoVehiculo.AUTO)) {
                vehiculosEncontrados.add(vehiculo);
            }

        }
        return vehiculosEncontrados;
    }*/

    private List<Vehiculo> convertToDatosVehiculos(List<Vehiculo> vehiculos){
        List<Vehiculo> datosDeVehiculos = new ArrayList<>();

        Vehiculo vehiculo1 = new Vehiculo(4554L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 245);
        //Vehiculo vehiculo1 = new Vehiculo(2L, "DEF456", "Azul", "Camioneta", TipoVehiculo.MOTO, 2000.0, 8.0, 2L);

        for (Vehiculo vehiculo: vehiculos){
            // Suponiendo que Vehiculo tenga un constructor de copia
            datosDeVehiculos.add(new Vehiculo(vehiculo));
        }
        return datosDeVehiculos;
    }

    /*@Override
    public Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculoAModificar, Conductor conductor, List<Conductor> conductores) {

        Boolean conductorEncontrado = buscarConductor(conductor.getNumeroDeDni());

        if (conductorEncontrado) {
            return true;
        }

        boolean vehiculoEncontrado = false;

        for (Vehiculo vehiculoGuardado : vehiculos) {
            if (vehiculoGuardado.getId().equals(vehiculoAModificar.getId())) {
             /*   vehiculoEncontrado = true;
                vehiculoGuardado.setPatente(vehiculoAModificar.getPatente());
                vehiculoGuardado.setColor(vehiculoAModificar.getColor());
                vehiculoGuardado.setModelo(vehiculoAModificar.getModelo());
                vehiculoGuardado.setTipoDeVehiculo(vehiculoAModificar.getTipoDeVehiculo());
                vehiculoGuardado.setPesoSoportado(vehiculoAModificar.getPesoSoportado());
                vehiculoGuardado.setDimensionDisponible(vehiculoAModificar.getDimensionDisponible());
                vehiculoGuardado.setIdConductor(vehiculoAModificar.getIdConductor());
                break;*/
                // if (vehiculoGuardado.getId().equals(vehiculoAModificar.getId())) {
                // Realizar las modificaciones en el vehículo encontrado
            /*    int index = vehiculos.indexOf(vehiculoGuardado);
                vehiculos.set(index, vehiculoAModificar); // Reemplaza el vehículo en la lista
                return true; // Modificación exitosa
            }

        }*/

        // return vehiculoEncontrado;
        // }

/*
    @Override
    public Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculoAmodificar, Conductor conductor, List<Conductor>conductores) {

        if (!buscarConductor(conductor.getId())) {
            return false; // Si el conductor no existe, no se puede modificar el vehículo
        }

        // Verificar si el vehículo a modificar existe en la lista de vehículos
        boolean vehiculoEncontrado = false;
        for (Vehiculo vehiculoGuardado : vehiculos) {
            if (vehiculoGuardado.getId().equals(vehiculoAmodificar.getId())) {
                vehiculoEncontrado = true;
                // Realizar las modificaciones en el vehículo
                vehiculoGuardado.setPatente(vehiculoAmodificar.getPatente());
                vehiculoGuardado.setColor(vehiculoAmodificar.getColor());
                vehiculoGuardado.setModelo(vehiculoAmodificar.getModelo());
                vehiculoGuardado.setTipoDeVehiculo(vehiculoAmodificar.getTipoDeVehiculo());
                vehiculoGuardado.setPesoSoportado(vehiculoAmodificar.getPesoSoportado());
                vehiculoGuardado.setDimensionDisponible(vehiculoAmodificar.getDimensionDisponible());
                vehiculoGuardado.setIdConductor(vehiculoAmodificar.getIdConductor());
                break;
            }
        }

        // Si el vehículo no se encuentra en la lista, no se puede modificar
        if (!vehiculoEncontrado) {
            return false;
        }

        return true; //

    }*/

    /*@RequestMapping(path = "/buscar-vehiculo", method = RequestMethod.GET)
    public ModelAndView buscarVehiculosPorId(Vehiculo vehiculo){

       /* String viewName = "buscar-vehiculos";

        ModelMap model = new ModelMap();

        model.put("message", "Bienvenido a su vehiculo");

        List<Vehiculo> vehiculosAuto = new ArrayList<>();

        for (Vehiculo vehiculo : this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo)){

            if(vehiculo.getTipoDeVehiculo().equals(TipoVehiculo.AUTO)) {
                vehiculosAuto.add(vehiculo);
            }
        }

        model.put("vehiculos",this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo));

        return new ModelAndView(viewName, model);*/

        /*return null;
    }*/

    /*REGISTRAR Y BUSCAR
     * 1RO QUE ESTÉ COMPLETO EL FORMULARIO DE INGRESO DEL VEHÍCULO -> CONTROLADOR
     * 2DO VERIFICA SI ESTÁ TODO OK ->SERVICIO
     * 3RO QUE SE PUEDA REGISTRAR UN VEHICULO Y QUE NO EXISTA OTRO IGUAL REPETIDO -> REPOSITORIO*/

    @RequestMapping(path = "registro-vehiculo", method = RequestMethod.GET)
    public ModelAndView registroVehiculo(Vehiculo vehiculo, Conductor conductor){

        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();

        model.put("message", "Bienvenido al registro de su vehiculo");

        if(queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductorServicio(vehiculo, conductor)){
            vehiculos.add(vehiculo);
        } /*else{
            throw new VehiculoExistenteException("El vehículo ya está registrado para este conductor");
        }*/

        return new ModelAndView(viewName, model);
    }

    @Override
    public Boolean queSePuedaRegistrarUnNuevoVehiculoConElIdDelConductorServicio(Vehiculo nuevoVehiculo, Conductor conductor) {
        if (conductor == null || conductor.getId() == null) {
            return false;
        }

        try {
            // Asigna el ID del conductor al vehículo
            nuevoVehiculo.setIdConductor(conductor.getId());

            // Persiste el vehículo en la base de datos
            entityManager.persist(nuevoVehiculo);
            entityManager.flush();  // Asegura que la operación se complete

            return true;
        } catch (Exception e) {
            // Manejo de la excepción en caso de error en la transacción
            System.out.println("Error al guardar el vehículo: " + e.getMessage());
            return false;
        }

    }

        @Override
        public Boolean queExistaElConductorEnLaListaDeConductores(Conductor conductor, List<Conductor>conductores) {

        Conductor nuevoConductor1 = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        Conductor nuevoConductor2 = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");
        Conductor nuevoConductor3 = new Conductor("Jose", "Perez", 12345678, "juan@example.com", "password", "juanito", "Calle Falsa 123", "1234567890", "0001002900001234567891");

        conductores.add(nuevoConductor1);
        conductores.add(nuevoConductor2);
        conductores.add(nuevoConductor3);

        for (Conductor conductorBuscado : conductores) {
            if (conductorBuscado.getNumeroDeDni().equals(conductor.getNumeroDeDni())) {
                return true;
            }
        }
        return true;
    }

    @Override
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return this.irepositoryVehiculo.obtenerTodosLosVehiculos();
    }

    @Override
    public Vehiculo obtenerVehiculoPorIdDelConductor(Integer id) {
        return this.irepositoryVehiculo.obtenerVehiculoPorElIdDelConductor(id);
    }

    @Override
    public List<Vehiculo> obtenerTodosLosVehiculosDelConductor(Integer id) {
        return this.irepositoryVehiculo.obtenerTodosLosVehiculosDelConductor(id);
    }

}



