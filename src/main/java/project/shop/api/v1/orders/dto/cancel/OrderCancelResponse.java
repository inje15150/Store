package project.shop.api.v1.orders.dto.cancel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderCancelResponse {

    private String requestURI;
    private String method;
    private HttpStatus status;
    private LocalDateTime orderCancelTime;
}
