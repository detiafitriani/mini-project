package com.project.store.repository;

import com.project.store.model.OrderDetails;
import com.project.store.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
