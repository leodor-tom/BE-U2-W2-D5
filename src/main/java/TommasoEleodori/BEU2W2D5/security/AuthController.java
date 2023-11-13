package TommasoEleodori.BEU2W2D5.security;

import TommasoEleodori.BEU2W2D5.exceptions.BadRequestException;
import TommasoEleodori.BEU2W2D5.users.LoginDTO;
import TommasoEleodori.BEU2W2D5.users.LoginSuccesDTO;
import TommasoEleodori.BEU2W2D5.users.UserDTO;
import TommasoEleodori.BEU2W2D5.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService as;
    @Autowired
    private UserService us;

    @PostMapping("/login")
    public LoginSuccesDTO login(@RequestBody LoginDTO body) {
        return new LoginSuccesDTO(as.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID saveUser(@RequestBody @Validated UserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return us.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
