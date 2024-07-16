package com.vehicleinventorysystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vehicleinventorysystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User save(User user);

	User findByUserNameAndPassword(String username, String password);

	User findByUserName(String username);
}
