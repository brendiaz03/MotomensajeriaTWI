package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.exceptions.VehiculoDuplicadoException;
import com.tallerwebi.dominio.vehiculo.VehiculoServicio;
import com.tallerwebi.dominio.vehiculo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpSession;


@Controller
public class VehiculoControlador {

    private VehiculoServicio vehiculoServicio;

    private ConductorServicio conductorServicio;

    @Autowired
    public VehiculoControlador(VehiculoServicio vehiculoServicio, ConductorServicio _conductorServicio) {
        this.vehiculoServicio = vehiculoServicio;
        this.conductorServicio = _conductorServicio;
    }

    @RequestMapping(path = "/form-vehiculo", method = RequestMethod.GET)
    public ModelAndView mostrarRegistroDelVehiculo(Vehiculo nuevoVehiculo,String mensajeError,HttpSession session) throws UsuarioNoEncontradoException {
        ModelMap model = new ModelMap();
        String viewName = "form-vehiculo";
        if(mensajeError==null||mensajeError.isEmpty()){
            model.put("mensajeError",null);
        }else{
            model.put("vehiculo",nuevoVehiculo);
            model.put("mensajeError",mensajeError);
        }

        Conductor buscado =conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
        Boolean estaLogueado=(Boolean)session.getAttribute("isUsuarioLogueado");
        if((estaLogueado!=null && estaLogueado) && buscado.getVehiculo()==null){
            System.out.println("EL USUARIO ESTA LOGUEADO");

            model.put("estaLogueado",estaLogueado);
            model.put("noVehiculo", true);
        }else if(estaLogueado==null || !estaLogueado){
            System.out.println("EL USUARIO NOOOOO ESTA LOGUEADO");

            model.put("noVehiculo", true);
        }else{
            model.put("noVehiculo",false);
        }

        if((Boolean)session.getAttribute("isEditForm")){
            model.put("isEditForm", true);
            Conductor conductor =conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
            model.put("vehiculo", conductor.getVehiculo());
        }else{
            model.put("isEditForm", false);
            model.put("vehiculo", new Vehiculo());
        }

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-vehiculo")
    public ModelAndView registrarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws UsuarioNoEncontradoException {
        try{
            Vehiculo vehiculo = vehiculoServicio.registrarVehiculo(nuevoVehiculo);
            conductorServicio.RelacionarVehiculoAConductor((Integer)session.getAttribute("IDUSUARIO"), vehiculo);

            Boolean estaLogueado=(Boolean)session.getAttribute("isUsuarioLogueado");
            if((estaLogueado!=null && estaLogueado)){
                return new ModelAndView("redirect:/perfil");
            }else{
                return new ModelAndView("redirect:/home");
            }
        }catch(UsuarioNoEncontradoException | VehiculoDuplicadoException e){
            return this.mostrarRegistroDelVehiculo(nuevoVehiculo,e.getMessage(),session);
        }
    }

    @PostMapping("/editar-vehiculo")
    public ModelAndView editarVehiculo(@ModelAttribute("vehiculo") Vehiculo nuevoVehiculo, HttpSession session) throws UsuarioNoEncontradoException {
        try{
            Conductor conductor =conductorServicio.obtenerConductorPorId((Integer)session.getAttribute("IDUSUARIO"));
            nuevoVehiculo.setId(conductor.getVehiculo().getId());
            vehiculoServicio.actualizarVehiculo(nuevoVehiculo);
            session.setAttribute("isEditForm", false);
            return new ModelAndView("redirect:/perfil");
        }catch(UsuarioNoEncontradoException e){
            return this.mostrarRegistroDelVehiculo(nuevoVehiculo,e.getMessage(),session);
        }
    }

    @RequestMapping(value = "/form-vehiculo-editar", method = RequestMethod.GET)
    public String mostrarEditarVehiculo(HttpSession session) {
        session.setAttribute("isEditForm", true);
        return "redirect:/form-vehiculo";
    }



}
