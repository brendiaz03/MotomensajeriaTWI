package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.login.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@SessionAttributes("isUsuarioLogueado")
public class LoginControlador {

    private static LoginServicio loginServicio;
    private static ImagenServicio imagenServicio;

    @Autowired
    public LoginControlador(LoginServicio _LoginServicio, ImagenServicio _imagenServicio){
        this.loginServicio = _LoginServicio;
        this.imagenServicio = _imagenServicio;
    }

    @RequestMapping("/home")
    public ModelAndView mostrarHome(HttpServletRequest request){
        String viewName= "home";
        ModelMap model = new ModelMap();
        Boolean isUsuarioLogueado = (Boolean) request.getSession().getAttribute("isUsuarioLogueado");

            model.put("isUsuarioLogueado",isUsuarioLogueado);

        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        Imagen user = imagenServicio.getImagenByName("user");
        model.put("user", user);
        Imagen auto = imagenServicio.getImagenByName("auto");
        model.put("auto", auto);
        Imagen fondo = imagenServicio.getImagenByName("fondo");
        model.put("fondo", fondo);
        Imagen botonPS = imagenServicio.getImagenByName("botonPS");
        model.put("botonPS", botonPS);
        return new ModelAndView(viewName,model);
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView mostrarLogin(){
        String viewName= "login-conductor";
        ModelMap model = new ModelMap();
        model.put("datosLogin",new DatosLoginConductor());
        Imagen logo = imagenServicio.getImagenByName("logo");
        model.put("logo", logo);
        return new ModelAndView(viewName,model);
    }

    @RequestMapping(path="/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLoginConductor datosLoginnConductor, HttpServletRequest request) {

        ModelMap model = new ModelMap();
            Conductor conductorBuscado = loginServicio.consultarUsuario(datosLoginnConductor.getUsuario(), datosLoginnConductor.getPassword());
            if (conductorBuscado != null) {
                request.getSession().setAttribute("NOMBRE", conductorBuscado.getNombre());
                request.getSession().setAttribute("IDUSUARIO", conductorBuscado.getId());
                request.getSession().setAttribute("APELLIDO", conductorBuscado.getApellido());
                request.getSession().setAttribute("isUsuarioLogueado", true);
                request.getSession().setAttribute("isEditForm", false);
                model.put("error", "Usuario o clave correcta");
                return new ModelAndView("redirect:/home", model);

            }else{
                model.put("error", "Usuario o clave incorrecta");
                request.getSession().setAttribute("isUsuarioLogueado", false);
                return new ModelAndView("redirect:/login", model);

            }

    }

    @RequestMapping(path = "/cerrar-sesion", method = RequestMethod.GET)
    public ModelAndView cerrarSesion(HttpServletRequest request) {
        request.getSession().invalidate(); // Invalida la sesión, lo que equivale a cerrar sesión
        return mostrarHome(request);
    }


}
