package TommasoEleodori.BEU2W2D5.devices;

import TommasoEleodori.BEU2W2D5.devices.enums.DeviceStatus;
import TommasoEleodori.BEU2W2D5.devices.enums.DeviceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record DeviceDTO(
        @NotNull(message = "The device type is mandatory")
        DeviceType deviceType,
        @NotNull(message = "The device status is mandatory")
        DeviceStatus deviceStatus,
        @NotNull(message = "The device must be related to a user")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        UUID author
        ) {
}
