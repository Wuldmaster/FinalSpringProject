package com.example.finalspringapp.controllers;

import com.example.finalspringapp.models.Cart;
import com.example.finalspringapp.models.Product;
import com.example.finalspringapp.security.PersonDetails;
import com.example.finalspringapp.services.CartService;
import com.example.finalspringapp.services.CategoryService;
import com.example.finalspringapp.services.OrderService;
import com.example.finalspringapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private ProductService productService;
    private OrderService orderService;
    private CartService cartService;
    private CategoryService categoryService;


    @Autowired
    public UserController(ProductService productService, OrderService orderService, CartService cartService, CategoryService categoryService) {
        this.productService = productService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String index(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            String role = personDetails.getPerson().getRole();
            model.addAttribute("userLogin", personDetails.getPerson().getLogin());
            model.addAttribute("isUser", true);

            if(role.equals("ROLE_ADMIN"))
            {
                return "redirect:/admin";
            }

        } else {
            model.addAttribute("isUser", false);
        }


        model.addAttribute("category", categoryService.findAll());
        model.addAttribute("products", productService.getAllProduct());
        return "user/index";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Product product = productService.getProductId(id);
        model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        cartService.addProductToCart(personDetails.getPerson().getId(), product.getId());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        List<Cart> cartList = cartService.findByPersonId(personDetails.getPerson().getId());
        List<Product> productsList = new ArrayList<>();

        for (Cart cart: cartList) {
            productsList.add(productService.getProductId(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productsList) {
            price += product.getPrice();
        }
        model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        model.addAttribute("price", price);
        model.addAttribute("cart_product", productsList);
        return "user/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProductCart(@PathVariable("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        cartService.deleteByPersonIdAndProductId(personDetails.getPerson().getId(), id);
        return "redirect:/cart";
    }

    @GetMapping("/order/create")
    public String order(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        orderService.createOrder(personDetails);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        model.addAttribute("orders", orderService.findByPerson(personDetails.getPerson()));
        return "/user/orders";
    }

    @PostMapping("/index/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "priceSort", required = false, defaultValue = "")String priceSort, @RequestParam(value = "category", required = false, defaultValue = "")String category, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            model.addAttribute("isUser", true);
            model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        } else {
            model.addAttribute("isUser", false);
        }

        model.addAttribute("isFlag", 1);
        model.addAttribute("search_product", productService.sortAndSearch(search, from, to, priceSort, category) );
        model.addAttribute("search", search);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("category", categoryService.findAll());
        return "/user/index";

    }
}
