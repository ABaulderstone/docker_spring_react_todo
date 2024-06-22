import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class CreateTodoDTO {

    @NotEmpty
    @NotBlank
    private String title;

    private String dueDate;
}
