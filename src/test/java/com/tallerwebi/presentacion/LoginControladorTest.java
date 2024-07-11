package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.enums.TipoUsuario;
import com.tallerwebi.dominio.exceptions.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.presentacion.Datos.DatosLogin;
import com.tallerwebi.dominio.login.LoginServicio;
import org.junit.jupiter.api.BeforeEach;
import javax.servlet.http.HttpSession;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;


public class LoginControladorTest {

	private LoginControlador loginControlador;
	private LoginServicio loginServicioMock;
	private HttpSession sessionMock;

	@BeforeEach
	public void init() {
		loginServicioMock = mock(LoginServicio.class);
		sessionMock = mock(HttpSession.class);
		loginControlador = new LoginControlador(loginServicioMock);
	}

	@Test
	public void errorDeberiaLlevarAError() {
		// Preparación
		String mensajeError = "Error inesperado";

		// Ejecución
		ModelAndView modelAndView = loginControlador.error(mensajeError);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("error"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase(mensajeError));
	}

	@Test
	public void mostrarHomeDeberiaLlevarAHome() {
		// Ejecución
		ModelAndView modelAndView = loginControlador.mostrarHome(sessionMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
	}

	@Test
	public void mostrarLoginDeberiaLlevarALogin() {
		// Preparación
		String mensajeError = "Credenciales inválidas";

		// Ejecución
		ModelAndView modelAndView = loginControlador.mostrarLogin(mensajeError);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase(mensajeError));
		assertThat(modelAndView.getModel().get("datosLogin"), instanceOf(DatosLogin.class));
	}

	@Test
	public void validarLoginConCredencialesCorrectasDeberiaLlevarAUbicacionSiEsConductor() {
		// Preparación
		DatosLogin datosLogin = new DatosLogin("usuario", "password");
		Usuario usuarioMock = mock(Usuario.class);
		when(usuarioMock.getTipoUsuario()).thenReturn(TipoUsuario.conductor);
		when(loginServicioMock.consultarUsuario(datosLogin.getUsuario(), datosLogin.getPassword())).thenReturn(usuarioMock);

		// Ejecución
		ModelAndView modelAndView = loginControlador.validarLogin(datosLogin, sessionMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("ubicacion"));
		verify(sessionMock, times(1)).setAttribute("IDUSUARIO", usuarioMock.getId());
		verify(sessionMock, times(1)).setAttribute("tipoUsuario", usuarioMock.getTipoUsuario());
		verify(sessionMock, times(1)).setAttribute("isEditForm", false);
	}

	@Test
	public void validarLoginConCredencialesCorrectasDeberiaRedirigirAHomeClienteSiNoEsConductor() {
		// Preparación
		DatosLogin datosLogin = new DatosLogin("usuario", "password");
		Usuario usuarioMock = mock(Usuario.class);
		when(usuarioMock.getTipoUsuario()).thenReturn(TipoUsuario.cliente);
		when(loginServicioMock.consultarUsuario(datosLogin.getUsuario(), datosLogin.getPassword())).thenReturn(usuarioMock);

		// Ejecución
		ModelAndView modelAndView = loginControlador.validarLogin(datosLogin, sessionMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home-cliente"));
		verify(sessionMock, times(1)).setAttribute("IDUSUARIO", usuarioMock.getId());
		verify(sessionMock, times(1)).setAttribute("tipoUsuario", usuarioMock.getTipoUsuario());
		verify(sessionMock, times(1)).setAttribute("isEditForm", false);
	}

	@Test
	public void validarLoginConCredencialesIncorrectasDeberiaLlevarALogin() {
		// Preparación
		DatosLogin datosLogin = new DatosLogin("usuario", "password");
		when(loginServicioMock.consultarUsuario(datosLogin.getUsuario(), datosLogin.getPassword())).thenReturn(null);

		// Ejecución
		ModelAndView modelAndView = loginControlador.validarLogin(datosLogin, sessionMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
	}

	@Test
	public void cerrarSesionDeberiaInvalidarSesionYLlevarAHome() throws UsuarioNoEncontradoException {
		// Ejecución
		ModelAndView modelAndView = loginControlador.cerrarSesion(sessionMock);

		// Validación
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
		verify(sessionMock, times(1)).invalidate();
	}
}