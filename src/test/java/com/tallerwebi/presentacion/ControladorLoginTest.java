/*package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ViajeService;
import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.imagen.IImageService;
import com.tallerwebi.dominio.login.DatosLoginConductor;
import com.tallerwebi.dominio.login.ILoginService;
import org.junit.jupiter.api.BeforeEach;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;


public class ControladorLoginTest {

	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private LoginController controladorLogin;
	private ILoginService servicioLoginMock;
	private IImageService servicioImagenMock;
	private Conductor usuarioMock;
	private DatosLoginConductor datosLoginMock;
	private ViajeService viajeService;


	@BeforeEach
	public void init() {
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ILoginService.class);
		servicioImagenMock = mock(IImageService.class);
		this.viajeService = mock(ViajeService.class);
		when(requestMock.getSession()).thenReturn(sessionMock);
		controladorLogin = new LoginController(servicioLoginMock, servicioImagenMock, viajeService);
		usuarioMock = mock(Conductor.class);
		when(usuarioMock.getNombreUsuario()).thenReturn("b");
		when(usuarioMock.getPassword()).thenReturn("b");  // Establecer una contraseña válida
		datosLoginMock = new DatosLoginConductor("dami@unlam.com", "123");
	}

	@Test
	public void loginConUsuarioYPasswordIncorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		Conductor usuarioEncontradoMock = mock(Conductor.class);

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
	}

}*/
