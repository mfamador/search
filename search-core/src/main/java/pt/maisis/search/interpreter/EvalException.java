package pt.maisis.search.interpreter;

public class EvalException extends RuntimeException {

    public EvalException() {
        super();
    }

    public EvalException(String str) {
        super(str);
    }

    public EvalException(Throwable e) {
        super(e);
    }

    public EvalException(String str, Throwable e) {
        super(str, e);
    }
}
