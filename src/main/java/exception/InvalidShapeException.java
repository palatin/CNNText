package exception;

public class InvalidShapeException extends CNNLayerException {

    public InvalidShapeException() {
        super("Invalid shape size");
    }

}
