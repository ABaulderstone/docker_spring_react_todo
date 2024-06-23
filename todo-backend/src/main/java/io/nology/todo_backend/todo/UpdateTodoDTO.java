package io.nology.todo_backend.todo;

import io.nology.todo_backend.common.validators.TemporalValidation;
import io.nology.todo_backend.common.validators.ValidDate;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTodoDTO {
    @Pattern(regexp = ".*\\S.*", message = "Title cannot be empty")
    private String title;

    @ValidDate(temporalValidation = TemporalValidation.FUTURE_OR_PRESENT)
    private String dueDate;

}
