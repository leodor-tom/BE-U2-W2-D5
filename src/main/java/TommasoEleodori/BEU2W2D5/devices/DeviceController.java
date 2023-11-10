package TommasoEleodori.BEU2W2D5.devices;

import TommasoEleodori.BEU2W2D5.devices.enums.DeviceStatus;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService ds;

    @GetMapping
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "deviceType") String orderBy) {
        return ds.getDevices(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable UUID id) {
        return ds.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID saveDevice(@RequestBody DeviceDTO body) throws IOException {
        return ds.save(body);
    }

    @PutMapping("/{id}")
    public Device findByIdAndUpdate(@PathVariable UUID id, @RequestBody DeviceUpdateDTO body) throws IOException {
        return ds.findByIdAndUpdate(id, body);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String delete() {
        return " when pigs fly!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) throws IOException {
        ds.findByIdAndDelete(id);
    }

    @GetMapping("/user/{id}")
    public List<Device> findByUserId(@PathVariable UUID id) {
        return ds.findByUserId(id);
    }

    @GetMapping("/type/{type}")
    public List<Device> findByDeviceType(@PathVariable DeviceType type) {
        return ds.findByDeviceType(type);
    }

    @GetMapping("/status/{status}")
    public List<Device> findByDeviceType(@PathVariable DeviceStatus status) {
        return ds.findByDeviceStatus(status);
    }
}
