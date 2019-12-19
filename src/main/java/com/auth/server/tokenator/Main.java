package com.auth.server.tokenator;


import com.auth.server.tokenator.model.Role;
import com.auth.server.tokenator.model.User;
import com.auth.server.tokenator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@RestController
public class Main implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

   @Override
  public void run(String... params) {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin123456789"));
    admin.setEmail("admin@email.com");
    admin.setRoles(new ArrayList<Role>(Collections.singletonList(Role.ROLE_ADMIN)));
    userRepository.save(admin);

    User client = new User();
    client.setUsername("client");
    client.setPassword(passwordEncoder.encode("client123456789"));
    client.setEmail("client@email.com");
    client.setRoles(new ArrayList<Role>(Collections.singletonList(Role.ROLE_GUEST)));
    userRepository.save(client);
  }

}
