package com.example.finalspringapp.services;

import com.example.finalspringapp.models.Person;
import com.example.finalspringapp.models.Product;
import com.example.finalspringapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAllPersons() {
        return userRepository.findAll();
    }

    public Person findByLogin(Person person){
        Optional<Person> personDB = userRepository.findByLogin(person.getLogin());
        return personDB.orElse(null);
    }

    public Person getUserId(int id) {
        Optional<Person> optionalPerson = userRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public void updateUserRole(int id, String role){
        Person person = getUserId(id);
        person.setRole(role);
        userRepository.save(person);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        userRepository.save(person);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
