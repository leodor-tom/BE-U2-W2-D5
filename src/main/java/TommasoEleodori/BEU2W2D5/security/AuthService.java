package TommasoEleodori.BEU2W2D5.security;

import TommasoEleodori.BEU2W2D5.exceptions.UnauthorizedException;
import TommasoEleodori.BEU2W2D5.users.LoginDTO;
import TommasoEleodori.BEU2W2D5.users.User;
import TommasoEleodori.BEU2W2D5.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService us;
    @Autowired
    private JWTTools tools;

    public String authenticateUser(LoginDTO body) {
        User user = us.findByEmail(body.email());
        if (body.password().equals(user.getPassword())) {
            return tools.createToken(user);
        } else {
            throw new UnauthorizedException("Login failed. Please check your username and password and try again");
        }
    }
}
