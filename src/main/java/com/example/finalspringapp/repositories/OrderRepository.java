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

    @Query(value = "select * from orders where lower(number) LIKE '%?1'", nativeQuery = true)
    List<Order> findByLastNumbers(String number);

    List<Order> findByNumberEndsWith(String number);
}
