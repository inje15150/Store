package project.shop.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.shop.api.v1.orders.dto.create.OrderCreateRequest;
import project.shop.domain.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
            return item.getId();
        } else {
            em.merge(item);
        }
        return null;
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

    public List<Item> findByCondition(String itemName, Integer price, String sign) {

        String jpql = "select i from Item i";
        boolean isFirstCondition = true;

        if (StringUtils.hasText(itemName)) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " i.name like '%'||:name||'%'";
        }

        if (StringUtils.hasText(String.valueOf(price))) {

            if (sign != null) {
                if (isFirstCondition) {
                    jpql += " where";
                    isFirstCondition = false;
                } else {
                    jpql += " and";
                }
                jpql += " i.price " + sign + " :price";
            } else {
                if (isFirstCondition) {
                    jpql += " where";
                    isFirstCondition = false;
                } else {
                    jpql += " and";
                }
                jpql += " i.price = :price";
            }
        }

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);

        if (StringUtils.hasText(itemName)) {
            query = query.setParameter("name", itemName);
        }
        if (StringUtils.hasText(String.valueOf(price))) {
            query = query.setParameter("price", price);
        }
        return query.getResultList();
    }

    public Item findByName(String name) {

        if (name == null) {
            return em.createQuery(
                    "select i from Item i", Item.class)
                    .getSingleResult();
        }

        return em.createQuery(
                "select i from Item i" +
                        " where i.name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void delete(Long itemId) {
        Item findItem = findOne(itemId);
        em.remove(findItem);
    }


}
