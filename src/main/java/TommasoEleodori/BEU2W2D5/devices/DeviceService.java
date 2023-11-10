package TommasoEleodori.BEU2W2D5.devices;

import TommasoEleodori.BEU2W2D5.config.EmailSender;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceStatus;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceType;
import TommasoEleodori.BEU2W2D5.exceptions.BadRequestException;
import TommasoEleodori.BEU2W2D5.exceptions.NotFoundException;
import TommasoEleodori.BEU2W2D5.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository dr;
    @Autowired
    private UserService us;
    @Autowired
    private EmailSender emailsdr;

    public Page<Device> getDevices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return dr.findAll(pageable);
    }

    public Device findById(UUID id) throws NotFoundException {
        return dr.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public UUID save(DeviceDTO body) throws BadRequestException, IOException {
        Device device = new Device();
        device.setDeviceType(body.deviceType());
        device.setDeviceStatus(body.deviceStatus());
        device.setUser(us.findById(body.user()));
        dr.save(device);
        emailsdr.sendNewDeviceEmail(device.getUser().getEmail(), device.getUser().getName(), device.getDeviceType().toString());
        return device.getId();
    }

    public Device findByIdAndUpdate(UUID id, DeviceUpdateDTO body) throws NotFoundException, IOException {
        Device found = this.findById(id);
        if (!found.getUser().equals(us.findById(body.user()))) {
            emailsdr.sendNewDeviceEmail(found.getUser().getEmail(), found.getUser().getName(), found.getDeviceType().toString());
        } else {
            emailsdr.sendNewDeviceEmail(us.findById(body.user()).getEmail(), us.findById(body.user()).getName(), found.getDeviceType().toString());
        }
        found.setDeviceStatus(body.deviceStatus());
        found.setUser(us.findById(body.user()));
        return dr.save(found);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException, IOException {
        Device device = this.findById(id);
        emailsdr.sendDeletedDeviceEmail(device.getUser().getEmail(), device.getUser().getName(), device.getDeviceType().toString());
        dr.delete(device);
    }

    public List<Device> findByUserId(UUID userId) throws NotFoundException {
        return dr.findByUserId(userId);
    }

    public List<Device> findByDeviceType(DeviceType deviceType) throws NotFoundException {
        return dr.findByDeviceType(deviceType);
    }

    public List<Device> findByDeviceStatus(DeviceStatus deviceStatus) throws NotFoundException {
        return dr.findByDeviceStatus(deviceStatus);
    }

}
