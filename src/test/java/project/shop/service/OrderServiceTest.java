package project.shop.service;

import com.sun.source.tree.LambdaExpressionTree;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.shop.domain.Address;
import project.shop.domain.Member;
import project.shop.domain.Order;
import project.shop.domain.OrderStatus;
import project.shop.domain.item.Book;
import project.shop.domain.item.Item;
import project.shop.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void orderItemTest() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(getOrder.getOrderItems().size(), 1);
        assertEquals(getOrder.getTotalPrice(), 20000);
        assertEquals(item.getStockQuantity(), 8);
    }

    @Test
    void orderItem_Stock_Over() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order findOrder = orderRepository.findOne(orderId);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.(NoEnoughStockException)");

    }

    @Test
    void order_cancel() {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(item.getStockQuantity(), 10);

    }



    public Member createMember() {
        Member member = new Member();
        member.setName("user1");
        member.setAddress(createAdd("서울", "강가", "123-123"));

        em.persist(member);

        return member;
    }

    public Book createBook(String name, int price, int stockQuantity) {

        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    public Address createAdd(String city, String street, String zipcode) {
        return new Address(city, street, zipcode);
    }



}