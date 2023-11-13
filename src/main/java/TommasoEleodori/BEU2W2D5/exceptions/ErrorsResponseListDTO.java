package TommasoEleodori.BEU2W2D5.exceptions;

import java.util.Date;
import java.util.List;

public record ErrorsResponseListDTO(String message, Date timestamp,
                                    List<String> errorsList) {
}
