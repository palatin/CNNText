package exception;

public class EmptyShapeException extends CNNLayerException {

    public EmptyShapeException() {
        super("Shape matrix cannot be empty");
    }

}
