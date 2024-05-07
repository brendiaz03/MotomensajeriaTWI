package com.tallerwebi.presentacion.vehiculo;
import com.tallerwebi.dominio.enums.TipoVehiculo;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class ControladorVehiculo{

    private IServicioVehiculo servicioVehiculo;

    public ControladorVehiculo(IServicioVehiculo servicioVehiculo) {

        this.servicioVehiculo = servicioVehiculo;

    }

    @RequestMapping(path = "/vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo() {

  /*  List<VehiculoDto> vehiculos = new ArrayList<VehiculoDto>();
    vehiculos.add(new VehiculoDto());
    vehiculos.add(new VehiculoDto());
    vehiculos.add(new VehiculoDto());*/


        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();
        model.put("message", "Bienvenido a su vehiculo");
        model.put("vehiculos", this.servicioVehiculo.get());

        return new ModelAndView(viewName, model);
    }

    @RequestMapping(path = "/buscar-vehiculo", method = RequestMethod.GET)
    public ModelAndView filtrarVehiculosPorTipo(TipoVehiculo tipoVehiculo){

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
