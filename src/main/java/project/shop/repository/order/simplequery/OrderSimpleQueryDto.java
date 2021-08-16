package project.shop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shop.api.v1.orders.dto.read.OrderDto;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryDto {

    private final EntityManager em;

    public List<OrderDto> findOrderDto() {
        return em.createQuery(
                "select new project.shop.api.v1.orders.dto.read.OrderDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderDto.class)
                .getResultList();
    }
}
