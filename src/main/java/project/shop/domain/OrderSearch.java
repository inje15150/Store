package project.shop.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 조건 검색 - 주문회원 이름, 주문 상태로 검색하기 위한 동적 쿼리
 * */
@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
