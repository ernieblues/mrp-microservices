package com.ernieblues.userservice.config;

import com.ernieblues.userservice.entity.User;
import com.ernieblues.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile({"local", "k8s"})
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {

        return args -> {

            if (userRepository.count() == 0) {

                List<User> users = List.of(
                        new User("michael.carter@example.com", "Michael", "Carter"),
                        new User("sarah.mitchell@example.com", "Sarah", "Mitchell"),
                        new User("david.reynolds@example.com", "David", "Reynolds"),
                        new User("lisa.anderson@example.com", "Lisa", "Anderson"),
                        new User("james.walker@example.com", "James", "Walker"),
                        new User("karen.brooks@example.com", "Karen", "Brooks")
                );

                userRepository.saveAll(users);
            }
        };
    }
}
