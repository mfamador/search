package pt.maisis.search.util;

/**
 * O <code>ServiceLocator</code> utiliza esta classe para registar
 * eventuais problemas.
 * 
 * Esta classe é uma simples extensão da classe Exception e que pode 
 * envolver (wrap) uma outra excepcão de mais baixo nível.
 *
 * @see ServiceLocator
 */
public class ServiceLocatorException extends Exception {

    private Exception exception;

    /**
     * Cria uma ServiceLocatorException a partir de uma excepcão e uma
     * mensagem.
     *
     * @param message   mensagem de erro.
     * @param exception a excepcão a ser armazenada.
     */
    public ServiceLocatorException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }

    /**
     * Cria uma ServiceLocatorException a partir da especificada mensagem
     * de erro.
     *
     * @param message mensagem de erro.
     */
    public ServiceLocatorException(String message) {
        this(message, null);
    }

    /**
     * Cria uma ServiceLocatorException envolvendo (wrapping) a dada excepcão.
     *
     * @param exception a excepcão a ser armazenada.
     */
    public ServiceLocatorException(Exception exception) {
        this(null, exception);
    }

    /**
     * @return a mensagem armazenada internamente.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Retorna recursivamente a causa principal da exception (root cause exception).
     *
     * @return a excepcão principal.
     */
    public Exception getRootCause() {
        if (exception instanceof ServiceLocatorException) {
            return ((ServiceLocatorException) exception).getRootCause();
        }
        return exception == null ? this : exception;
    }

    /**
     * Mensagem de erro da root cause exception.
     *
     * @return a excepcão principal.
     */
    public String toString() {
        if (exception instanceof ServiceLocatorException) {
            return ((ServiceLocatorException) exception).toString();
        }
        return exception == null ? super.toString() : exception.toString();
    }
}
