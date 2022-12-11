package com.example.finalspringapp.controllers;

import com.example.finalspringapp.models.Order;
import com.example.finalspringapp.models.Product;
import com.example.finalspringapp.services.CategoryService;
import com.example.finalspringapp.services.OrderService;
import com.example.finalspringapp.services.ProductService;
import com.example.finalspringapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private OrderService orderService;


    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public AdminController(ProductService productService, CategoryService categoryService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String admin(){
        return "admin/admin";
    }

    @GetMapping("/product")
    public String productView(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "admin/productView";
    }

    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryService.findAll());

        return "product/addProduct";
    }


    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("fileOne")MultipartFile fileOne, @RequestParam("fileTwo")MultipartFile fileTwo, @RequestParam("fileThree")MultipartFile fileThree, @RequestParam("fileFour")MultipartFile fileFour, @RequestParam("fileFive") MultipartFile fileFive) {

        if(bindingResult.hasErrors())
        {
            return "product/addProduct";
        }

        productService.applyImage(product, fileOne, uploadPath);
        productService.applyImage(product, fileTwo, uploadPath);
        productService.applyImage(product, fileThree, uploadPath);
        productService.applyImage(product, fileFour, uploadPath);
        productService.applyImage(product, fileFive, uploadPath);

        productService.saveProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryService.findAll());
        return "product/editProduct";
    }


    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
        {
            return "product/editProduct";
        }

        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    @GetMapping("/product/info/{id}")
    public String infoAdminProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        return "admin/productInfo";
    }

    //// USER


    @GetMapping("/user")
    public String userView(Model model){
        model.addAttribute("users", userService.getAllPersons());
        return "admin/userView";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getUserId(id));
        return "user/editUser";
    }


    @PostMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") int id, @RequestParam("role")String role){

        userService.updateUserRole(id, role);
        return "redirect:/admin";
    }

    @GetMapping("/user/info/{id}")
    public String infoAdminUser(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getUserId(id));
        model.addAttribute("orders", orderService.findByPerson(userService.getUserId(id)));
        return "admin/userInfo";
    }


    //// ORDER

    @GetMapping("/order")
    public String orderView(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "admin/orderView";
    }


    @GetMapping("/order/info/{id}")
    public String infoAdminOrder(Model model, @PathVariable("id") int id){
        model.addAttribute("order", orderService.getOrderId(id));
        return "admin/orderInfo";
    }

    @PostMapping("/order/search")
    public String orderSearch(@RequestParam("search") String search, Model model){
        model.addAttribute("search", search);
        model.addAttribute("orders", orderService.findByLastNumbers(search));
        return "/admin/orderView";

    }

    @GetMapping("/order/edit/{id}")
    public String editOrder(Model model, @PathVariable("id") int id){
        model.addAttribute("order", orderService.getOrderId(id));
        return "admin/editOrder";
    }

    @PostMapping("/order/edit/{id}")
    public String editOrder(@RequestParam("status") String status, @PathVariable("id") int id){

        orderService.updateOrder(id, status);
        return "redirect:/admin/order";
    }
}
