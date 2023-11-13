package TommasoEleodori.BEU2W2D5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(NotFoundException e) {
        return new ErrorDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponseListDTO handleBadRequest(BadRequestException e) {
        if (e.getErrorList() != null) {
            List<String> errorsList = e.getErrorList().stream()
                    .map(objectError -> objectError.getDefaultMessage()).toList();
            return new ErrorsResponseListDTO(e.getMessage(), new Date(), new ArrayList<>());
        } else {
            return new ErrorsResponseListDTO(e.getMessage(), new Date(), new ArrayList<>());
        }
    }

    @ExceptionHandler(NameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNameNotFound(NameNotFoundException e) {
        return new ErrorDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(SurnameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleSurnameNotFound(SurnameNotFoundException e) {
        return new ErrorDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleGenericError(Exception e) {
        return new ErrorDTO(e.getMessage(), new Date());
    }
}
