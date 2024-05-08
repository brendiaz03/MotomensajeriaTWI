package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private HttpServletRequest requestMock;
	private HttpSession sessionMock;


	@BeforeEach
	public void init(){
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);

	}

//	@Test
//	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
//		// preparacion
//		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);
//
//		// ejecucion
//		//ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
//
//		// validacion
//		//assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
//		//assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
//		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
//	}
//
//	@Test
//	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
//		// preparacion
//		Usuario usuarioEncontradoMock = mock(Usuario.class);
//		//when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");
//
//		when(requestMock.getSession()).thenReturn(sessionMock);
//		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
//
//		// ejecucion
//		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
//
//		// validacion
//		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
//		//verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
//	}


}
