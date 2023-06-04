package cl.gob.scj.usuarios.dto;

import javax.validation.constraints.Pattern;

public class LoginDTO {

    private String name;

    @Pattern.List({
        @Pattern(regexp = "[^0123456789]*\\d[^0123456789]*\\d[^0123456789]*", message = "La password debe contener al menos dos digitos."),
        @Pattern(regexp = "(?=.*[a-z]).+", message = "La password debe contener al menos una letra minuscula."),
        @Pattern(regexp = "[^[A-Z]]*[A-Z]{1}[^[A-Z]]*", message = "La password debe contener una letra mayuscula."),
        @Pattern(regexp = "(?=\\S+$).+", message = "La password no debe contener espacios en blanco")
    })
    private String password;

    private String token;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}