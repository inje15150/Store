package project.shop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shop.api.v1.orders.dto.create.OrderCreateRequest;
import project.shop.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findALl() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }


    public void update(Long itemId, String itemName, Integer price, Integer stockQuantity) {

        Item item = em.find(Item.class, itemId);
        item.setName(itemName);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public Long findByName(String name) {

        return (Long) em.createQuery(
                "select i from Item i" +
                        " where i.name= :name", Item.class)
                .setParameter("name", name)
                .getParameterValue(name);
    }
}
