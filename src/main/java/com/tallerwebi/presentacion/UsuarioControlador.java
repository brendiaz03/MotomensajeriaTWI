package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuario.UsuarioServicio;
import com.tallerwebi.presentacion.Datos.DatosUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

    private UsuarioServicio usuarioServicio;
    private ConductorServicio conductorServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio, ConductorServicio conductorServicio) {
        this.usuarioServicio = usuarioServicio;
        this.conductorServicio = conductorServicio;
    }

    @RequestMapping(value = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView mostrarForm(String mensajeError, HttpSession session) throws UsuarioNoEncontradoException {
        String viewName= "form-usuario";
        ModelMap model = new ModelMap();
        model.put("usuario", new DatosUsuario());

        model.put("isEditForm", false);

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/registrar-usuario")
    public ModelAndView registrarConductor(@ModelAttribute("usuario") DatosUsuario nuevoUsuario, HttpSession session) throws Exception {
        try{
            Usuario usuario = usuarioServicio.registrarUsuario(nuevoUsuario);
            if(usuario instanceof Conductor){
                Conductor conductor = (Conductor)usuario;
                session.setAttribute("IDUSUARIO", conductor.getId());
                return new ModelAndView("redirect:/vehiculo");
            }else{
                Cliente cliente = (Cliente)usuario;
                session.setAttribute("IDUSUARIO", cliente.getId());
                return new ModelAndView("redirect:/homeCliente");
            }
        }catch(Exception e){
            e.printStackTrace();
            return this.mostrarForm("Se ha producido un error en el servidor.",session);
        }
    }
}