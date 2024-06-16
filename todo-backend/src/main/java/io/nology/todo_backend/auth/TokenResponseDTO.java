package io.nology.todo_backend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
    private String token;

    TokenResponseDTO() {
    }

}
