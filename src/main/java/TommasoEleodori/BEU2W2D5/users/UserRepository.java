package TommasoEleodori.BEU2W2D5.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByNameIgnoreCase(String name);

    List<User> findBySurnameIgnoreCase(String surname);

    Optional<User> findByUsernameIgnoreCase(String username);

}
