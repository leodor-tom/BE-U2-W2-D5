package TommasoEleodori.BEU2W2D5.users;

import TommasoEleodori.BEU2W2D5.devices.Device;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(length = 20)
    private String name;
    @Column(length = 40)
    private String surname;
    private String email;
    private String password;
    private String username;
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();
}
