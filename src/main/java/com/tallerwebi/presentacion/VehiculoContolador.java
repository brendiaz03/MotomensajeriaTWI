package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class VehiculoContolador {

    private ImagenServicio imagenServicio;

    private VehiculoServicio vehiculoServicio;

    @Autowired
    public VehiculoContolador(VehiculoServicio vehiculoServicio, ImagenServicio _imagenServicio) {

        this.vehiculoServicio = vehiculoServicio;

        this.imagenServicio = _imagenServicio;

    }

    @RequestMapping(path = "/vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo() {

        String viewName = "registro-vehiculo";

        ModelMap model = new ModelMap();
        model.put("message", "Bienvenido a su vehiculo");
        model.put("vehiculo", new Vehiculo());
        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = imagenServicio.getImagenByName("user");
        model.put("user", user);

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registro-vehiculo")
    public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo) {
       ModelMap model = new ModelMap();
               if(vehiculoServicio.registrarVehiculoSiPatenteNoEstaYaCargada(nuevoVehiculo)){
                   return new ModelAndView("redirect:/home");
           }else{
                   model.put("error", "Patente Repetida");
                   return new ModelAndView("redirect:/registro-vehiculo", model);
               }
    }

}
