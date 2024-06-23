package io.nology.todo_backend.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid hex color code")
    private String color;

    public CreateCategoryDTO() {
    }

}
