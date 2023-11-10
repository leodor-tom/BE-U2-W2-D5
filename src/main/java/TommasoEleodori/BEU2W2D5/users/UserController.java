package TommasoEleodori.BEU2W2D5.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "surname") String orderBy) {
        return us.getUsers(page,size,orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {return us.findById(id);}
}
