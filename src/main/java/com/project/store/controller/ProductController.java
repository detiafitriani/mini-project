package com.project.store.controller;

import com.project.store.exception.ResourceNotFoundException;
import com.project.store.model.OrderDetails;
import com.project.store.model.Orders;
import com.project.store.model.Product;
import com.project.store.repository.OrderDetailsRepository;
import com.project.store.repository.OrderRepository;
import com.project.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    OrderRepository orderRepository;


    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productRepository.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    @PostMapping("/order-details/")
    public ResponseEntity<OrderDetails> createOrderDetails(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "orderId") Long orderId,
            @RequestBody OrderDetails orderDetails) {


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));


        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));


        orderDetails.setProduct(product);
        orderDetails.setOrder(order);

        OrderDetails createdOrderDetails = orderDetailsRepository.save(orderDetails);

        return new ResponseEntity<>(createdOrderDetails, HttpStatus.CREATED);
    }





    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(value = "id") Long productId, @RequestBody Product productDetails) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product existingProduct = product.get();


            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            // ...

            return ResponseEntity.ok(productRepository.save(existingProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

