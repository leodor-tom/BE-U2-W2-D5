package TommasoEleodori.BEU2W2D5.users;

import TommasoEleodori.BEU2W2D5.devices.Device;
import TommasoEleodori.BEU2W2D5.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "AccountNonExpired", "AccountNonLocked"})
public class User implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Device> devices = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
