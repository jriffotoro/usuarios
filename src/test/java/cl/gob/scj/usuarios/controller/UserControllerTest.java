package cl.gob.scj.usuarios.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTO;
import cl.gob.scj.usuarios.dto.UserDTORequest;
import cl.gob.scj.usuarios.exception.SCJException;
import cl.gob.scj.usuarios.model.User;
import cl.gob.scj.usuarios.service.UserService;

@SpringBootTest
@TestPropertySource("classpath:messages.properties")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private MessageSource mensajes;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUserSuccess() throws SCJException {
        // Mock de datos
        UserDTORequest userDTO = new UserDTORequest();
        userDTO.setName("scj");
		userDTO.setEmail("scj@scj.gob.cl");
		userDTO.setPassword("Bgfdgfdg23dsfds");
        User user = new User();
		user.setName("scj");
		user.setEmail("scj@scj.gob.cl");
		user.setPassword("Bgfdgfdg23dsfds");
        RespuestaJSON respuestaJSON = new RespuestaJSON(RespuestaJSON.EstadoType.OK.getRespuestaJSONS(), mensajes.getMessage("user.creado", null, LocaleContextHolder.getLocale()), user);
        ResponseEntity<RespuestaJSON> expectedResponse = new ResponseEntity<>(respuestaJSON, HttpStatus.CREATED);

        // Mock de comportamiento
        when(userService.crearUser(any(UserDTORequest.class))).thenReturn(respuestaJSON);
        when(request.getAttribute("jwtToken")).thenReturn("mockToken");

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.crearUser(userDTO, request);

        // Verificaciones
        verify(userService, times(1)).crearUser(any(UserDTORequest.class));
        verify(request, times(1)).getAttribute("jwtToken");
        assertEquals(expectedResponse, response);
        assertEquals(response.getStatusCode().value(), HttpStatus.CREATED.value());
    }

    @Test
    void testCrearUserSCJException() throws SCJException {
        // Mock de datos
        UserDTORequest user = new UserDTORequest();

        // Mock de comportamiento
        when(userService.crearUser(any(UserDTORequest.class))).thenThrow(new SCJException("Error"));

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.crearUser(user, request);

        // Verificaciones
        verify(userService, times(1)).crearUser(any(UserDTORequest.class));
        assertEquals(HttpStatus.ALREADY_REPORTED, response.getStatusCode());
    }

    @Test
    void testGetUsersSuccess() {
        // Mock de datos
        List<UserDTO> tDto = new ArrayList<UserDTO>();
        UserDTO userDTO = new UserDTO();
        userDTO.setName("scj");
		userDTO.setEmail("scj@scj.gob.cl");
		userDTO.setPassword("Bgfdgfdg23dsfds");
        tDto.add(userDTO);
        RespuestaJSON respuestaJSON = new RespuestaJSON(RespuestaJSON.EstadoType.OK.getRespuestaJSONS(), mensajes.getMessage("ok", null, LocaleContextHolder.getLocale()), tDto);
        ResponseEntity<RespuestaJSON> expectedResponse = new ResponseEntity<>(respuestaJSON, HttpStatus.OK);

        // Mock de comportamiento
        when(userService.getUsers()).thenReturn(respuestaJSON);

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.getUsers();

        // Verificaciones
        verify(userService, times(1)).getUsers();
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

    }

    @Test
    void testGetUsersException() {
        // Mock de datos
        ResponseEntity<RespuestaJSON> expectedResponse = new ResponseEntity<>(new RespuestaJSON(), HttpStatus.INTERNAL_SERVER_ERROR);

        // Mock de comportamiento
        when(userService.getUsers()).thenThrow(new RuntimeException("Error"));

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.getUsers();

        // Verificaciones
        verify(userService, times(1)).getUsers();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testBorraUserUserFound() throws SCJException {
        // Mock de datos
        String userId = "123";
        RespuestaJSON expectedResponse = new RespuestaJSON(RespuestaJSON.EstadoType.OK.getRespuestaJSONS(), "Usuario eliminado", null);

        // Mock de comportamiento
        when(userService.borraUser(userId)).thenReturn(expectedResponse);

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.borraUser(userId);

        // Verificaciones
        verify(userService, times(1)).borraUser(userId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testBorraUserUserNotFound() throws SCJException {
        // Mock de datos
        String userId = "123";
        RespuestaJSON expectedResponse = new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), 
        "Usuario no encontrado");       

        // Mock de comportamiento
        when(mensajes.getMessage(anyString(), isNull(), eq(LocaleContextHolder.getLocale()))).thenReturn("Usuario no encontrado");
        when(userService.borraUser(userId)).thenReturn(expectedResponse);

        // Ejecutar el método a probar
        ResponseEntity<RespuestaJSON> response = userController.borraUser(userId);

        // Verificaciones
        verify(userService, times(1)).borraUser(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testBorraUserException() throws SCJException {
        // Mock de datos
        String userId = "123";
        RespuestaJSON expectedResponse = new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), "Error");

        // Mock de comportamiento
        when(userService.borraUser(userId)).thenThrow(new RuntimeException("Error"));

        // Ejecutar el método a probar
        when(mensajes.getMessage(anyString(), isNull(), eq(LocaleContextHolder.getLocale()))).thenReturn("Error");
        ResponseEntity<RespuestaJSON> response = userController.borraUser(userId);

        // Verificaciones
        verify(userService, times(1)).borraUser(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.getMensaje(), response.getBody().getMensaje());
    }


}
