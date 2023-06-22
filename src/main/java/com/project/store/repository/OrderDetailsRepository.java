package com.project.store.repository;

import com.project.store.model.OrderDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

//    @Transactional
//    void deleteById(long id);
//
//    @Transactional
//    void deleteByOrderId(long orderId);
}
