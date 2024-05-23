package com.tallerwebi.presentacion;
import org.springframework.stereotype.Controller;

@Controller
public class CompaniaControlador {

//    private IServiceCompania iServiceCompania;
//
//    private ConductorServicio conductorServicio;
//
//    private ImagenServicio imagenServicio;
//
//    @Autowired
//    public CompaniaControlador(IServiceCompania iServiceCompania, ConductorServicio conductorServicio, ImagenServicio imagenServicio){
//        this.iServiceCompania = iServiceCompania;
//        this.conductorServicio = conductorServicio;
//        this.imagenServicio = imagenServicio;
//
//    }
//
//    @RequestMapping("/compania")
//    public ModelAndView mostrarVistaCompania(HttpServletRequest request) throws ConductorNoEncontradoException {
//        ModelMap model = new ModelMap();
//
//        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");
//
//        Conductor conductor;
//
//        model.put("isUsuarioLogueado",isUsuarioLogueado);
//        if(request.getSession().getAttribute("IDUSUARIO") != null){
//            conductor = conductorServicio.obtenerConductorPorId((Integer) request.getSession().getAttribute("IDUSUARIO"));
//        }else{
//            conductor = null;
//        }
//        model.put("conductor", conductor);
//        Imagen logo = imagenServicio.getImagenByName("logo");
//        model.put("logo", logo);
//        Imagen user = imagenServicio.getImagenByName("user");
//        model.put("user", user);
//        Imagen auto = imagenServicio.getImagenByName("auto");
//        model.put("auto", auto);
//        Imagen fondo = imagenServicio.getImagenByName("fondo");
//        model.put("fondo", fondo);
//        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
//        model.put("botonPS", botonPS);
//        return new ModelAndView("compania", model);
//    }

}
