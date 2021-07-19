package project.shop.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AddressDto {

    private String city;
    private String street;
    private String zipcode;
}
