package com.umbrella.Dev.repository;

import com.umbrella.Dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
