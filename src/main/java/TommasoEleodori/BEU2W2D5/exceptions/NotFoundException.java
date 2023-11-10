package TommasoEleodori.BEU2W2D5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Record whit id: " + id + " not found");
    }

    public NotFoundException(String username) {
        super("Record: " + username + " not found");
    }
}
