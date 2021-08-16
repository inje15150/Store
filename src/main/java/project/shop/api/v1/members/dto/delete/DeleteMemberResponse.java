package project.shop.api.v1.members.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DeleteMemberResponse {

    private String requestURI;
    private Long id;
    private String method;
    private HttpStatus status;
}
