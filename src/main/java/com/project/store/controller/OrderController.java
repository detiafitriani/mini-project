package com.project.store.controller;

import com.project.store.exception.ResourceNotFoundException;
import com.project.store.model.Orders;
import com.project.store.repository.OrderDetailsRepository;
import com.project.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        order.setDate_order(LocalDate.now());
        order.setTime_order(LocalTime.now());

        Orders createdOrder = orderRepository.save(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            updatedOrder.setId(id);
            Orders savedOrder = orderRepository.save(updatedOrder);
            return new ResponseEntity<>(savedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders")
    public ResponseEntity<HttpStatus> deleteAllOrders(){
        orderRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
