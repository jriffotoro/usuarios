package cl.gob.scj.usuarios.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserDTORequest {
    
    private String id;

    private String name;

    @Email
    @NotEmpty
    private String email; 

    @Pattern.List({
        @Pattern(regexp = "[^0123456789]*\\d[^0123456789]*\\d[^0123456789]*", message = "La password debe contener al menos dos digitos."),
        @Pattern(regexp = "(?=.*[a-z]).+", message = "La password debe contener al menos una letra minuscula."),
        @Pattern(regexp = "[^[A-Z]]*[A-Z]{1}[^[A-Z]]*", message = "La password debe contener una letra mayuscula."),
        @Pattern(regexp = "(?=\\S+$).+", message = "La password no debe contener espacios en blanco")
    })
    private String password;

    private String token;
    private List<PhoneDTO> phones;

    private Boolean isactive;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<PhoneDTO> getPhones() {
        return this.phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }


    public Boolean isIsactive() {
        return this.isactive;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

}
