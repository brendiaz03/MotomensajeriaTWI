package com.tallerwebi.dominio.vehiculo;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioVehiculoImpl implements IServicioVehiculo {

    private List<Vehiculo> vehiculos;

    private IRepositoryVehiculo irepositoryVehiculo;

    private List<Conductor>conductores;

    public ServicioVehiculoImpl(List<Vehiculo> vehiculos, IRepositoryVehiculo irepositoryVehiculo) {
        this.vehiculos = vehiculos;

        this.irepositoryVehiculo = irepositoryVehiculo;
    }

    @Override
    public List<Vehiculo> get() {
        return vehiculos;
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
    public Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculoAmodificar, Conductor conductor, List<Conductor>conductores) {

        if (!queExistaElConductorEnLaListaDeConductores(conductor, conductores)) {
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
                break; // Salir del bucle una vez que se haya encontrado el vehículo
            }
        }

        // Si el vehículo no se encuentra en la lista, no se puede modificar
        if (!vehiculoEncontrado) {
            return false;
        }

        return true; // Se modificó el vehículo exitosamente


        /*if (queExistaElConductorEnLaListaDeConductores(conductor, conductores)) {

            for (Vehiculo vehiculoGuardado : vehiculos) {
                if (vehiculoGuardado.getId().equals(vehiculoAmodificar.getId())) {
                }
            }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getId().equals(vehiculoAmodificar.getId())) {
                vehiculo.setPatente(vehiculoAmodificar.getPatente());
                vehiculo.setColor(vehiculoAmodificar.getColor());
                vehiculo.setModelo(vehiculoAmodificar.getModelo());
                vehiculo.setTipoDeVehiculo(vehiculoAmodificar.getTipoDeVehiculo());
                vehiculo.setPesoSoportado(vehiculoAmodificar.getPesoSoportado());
                vehiculo.setDimensionDisponible(vehiculoAmodificar.getDimensionDisponible());
                vehiculo.setIdConductor(vehiculoAmodificar.getIdConductor());
            }
        }
    }
        return true;*/

      //  public Boolean modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculoAmodificar, Conductor conductor, List<Conductor> conductores) {
            // Verificar si el conductor existe en la lista de conductores


    }

    @Override
    public List<Vehiculo> getByTipoDeVehiculo(TipoVehiculo tipoVehiculo) {

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
    }

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
    public ModelAndView registroVehiculo(Vehiculo vehiculo, Conductor conductor) {

        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();

        model.put("message", "Bienvenido al registro de su vehiculo");

        if(queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(vehiculos, vehiculo, conductor, conductores)){
            vehiculos.add(vehiculo);
        }

        return new ModelAndView(viewName, model);
    }

    @Override
    public Boolean queSePuedaRegistrarUnNuevoVehiculoConElDniDelConductor(List<Vehiculo> vehiculos, Vehiculo nuevoVehiculo, Conductor conductor, List<Conductor>conductores) {

            if (!queExistaElConductorEnLaListaDeConductores(conductor, conductores)) {
                return false;
            }

            for (Vehiculo vehiculoGuardado : vehiculos) {
                if (vehiculoGuardado.getId().equals(nuevoVehiculo.getId())) {
                    return false;
                }
            }

            for (Vehiculo vehiculoGuardado : vehiculos) {
                if (vehiculoGuardado.getIdConductor().equals(conductor.getId())) {
                    return false;
                }
            }

            // Si todas las verificaciones pasan, se puede registrar el vehículo
            return true;
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
    }



