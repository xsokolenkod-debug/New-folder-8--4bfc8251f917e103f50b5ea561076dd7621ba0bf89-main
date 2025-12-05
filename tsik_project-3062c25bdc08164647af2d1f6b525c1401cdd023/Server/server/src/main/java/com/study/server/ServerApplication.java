package com.study.server;

import com.study.server.model.User;
import com.study.server.repository.UserRepository;
import com.study.server.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ServerApplication {

        public static void main(String[] args) {
                SpringApplication.run(ServerApplication.class, args);
        }

        @Bean
        CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
                return args -> {
                        if (userRepository.count() == 0) {
                                userRepository.save(User.builder()
                                                .username("admin")
                                                .email("admin@example.com")
                                                .name("Administrator")
                                                .passwordHash(passwordEncoder.encode("1234"))
                                                .role(Role.ADMIN)
                                                .build());

                                userRepository.save(User.builder()
                                                .username("student")
                                                .email("student@example.com")
                                                .name("Student")
                                                .passwordHash(passwordEncoder.encode("abcd"))
                                                .role(Role.USER)
                                                .build());
                        }

                        System.out.println("✅ SQLite test complete. Found users:");
                        userRepository.findAll().forEach(u ->
                                        System.out.println(" - " + u.getUsername() + " (" + u.getRole() + ")"));
                };
        }
}
