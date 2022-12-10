package com.example.finalspringapp.repositories;

import com.example.finalspringapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);
}
