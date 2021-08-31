package project.shop.repository.springdatajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.shop.domain.Order;
import project.shop.domain.OrderStatus;

import java.util.List;

public interface SpringJpaOrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o")
    @EntityGraph(attributePaths = {"member", "delivery"})
    List<Order> findOrders();

    @Query("select o from Order o join fetch o.member m where m.name like '%'||:username||'%' and o.status like '%'||:status||'%'")
    List<Order> findByUsernameAndStatus(@Param("username") String username,
                                        @Param("status") String status);

    @Query("select o from Order o join fetch o.member m where m.name like '%'||:username||'%'")
    List<Order> findByName(@Param("username") String username);

    @Query("select o from Order o where o.status like '%'||:status||'%'")
    List<Order> findByStatus(@Param("status") String status);

}
