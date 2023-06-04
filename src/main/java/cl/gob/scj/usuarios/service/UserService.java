package cl.gob.scj.usuarios.service;

import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTORequest;
import cl.gob.scj.usuarios.dto.LoginDTO;
import cl.gob.scj.usuarios.exception.SCJException;

public interface UserService {

    RespuestaJSON crearUser(UserDTORequest user) throws SCJException;

    RespuestaJSON getUsers();

    RespuestaJSON borraUser(String id);

    RespuestaJSON actualizaUser(UserDTORequest user);

    RespuestaJSON login(LoginDTO loginDTO);
    
}