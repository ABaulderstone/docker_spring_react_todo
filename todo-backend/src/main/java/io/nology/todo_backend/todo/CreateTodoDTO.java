package io.nology.todo_backend.todo;

import io.nology.todo_backend.common.validators.TemporalValidation;
import io.nology.todo_backend.common.validators.ValidDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoDTO {

    @NotEmpty
    @NotBlank
    private String title;

    @NotEmpty
    @NotBlank
    @ValidDate(temporalValidation = TemporalValidation.FUTURE_OR_PRESENT)
    private String dueDate;

    @Min(1)
    private Long categoryId;

    public CreateTodoDTO() {
    }

}
