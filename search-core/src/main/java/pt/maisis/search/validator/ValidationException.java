package pt.maisis.search.validator;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String str) {
        super(str);
    }

    public ValidationException(Throwable e) {
        super(e);
    }

    public ValidationException(String str, Throwable e) {
        super(str, e);
    }
}
