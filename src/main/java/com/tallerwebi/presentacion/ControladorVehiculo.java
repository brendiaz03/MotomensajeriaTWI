package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorDuplicadoException;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.IServicioVehiculo;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorVehiculo{

    private IImageService iImageService;

    private IServicioVehiculo iServicioVehiculo;

    @Autowired
    public ControladorVehiculo(IServicioVehiculo iServicioVehiculo, IImageService _iImageService) {

        this.iServicioVehiculo = iServicioVehiculo;

        this.iImageService = _iImageService;

    }

    @RequestMapping(path = "/vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo() {

        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();
        model.put("message", "Bienvenido a su vehiculo");
        model.put("vehiculo", new Vehiculo());
        Imagen logo = iImageService.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = iImageService.getImagenByName("user");
        model.put("user", user);

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registro-vehiculo")
    public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo) {
       ModelMap model = new ModelMap();
               if(iServicioVehiculo.registrarVehiculoSiPatenteNoEstaYaCargada(nuevoVehiculo)){
                   System.out.println(nuevoVehiculo.getColor());
                   return new ModelAndView("redirect:/home");
           }else{
                   model.put("error", "Patente Repetida");
                   return new ModelAndView("redirect:/registro-vehiculo", model);
               }
    }

}
