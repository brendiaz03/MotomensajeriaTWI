package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.conductor.ConductorServicio;
import com.tallerwebi.presentacion.Datos.DatosLoginConductor;
import com.tallerwebi.dominio.login.LoginServicio;
import com.tallerwebi.dominio.viaje.ViajeServicio;
import org.junit.jupiter.api.BeforeEach;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;


public class LoginControladorTest {

	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private LoginControlador controladorLogin;
	private LoginServicio servicioLoginMock;
	private ConductorServicio servicioConductorMock;
	private Conductor usuarioMock;
	private DatosLoginConductor datosLoginMock;
	private ViajeServicio viajeServicio;


	@BeforeEach
	public void init() {
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(LoginServicio.class);
		servicioConductorMock = mock(ConductorServicio.class);
		viajeServicio = mock(ViajeServicio.class);
		when(requestMock.getSession()).thenReturn(sessionMock);
		controladorLogin = new LoginControlador(servicioLoginMock, servicioConductorMock, viajeServicio);
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
