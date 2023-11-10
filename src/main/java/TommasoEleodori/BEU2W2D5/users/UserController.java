package TommasoEleodori.BEU2W2D5.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
        return us.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return us.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID saveUser(@RequestBody UserDTO body) throws IOException {
        return us.save(body);
    }

    @PostMapping("/upload/{id}")
    public String uploadPicture(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) throws IOException {
        return us.uploadPicture(id, file);
    }

    @PutMapping("/{id}")
    public User userFindByIdAndUpdate(@PathVariable UUID id, @RequestBody UserDTO body) throws IOException {
        return us.findByIdAndUpdate(id, body);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String delete() {
        return " when pigs fly!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) throws IOException {
        us.findByIdAndDelete(id);
    }

    @GetMapping("/name/{name}")
    public List<User> findByName(@PathVariable String name) {
        return us.findByName(name);
    }
}
