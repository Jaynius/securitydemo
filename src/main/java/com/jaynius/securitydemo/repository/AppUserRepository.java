package com.jaynius.securitydemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaynius.securitydemo.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    Optional<AppUser> findUserByEmail(String email);

    
}
