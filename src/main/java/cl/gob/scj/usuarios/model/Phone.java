package cl.gob.scj.usuarios.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="phone")
public class Phone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_phone;
    private String number;
    private String citycode;
    private String contrycode;
    @ManyToOne
	@JoinColumn(name="id")
    private User user;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return this.citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return this.contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }     

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }      

    public Integer getId_phone() {
        return this.id_phone;
    }

    public void setId_phone(Integer id_phone) {
        this.id_phone = id_phone;
    }    

    @Override
    public String toString() {
        return "{" +
            " id_phone='" + getId_phone() + "'" +
            ", number='" + getNumber() + "'" +
            ", citycode='" + getCitycode() + "'" +
            ", contrycode='" + getContrycode() + "'" +
            "}";
    }

}