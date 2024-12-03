package com.nikolas.graphql.controller;

import com.nikolas.graphql.dto.DeleteResponse;
import com.nikolas.graphql.entity.Product;
import com.nikolas.graphql.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;


    @QueryMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @QueryMapping
    public List<Product> getProductsByCategory(@Argument String category){
        return service.getProductsByCategory(category);
    }

    @MutationMapping
    public Product updateStock(@Argument int id, @Argument int stock) {
        return service.updateStock(id, stock);
    }

    @MutationMapping
//    @SubscriptionMapping
    public Product receiveNewShipment(@Argument int id, @Argument int quantity) {
        return service.receiveNewShipment(id, quantity);
    }

//    @MutationMapping
//    public Boolean deleteProductById(@Argument int id) {
//        try {
//            service.deleteProductById(id);
//            return true;
//        } catch (EntityNotFoundException ex) {
//            return false;
//        }
//    }

    @MutationMapping
    public DeleteResponse deleteProductById(@Argument int id) {
        try {
            service.deleteProductById(id);
            return new DeleteResponse(true, "Product deleted successfully", 200);
        } catch (EntityNotFoundException ex) {
            return new DeleteResponse(false, ex.getMessage(), 404);
        }
    }


    @MutationMapping
    public Product createProduct(@Argument String name, @Argument String category,
                                 @Argument float price, @Argument int stock) {
        return service.createProduct(name, category, price, stock);
    }

}
