package TommasoEleodori.BEU2W2D5.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record LoginDTO(@NotEmpty(message = "The email is mandatory")
                       @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email is not valid")
                       String email,
                       @NotEmpty(message = "The password is mandatory")
                       @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])\\S{8,15}$", message = "Your password must meet the following criteria:" +
                               " at least 8 characters long, no more than 15 characters, include uppercase and lowercase letters, contain at least one number, " +
                               "and at least one special character (e.g., @, #, $, %, etc.). Spaces are not allowed.")
                       String password) {
}
