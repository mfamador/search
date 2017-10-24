package pt.maisis.search.validator;

/**
 * Excepcão lancada quando ocorre algum problema na configuracão 
 * de um dado validator.
 *
 * @version 1.0
 */
public class ValidatorConfigException extends RuntimeException {

    public ValidatorConfigException() {
        super();
    }

    public ValidatorConfigException(final String str) {
        super(str);
    }

    public ValidatorConfigException(final Throwable e) {
        super(e);
    }

    public ValidatorConfigException(final String str,
            final Throwable e) {
        super(str, e);
    }
}
