package cl.gob.scj.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;

import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTO;
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
		UserDTO userDto = new UserDTO();
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

}
