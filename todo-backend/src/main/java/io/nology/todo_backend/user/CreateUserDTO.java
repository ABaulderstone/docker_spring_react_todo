package io.nology.todo_backend.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
    @NotBlank
    @NotEmpty
    private String email;
    @NotBlank
    @NotEmpty
    private String password;

    public CreateUserDTO() {
    }

    @Override
    public String toString() {
        return "CreateUserDTO [email=" + email + ", password=" + password + "]";
    }

}
