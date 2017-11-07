package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.User;


interface  UserRepository extends CrudRepository<User, Long>{
}
