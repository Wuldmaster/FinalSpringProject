package com.example.finalspringapp.services;

import com.example.finalspringapp.enums.Status;
import com.example.finalspringapp.models.Cart;
import com.example.finalspringapp.models.Order;
import com.example.finalspringapp.models.Person;
import com.example.finalspringapp.models.Product;
import com.example.finalspringapp.repositories.OrderRepository;
import com.example.finalspringapp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CartService cartService;
    private ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    public List<Order> findByPerson(Person person){
        return orderRepository.findByPerson(person);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order getOrderId(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public List<Order> findByLastNumbers(String number){
        return orderRepository.findByNumberEndsWith(number);
    }

    @Transactional
    public void updateOrder(int id, String status){
        Order order = getOrderId(id);
        chooseStatus(order, status);
        orderRepository.save(order);
    }

    @Transactional
    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public void createOrder(PersonDetails personDetails){
        List<Cart> cartList = cartService.findByPersonId(personDetails.getPerson().getId());
        List<Product> productsList = new ArrayList<>();

        for (Cart cart: cartList) {
            productsList.add(productService.getProductId(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productsList){
            price += product.getPrice();
        }

        String uuid = UUID.randomUUID().toString();
        for (Product product: productsList){
            Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.Оформлен);
            saveOrder(newOrder);
            cartService.deleteByPersonIdAndProductId(personDetails.getPerson().getId(), product.getId());
        }
    }

    private void chooseStatus(Order order, String status){
        switch (status) {
            //Оформлен, Отправлен, Ожидает, Получен
            case "Оформлен" -> order.setStatus(Status.Оформлен);
            case "Отправлен" -> order.setStatus(Status.Отправлен);
            case "Ожидает" -> order.setStatus(Status.Ожидает);
            case "Получен" -> order.setStatus(Status.Получен);
        }
    }
}
