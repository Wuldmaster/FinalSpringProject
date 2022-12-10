package com.example.finalspringapp.controllers;

import com.example.finalspringapp.services.CategoryService;
import com.example.finalspringapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private  ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("category", categoryService.findAll());
        return "/product/product";
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "priceSort", required = false, defaultValue = "")String priceSort, @RequestParam(value = "category", required = false, defaultValue = "")String category, Model model){
        model.addAttribute("isFlag", 1);
        model.addAttribute("search_product", productService.sortAndSearch(search, from, to, priceSort, category) );
        model.addAttribute("value_search", search);
        model.addAttribute("price_from", from);
        model.addAttribute("price_to", to);
        model.addAttribute("category", categoryService.findAll());
       // model.addAttribute("products", productService.getAllProduct());
        return "/product/product";

    }

}
