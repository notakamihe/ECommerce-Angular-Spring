package com.michaelakamihe.ecommercebackend.repo;

import com.michaelakamihe.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername (String username);
}
