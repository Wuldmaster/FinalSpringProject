package com.example.finalspringapp.services;

import com.example.finalspringapp.models.Cart;
import com.example.finalspringapp.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findByPersonId(int id){
        return cartRepository.findByPersonId(id);
    }

    public void addProductToCart(int personId, int productId){
        cartRepository.save(new Cart(personId, productId));
    }

    public void deleteByProductId(int id){
        cartRepository.deleteCartByProductId(id);
    }

    public void deleteByPersonIdAndProductId(int personId, int productId){
        cartRepository.deleteByPersonIdAndProductId(personId,productId);
    }
}
