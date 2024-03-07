package com.chandev.armond.domain.item;

import com.chandev.armond.domain.Category;
import com.chandev.armond.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    //== 비즈니스 로직 ==//
    /**
     * 재고 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
