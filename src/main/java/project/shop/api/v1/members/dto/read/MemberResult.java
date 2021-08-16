package project.shop.api.v1.members.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResult<T> {

    private int count;
    private T data; // generic 타입으로 collect 리스트 감싸야 다른 필드 노출 가능
}
