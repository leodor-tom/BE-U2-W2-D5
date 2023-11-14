package TommasoEleodori.BEU2W2D5.users;

import TommasoEleodori.BEU2W2D5.users.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RoleDTO(@NotNull(message = "The role is manadatory")
                      Role role) {
}
