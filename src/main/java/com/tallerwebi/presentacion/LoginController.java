package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.imagen.ImageService;
import com.tallerwebi.dominio.imagen.Imagen;
import com.tallerwebi.dominio.login.ILoginService;
import com.tallerwebi.dominio.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private ILoginService iLoginService;
    private IImageService iImagenService;

//    @Autowired
//    public LoginController(ILoginService _iLoginService, IImageService _iImageService){
//        this.iLoginService = _iLoginService;
//        this.iImagenService = _iImageService;
//    }


}
