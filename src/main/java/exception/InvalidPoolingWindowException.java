package exception;

public class InvalidPoolingWindowException extends CNNLayerException {



    public InvalidPoolingWindowException() {
        super("Invalid pooling window");
    }


    public InvalidPoolingWindowException(String msg) {
        super(msg);
    }
}
