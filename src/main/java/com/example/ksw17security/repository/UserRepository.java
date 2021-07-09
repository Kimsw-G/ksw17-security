package com.example.ksw17security.repository;

import com.example.ksw17security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    // JPA!
    // User findByUsername => select * from user where username = #{username}
    // ++ jpa 쿼리메소드
    User findByUsername(String username);
}
