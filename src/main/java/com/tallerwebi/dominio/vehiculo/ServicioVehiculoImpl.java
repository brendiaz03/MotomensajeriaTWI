package com.tallerwebi.dominio.vehiculo;

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

    public ServicioVehiculoImpl(List<Vehiculo> vehiculos, IRepositoryVehiculo irepositoryVehiculo) {
        this.vehiculos = vehiculos;
        this.irepositoryVehiculo = irepositoryVehiculo;
    }

    /*public ServicioVehiculoImpl() {

        this.irepositoryVehiculo = irepositoryVehiculo;

        this.vehiculos = vehiculos;

        vehiculos.add(new Vehiculo(1L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(2L, TipoVehiculo.AUTO));
        vehiculos.add(new Vehiculo(3L, TipoVehiculo.MOTO));

    }*/

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

    public List<Vehiculo> modificarVehiculo(List<Vehiculo> vehiculos, Vehiculo vehiculoAmodificar) {

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
        return vehiculos;
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

    /*REGISTRAR Y BUSCAR
    * 1RO QUE ESTÉ COMPLETO EL FORMULARIO DE INGRESO DEL VEHÍCULO -> CONTROLADOR
    * 2DO VERIFICA SI ESTÁ TODO OK ->SERVICIO
    * 3RO QUE SE PUEDA REGISTRAR UN VEHICULO Y QUE NO EXISTA OTRO IGUAL REPETIDO -> REPOSITORIO*/

    @RequestMapping(path = "/buscar-vehiculo", method = RequestMethod.GET)
    public ModelAndView buscarVehiculosPorId(Vehiculo vehiculo){

        String viewName = "buscar-vehiculos";

        ModelMap model = new ModelMap();

        model.put("message", "Bienvenido a su vehiculo");

        List<Vehiculo> vehiculosAuto = new ArrayList<>();

        for (Vehiculo vehiculo : this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo)){

            if(vehiculo.getTipoDeVehiculo().equals(TipoVehiculo.AUTO)) {
                vehiculosAuto.add(vehiculo);
            }
        }

        model.put("vehiculos",this.servicioVehiculo.getByTipoDeVehiculo(tipoVehiculo));

        return new ModelAndView(viewName, model);

    }

}
