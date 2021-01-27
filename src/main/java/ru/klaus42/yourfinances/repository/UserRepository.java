package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.klaus42.yourfinances.entity.User;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Query("select u from User u where u.email = ?1 or u.name=?1")
    User findByUsername(String username);
}