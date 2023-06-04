package cl.gob.scj.usuarios.repository;

import org.springframework.data.repository.CrudRepository;

import cl.gob.scj.usuarios.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Integer>  {
    
}