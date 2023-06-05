package cl.gob.scj.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.TestPropertySource;

import cl.gob.scj.usuarios.dto.PhoneDTO;
import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTO;
import cl.gob.scj.usuarios.dto.UserDTORequest;
import cl.gob.scj.usuarios.exception.SCJException;
import cl.gob.scj.usuarios.model.Phone;
import cl.gob.scj.usuarios.model.User;
import cl.gob.scj.usuarios.repository.PhoneRepository;
import cl.gob.scj.usuarios.repository.UserRepository;

@SpringBootTest
@TestPropertySource("classpath:messages.properties")
class UserServiceImplTests {

	private static final String idUser = "c2667cf9-78a1-4f68-aff1-bda90957e6fa";

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PhoneRepository phoneRepository;

	@MockBean
	private MessageSource mensajes;

	@Test
	void crearUserTest() throws SCJException {
		//Arrage
		User user = new User();
		UserDTORequest userDto = new UserDTORequest();
		userDto.setName("scj");
		userDto.setEmail("scj@scj.gob.cl");
		userDto.setPassword("Bgfdgfdg23dsfds");
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, phoneRepository, mensajes);
		when(userRepository.email(anyString())).thenReturn(Optional.empty());
    	when(userRepository.save(user)).thenReturn(any(User.class));
		//Act
		RespuestaJSON r = userServiceImpl.crearUser(userDto);
		//Assert
		assertNotNull(r);
		assertEquals(((User)r.getAdicional()).getName(), userDto.getName());
	}

	@Test 
	void getUsersTest() {
		//Arrage
		User user = new User();
		user.setName("scj");
		User user2 = new User();
		User user3 = new User();
		List<User> usuarios = new ArrayList<User>();
		usuarios.add(user);
		usuarios.add(user2);
		usuarios.add(user3);
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, phoneRepository, mensajes);
		when(userRepository.findAll()).thenReturn(usuarios);
		//Act
		RespuestaJSON r = userServiceImpl.getUsers();
		//Assert
		assertNotNull(r);
		assertEquals(((ArrayList<UserDTO>)r.getAdicional()).get(0).getName(), usuarios.get(0).getName());
	}

	@Test
	void borrarUserTest() throws SCJException {
		//Arrage
		User user = new User();
		user.setName("scj");
		user.setEmail("scj@scj.gob.cl");
		user.setPassword("Bgfdgfdg23dsfds");
		UserDTO userDto = new UserDTO();
		userDto.setName("scj");
		userDto.setEmail("scj@scj.gob.cl");
		userDto.setPassword("Bgfdgfdg23dsfds");
		Optional<User> userOpt = Optional.ofNullable(user);
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, phoneRepository, mensajes);
		when(userRepository.findById(idUser)).thenReturn(userOpt);
		doNothing().when(phoneRepository).delete(any(Phone.class));
        doNothing().when(userRepository).delete(user);
		//Act
		RespuestaJSON r = userServiceImpl.borraUser(idUser);
		//Assert
		assertNotNull(r);
		assertEquals(((UserDTO)r.getAdicional()).getName(), userDto.getName());
	}

	@Test
    public void testActualizaUserUserExists() {
        // Datos de prueba
        UserDTORequest userDTORequest = new UserDTORequest();
        userDTORequest.setId(idUser);
        userDTORequest.setEmail("newemail@example.com");
        userDTORequest.setIsactive(true);
        userDTORequest.setName("John Doe");
        userDTORequest.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(idUser);
        existingUser.setEmail("oldemail@example.com");
        existingUser.setIsactive(false);
        existingUser.setName("Jane Doe");
        existingUser.setPassword("oldPassword");

        List<PhoneDTO> phoneDTOs = new ArrayList<>();
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("123456789");
        phoneDTO.setCitycode("123");
		phoneDTO.setContrycode("56");

        phoneDTOs.add(phoneDTO);
        userDTORequest.setPhones(phoneDTOs);

		RespuestaJSON respuestaJSON = new RespuestaJSON(RespuestaJSON.EstadoType.OK.getRespuestaJSONS(), "User updated", existingUser);

        // Mock del repositorio para devolver el usuario existente
        when(userRepository.findById(userDTORequest.getId())).thenReturn(Optional.of(existingUser));

        // Mock de la traducción del mensaje
        when(mensajes.getMessage(anyString(), any(), any())).thenReturn("User updated");

		doNothing().when(phoneRepository).delete(any(Phone.class));
		when(phoneRepository.save(any(Phone.class))).thenReturn(any(Phone.class));

        // Llamada al método que se va a probar
		UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, phoneRepository, mensajes);
        RespuestaJSON respuesta = userServiceImpl.actualizaUser(userDTORequest);

        // Verificaciones
        assertEquals(respuestaJSON.getMensaje(), respuesta.getMensaje());
        assertEquals("User updated", respuesta.getMensaje());

        // Verificaciones adicionales según los cambios hechos en el método
        assertEquals(userDTORequest.getEmail(), existingUser.getEmail());
        assertEquals(userDTORequest.getIsactive(), existingUser.getIsactive());
        assertEquals(userDTORequest.getName(), existingUser.getName());
        assertEquals(userDTORequest.getPassword(), existingUser.getPassword());
        assertNotNull(existingUser.getLast_login());
        assertNotNull(existingUser.getModified());
        assertEquals(1, existingUser.getPhones().size());

        // Verificación de llamadas al repositorio
        verify(userRepository, times(1)).findById(userDTORequest.getId());
        verify(phoneRepository, times(0)).delete(any(Phone.class));
        verify(phoneRepository, times(1)).save(any(Phone.class));
    }

}
