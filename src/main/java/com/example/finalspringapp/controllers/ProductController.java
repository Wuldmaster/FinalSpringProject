package com.example.finalspringapp.controllers;

import com.example.finalspringapp.security.PersonDetails;
import com.example.finalspringapp.services.CategoryService;
import com.example.finalspringapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private  ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getPrincipal().equals("anonymousUser")){
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            model.addAttribute("isUser", true);
            model.addAttribute("userLogin", personDetails.getPerson().getLogin());
        } else {
            model.addAttribute("isUser", false);
        }


        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

}

