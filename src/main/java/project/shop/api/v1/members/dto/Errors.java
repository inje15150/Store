package project.shop.api.v1.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Errors<T> {

    private T errors;
}
