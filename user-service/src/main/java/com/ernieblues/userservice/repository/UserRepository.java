package com.ernieblues.userservice.repository;

import com.ernieblues.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {
}
