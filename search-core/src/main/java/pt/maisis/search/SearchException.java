package pt.maisis.search;

/**
 * Excepcão lancada quando ocorre algum problema ao nível da
 * framework de pesquisa.
 *
 * @version 1.0
 */
public class SearchException extends RuntimeException {

    public SearchException() {
        super();
    }

    public SearchException(String str) {
        super(str);
    }

    public SearchException(Throwable e) {
        super(e);
    }

    public SearchException(String str, Throwable e) {
        super(str, e);
    }
}
