package TommasoEleodori.BEU2W2D5.exceptions;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException(String name) {
        super("Record whit name: " + name + "doesn't exist");
    }
}
