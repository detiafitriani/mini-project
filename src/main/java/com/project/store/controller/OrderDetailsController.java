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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable(value = "id") Long orderDetailsId) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(orderDetailsId);
        if (orderDetails.isPresent()) {
            return ResponseEntity.ok().body(orderDetails.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/orders/{orderId}/details")
    public ResponseEntity<OrderDetails> createOrderDetails(
            @PathVariable(value = "orderId") Long orderId, @RequestParam(value = "productId") Long productId,
            @RequestBody OrderDetails orderDetails) {

        // Mendapatkan Order berdasarkan orderId
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Mendapatkan Product berdasarkan productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Menetapkan Order dan Product pada OrderDetails
        orderDetails.setOrder(order);
        orderDetails.setProduct(product);

        OrderDetails createdOrderDetails = orderDetailsRepository.save(orderDetails);

        return new ResponseEntity<>(createdOrderDetails, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetails(
            @PathVariable(value = "id") Long orderDetailsId, @RequestBody OrderDetails orderDetailsDetails) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(orderDetailsId);
        if (orderDetails.isPresent()) {
            OrderDetails existingOrderDetails = orderDetails.get();


            existingOrderDetails.setQuantity(orderDetailsDetails.getQuantity());
            existingOrderDetails.setTotal(orderDetailsDetails.getTotal());


            return ResponseEntity.ok(orderDetailsRepository.save(existingOrderDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllDetails(){
        orderDetailsRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable(value = "id") Long orderDetailsId) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(orderDetailsId);
        if (orderDetails.isPresent()) {
            orderDetailsRepository.deleteById(orderDetailsId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

