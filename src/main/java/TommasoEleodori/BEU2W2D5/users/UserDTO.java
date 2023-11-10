package TommasoEleodori.BEU2W2D5.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.aspectj.bridge.IMessage;

public record UserDTO(
        @NotEmpty(message = "The name is mandatory")
        @Size(min = 2, max = 20, message = "The name cannot contain less than 2 character and more than 20 characters")
        String name,
        @NotEmpty(message = "The surname is mandatory")
        @Size(min = 2, max = 40, message = "the surname cannot contain less than 2 characters and more than 40 characters")
        String surname,
        @NotEmpty(message = "The email is mandatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email is not valid")
        String email
) {
}
