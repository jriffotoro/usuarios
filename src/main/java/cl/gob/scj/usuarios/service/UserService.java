package cl.gob.scj.usuarios.service;

import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTO;
import cl.gob.scj.usuarios.dto.LoginDTO;
import cl.gob.scj.usuarios.exception.SCJException;

public interface UserService {

    RespuestaJSON crearUser(UserDTO user) throws SCJException;

    RespuestaJSON getUsers();

    RespuestaJSON borraUser(String id);

    RespuestaJSON actualizaUser(UserDTO user);

    RespuestaJSON login(LoginDTO loginDTO);
    
}