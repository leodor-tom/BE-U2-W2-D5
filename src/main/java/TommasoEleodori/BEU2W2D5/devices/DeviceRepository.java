package TommasoEleodori.BEU2W2D5.devices;

import TommasoEleodori.BEU2W2D5.devices.enums.DeviceStatus;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByUserId(UUID userId);

    List<Device> findByDeviceType(DeviceType deviceType);

    List<Device> findByDeviceStatus(DeviceStatus deviceStatus);
}
