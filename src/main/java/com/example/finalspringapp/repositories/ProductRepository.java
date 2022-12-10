package com.example.finalspringapp.repositories;

import com.example.finalspringapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleContainingIgnoreCaseAndCategoryIdAndPriceBetweenOrderByPriceAsc(String search, int categoryId, float priceStart, float priceEnd);
    List<Product> findByTitleContainingIgnoreCaseAndCategoryIdAndPriceBetweenOrderByPriceDesc(String search, int categoryId, float priceStart, float priceEnd);

}
