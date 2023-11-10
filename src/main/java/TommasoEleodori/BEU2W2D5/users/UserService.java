package TommasoEleodori.BEU2W2D5.users;

import TommasoEleodori.BEU2W2D5.config.EmailSender;
import TommasoEleodori.BEU2W2D5.exceptions.BadRequestException;
import TommasoEleodori.BEU2W2D5.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userepo;
    @Autowired
    private EmailSender emailsdr;
    @Autowired
    private Cloudinary cloudinary;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userepo.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return userepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public UUID save(UserDTO body) throws BadRequestException, IOException {
        userepo.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The email: " + user.getEmail() + "it's already used");
        });
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setUsername(body.name() + "_" + body.surname());
        user.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        userepo.save(user);
        emailsdr.sendRegistrationEmail(user.getEmail(), user.getName());
        return user.getId();
    }

    public String uploadPicture(UUID id, MultipartFile file) throws NotFoundException, IOException {
        User found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        userepo.save(found);
        return found.getAvatar();
    }

    public User findByIdAndUpdate(UUID id, UserDTO body) throws NotFoundException, IOException {
        User found = this.findById(id);
        if (found.getAvatar().equals("http://ui-avatars.com/api/?name=" + found.getName() + "+" + found.getSurname())) {
            found.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        }
        if (!found.getEmail().equals(body.email())) {
            userepo.findByEmail(body.email()).ifPresent(user -> {
                throw new BadRequestException("The email: " + user.getEmail() + "it's already used");
            });
        }
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setUsername(body.name() + "_" + body.surname());
        userepo.save(found);
        emailsdr.sendUpdateAccountEmail(found.getEmail(), found.getName());
        return found;
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException, IOException {
        User found = this.findById(id);
        userepo.delete(found);
        emailsdr.sendDeletedAccountEmail(found.getEmail(), found.getName());
    }

}
