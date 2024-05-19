package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.dominio.imagen.ImagenServicio;
import com.tallerwebi.dominio.login.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import com.tallerwebi.dominio.viaje.ViajeService;
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
	private LoginControlador controladorLogin;
	private LoginServicio servicioLoginMock;
	private ImagenServicio servicioImagenMock;
	private ConductorServicio servicioConductorMock;
	private Conductor usuarioMock;
	private DatosLoginConductor datosLoginMock;
	private ViajeService viajeService;


	@BeforeEach
	public void init() {
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(LoginServicio.class);
		servicioImagenMock = mock(ImagenServicio.class);
		servicioConductorMock = mock(ConductorServicio.class);
		viajeService = mock(ViajeService.class);
		when(requestMock.getSession()).thenReturn(sessionMock);
		controladorLogin = new LoginControlador(servicioLoginMock, servicioImagenMock, servicioConductorMock, viajeService);
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

}
