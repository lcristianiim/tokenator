package com.auth.server.tokenator.repository;



import com.auth.server.tokenator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);

  User findByEmail(String email);

  @Transactional
  void deleteByEmail(String email);

}
