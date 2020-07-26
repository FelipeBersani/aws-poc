package awspock.poc.sqs.example.common.exception;

public class HeroeNotFoundException extends RuntimeException {

    public HeroeNotFoundException() {
        super();
    }

    public HeroeNotFoundException(String message) {
        super(message);
    }
}
