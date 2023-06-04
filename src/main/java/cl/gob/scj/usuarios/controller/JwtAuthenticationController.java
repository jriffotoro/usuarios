package cl.gob.scj.usuarios.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Value;

import cl.gob.scj.usuarios.dto.LoginDTO;
import cl.gob.scj.usuarios.dto.RespuestaJSON;
import cl.gob.scj.usuarios.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource mensajes;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${login.username}")
	private String loginName;

	@Value("${login.password}")
	private String loginPassword;

    @PostMapping("/login")
    public ResponseEntity<RespuestaJSON> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
			authenticate(loginDTO.getName(), loginDTO.getPassword());
            loginDTO.setToken(getJWTToken(loginDTO.getName()));
            return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RespuestaJSON(RespuestaJSON.EstadoType.ERROR.getRespuestaJSONS(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getJWTToken(String username) {       
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
					secret.getBytes()).compact();

		return "Bearer " + token;
	}

	private void authenticate(String name, String password) throws Exception {
		if (!name.equals(loginName) || !password.equals(loginPassword)) {
			throw new BadCredentialsException(mensajes.getMessage("login.invalid-credentials", null, LocaleContextHolder.getLocale()));
		}
	}

}