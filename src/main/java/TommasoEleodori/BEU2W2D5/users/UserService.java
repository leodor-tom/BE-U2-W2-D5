package TommasoEleodori.BEU2W2D5.users;

import TommasoEleodori.BEU2W2D5.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class UserService {
    @Autowired
    private UserRepository userepo;

    public Page<User> getUsers(int page, int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return userepo.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return userepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
