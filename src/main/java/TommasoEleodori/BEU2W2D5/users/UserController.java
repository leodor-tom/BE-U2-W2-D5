package TommasoEleodori.BEU2W2D5.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "surname") String orderBy) {
        return us.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public User findById(@PathVariable UUID id) {
        return us.findById(id);
    }


    @PostMapping("/upload/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String uploadPicture(@PathVariable UUID id, @RequestParam("avatar") MultipartFile file) throws IOException {
        return us.uploadPicture(id, file);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User userFindByIdAndUpdate(@PathVariable UUID id, @RequestBody UserDTO body) throws IOException {
        return us.findByIdAndUpdate(id, body);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findByIdAndUpdateRole(@PathVariable UUID id, @RequestBody RoleDTO body) throws IOException {
        return us.findByIdAndPatchRole(id, body);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String delete() {
        return " when pigs fly!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) throws IOException {
        us.findByIdAndDelete(id);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findByName(@PathVariable String name) {
        return us.findByName(name);
    }

    @GetMapping("/surname/{surname}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findBySurname(@PathVariable String surname) {
        return us.findBySurname(surname);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public UserDetails putProfile(@AuthenticationPrincipal User currentUser, @RequestBody UserDTO body) throws IOException {
        return us.findByIdAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentUser) throws IOException {
        us.findByIdAndDelete(currentUser.getId());
    }
}
