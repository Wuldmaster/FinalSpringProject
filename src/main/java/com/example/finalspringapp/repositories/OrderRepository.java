package com.example.finalspringapp.repositories;

import com.example.finalspringapp.models.Order;
import com.example.finalspringapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPerson(Person person);

    List<Order> findByNumberEndsWith(String number);

    void deleteByPersonId(int id);
}
