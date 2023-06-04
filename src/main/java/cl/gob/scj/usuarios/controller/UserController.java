package cl.gob.scj.usuarios.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.dto.UserDTO;
import cl.gob.scj.usuarios.exception.SCJException;
import cl.gob.scj.usuarios.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource mensajes;

    @PostMapping("/user")
    public ResponseEntity<RespuestaJSON> crearUser(@RequestBody UserDTO user, HttpServletRequest request) {
        try {
            user.setToken((String)request.getAttribute("jwtToken"));
            return new ResponseEntity<>(userService.crearUser(user), HttpStatus.CREATED);
        } catch (SCJException e) {
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), e.getMessage()), HttpStatus.ALREADY_REPORTED);
        } catch (Exception e) {
            StringWriter sw1 = new StringWriter();
            e.printStackTrace(new PrintWriter(sw1));
            String exceptionAsString1 = sw1.toString();
            System.out.println(exceptionAsString1);
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), mensajes.getMessage("user.error", null, LocaleContextHolder.getLocale())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<RespuestaJSON> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), mensajes.getMessage("user.error", null, LocaleContextHolder.getLocale())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<RespuestaJSON> borraUser(@PathVariable String id) {
        try {
            RespuestaJSON r = userService.borraUser(id);
            if (r.getMensaje().equals(mensajes.getMessage("user.no-encontrado", null, LocaleContextHolder.getLocale()))) {
                return new ResponseEntity<>(r, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(r, HttpStatus.ACCEPTED); 
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), mensajes.getMessage("user.error", null, LocaleContextHolder.getLocale())), HttpStatus.INTERNAL_SERVER_ERROR);            
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<RespuestaJSON> actualizaUser(@PathVariable String id, @RequestBody UserDTO user) {
        try {            
            user.setId(id);
            RespuestaJSON r = userService.actualizaUser(user);
            if (r.getMensaje().equals(mensajes.getMessage("user.no-encontrado", null, LocaleContextHolder.getLocale()))) {
                return new ResponseEntity<>(r, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(r, HttpStatus.ACCEPTED); 
        } catch (Exception e) {
            StringWriter sw1 = new StringWriter();
            e.printStackTrace(new PrintWriter(sw1));
            String exceptionAsString1 = sw1.toString();
            System.out.println(exceptionAsString1);
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), mensajes.getMessage("user.error", null, LocaleContextHolder.getLocale())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getJWTToken(String username) {
		String secretKey = "mySecretKey";        
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}