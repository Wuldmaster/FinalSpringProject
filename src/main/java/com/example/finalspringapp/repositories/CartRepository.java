package com.example.finalspringapp.repositories;

import com.example.finalspringapp.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByPersonId(int id);

    void deleteCartByProductId(int id);

    void deleteByPersonIdAndProductId(int personId, int productId);
}
