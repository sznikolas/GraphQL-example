package com.nikolas.graphql.service;

import com.nikolas.graphql.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nikolas.graphql.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public List<Product> getProductsByCategory(String category){
        return repository.findByCategory(category);
    }

    public Product updateStock(int id, Integer stock){
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id " + id));
        existingProduct.setStock(stock);
        return repository.save(existingProduct);
    }

    public Product receiveNewShipment(int id, int quantity){
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id " + id));
        existingProduct.setStock(existingProduct.getStock()+quantity);
        return repository.save(existingProduct);
    }


    public void deleteProductById(int id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id " + id);
        }
        repository.deleteById(id);
    }

    public Product createProduct(String name, String category, float price, int stock) {
        Product newProduct = new Product(name, category, price, stock);
        return repository.save(newProduct);
    }
}
