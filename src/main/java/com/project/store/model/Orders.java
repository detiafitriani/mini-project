package com.project.store.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Entity
    @Table(name = "orders")
    public class Orders {
            @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
            private Long id;

            @Column(name = "date_order")
            private LocalDate date_order;

            @Column(name = "time_order")
            private LocalTime time_order;

        public Orders() {
        }

        public Orders(Long id, LocalDate date_order, LocalTime time_order) {
            this.id = id;
            this.date_order = date_order;
            this.time_order = time_order;
        }

        public Orders(LocalDate date_order, LocalTime time_order) {
            this.date_order = date_order;
            this.time_order = time_order;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_order() {
                return this.date_order;
            }

            public void setDate_order(LocalDate date_order) {
                this.date_order = date_order;
            }

            public LocalTime getTime_order() {
                return time_order;
            }

            public void setTime_order(LocalTime time_order) {
                this.time_order = time_order;
            }

}
