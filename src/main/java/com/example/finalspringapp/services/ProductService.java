package com.example.finalspringapp.services;

import com.example.finalspringapp.models.Image;
import com.example.finalspringapp.models.Product;
import com.example.finalspringapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    //Данный метод позволяет вернуть товар по id
    public Product getProductId(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    //Данный метод позволяет сохранить товар
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    //Данный метод позволяет обновить данные товара
    @Transactional
    public void updateProduct(int id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }

    //Данный метод позволяет удалить товар по id
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void applyImage(Product product, MultipartFile file, String path){
        if(!file.isEmpty())
        {
            File uploadDir = new File(path);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(path + "/" + resultFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
    }

    public List<Product> sortAndSearch(String search, String from, String to, String priceSort, String category){

        if(from.isEmpty()){
            from = "0";
        }

        if(to.isEmpty()){
            to="1000000";
        }

        if(priceSort.equals("sorted_by_ascending_price") || priceSort.isEmpty()){
            return productRepository
                    .findByTitleContainingIgnoreCaseAndCategoryIdAndPriceBetweenOrderByPriceAsc(search,
                            categoryService.findByName(category).getId(), Float.parseFloat(from) ,Float.parseFloat(to));
        } else {
            return productRepository
                    .findByTitleContainingIgnoreCaseAndCategoryIdAndPriceBetweenOrderByPriceDesc(search,
                            categoryService.findByName(category).getId(), Float.parseFloat(from) ,Float.parseFloat(to));
        }

    }
}
