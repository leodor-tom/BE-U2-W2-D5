package TommasoEleodori.BEU2W2D5.devices;

import TommasoEleodori.BEU2W2D5.devices.enums.DeviceStatus;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceType;
import TommasoEleodori.BEU2W2D5.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@ToString
public class Device {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus = DeviceStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
