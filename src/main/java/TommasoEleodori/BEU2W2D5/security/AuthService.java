package TommasoEleodori.BEU2W2D5.security;

import TommasoEleodori.BEU2W2D5.config.EmailSender;
import TommasoEleodori.BEU2W2D5.exceptions.BadRequestException;
import TommasoEleodori.BEU2W2D5.exceptions.UnauthorizedException;
import TommasoEleodori.BEU2W2D5.users.*;
import TommasoEleodori.BEU2W2D5.users.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserService us;
    @Autowired
    private JWTTools tools;

    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private EmailSender emailsdr;


    public String authenticateUser(LoginDTO body) {
        User user = us.findByEmail(body.email());
        SubjectDTO subjectDTO = new SubjectDTO(String.valueOf(user.getSurname()), String.valueOf(user.getRole()));
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return tools.createToken(subjectDTO);
        } else {
            throw new UnauthorizedException("Login failed. Please check your username and password and try again");
        }
    }

    public UUID save(UserDTO body) throws BadRequestException, IOException {
        ur.findByEmailIgnoreCase(body.email()).ifPresent(user -> {
            throw new BadRequestException("The email: " + user.getEmail() + "it's already used");
        });
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setUsername(body.name() + "_" + body.surname());
        user.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        user.setRole(Role.USER);
        ur.save(user);
        emailsdr.sendRegistrationEmail(user.getEmail(), user.getName());
        return user.getId();
    }
}
