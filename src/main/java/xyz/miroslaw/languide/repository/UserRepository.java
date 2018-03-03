package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.User;


public interface  UserRepository extends CrudRepository<User, Long>{
    User findByName(String name);
    User findByEmail(String email);

}
