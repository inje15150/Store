package project.shop.api.v1.members.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {

    private String requestURI;
    private String method;
    private Long id;
    private String name;
    private HttpStatus status;

}
