package project.shop.repository.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.domain.item.Item;

public interface SpringJpaItemRepository extends JpaRepository<Item, Long> {

}
