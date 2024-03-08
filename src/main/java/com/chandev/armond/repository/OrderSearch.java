package com.chandev.armond.repository;

import com.chandev.armond.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
