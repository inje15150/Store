package project.shop.domain.item;

import lombok.Getter;
import lombok.Setter;
import project.shop.domain.Category;
import project.shop.exception.NoEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //== 비즈니스 로직 ==//
    public void addStock(int quantity) { //주문 취소 및 재고 추가 시 재고 수량 증가 로직
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) { // 주문 시 재고 수량 감소 로직
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) { // 재고가 0보다 작으면 Exception 발생
            throw new NoEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
