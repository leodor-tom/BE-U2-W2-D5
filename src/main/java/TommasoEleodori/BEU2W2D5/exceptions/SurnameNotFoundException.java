package TommasoEleodori.BEU2W2D5.exceptions;

public class SurnameNotFoundException extends RuntimeException {
    public SurnameNotFoundException(String surname) {
        super("Record whit surname: " + surname + " doesn't exist");
    }
}
