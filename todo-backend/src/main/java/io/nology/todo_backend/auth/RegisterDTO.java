package io.nology.todo_backend.auth;

import io.nology.todo_backend.common.validators.Matches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @Email()
    private String email;
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character, and have no whitespace")
    private String password;
    @NotBlank
    @NotEmpty
    // @Matches(field = "password", message = "Password Confirm does not Match
    // Password")
    private String passwordConfirm;

    public RegisterDTO() {
    }
}
