package pt.maisis.search.config;

/**
 * Excepcão lancada quando ocorre algum problema na configuracão 
 * da framework de pesquisa.
 *
 * @version 1.0
 */
public class SearchConfigException extends RuntimeException {

    public SearchConfigException() {
        super();
    }

    public SearchConfigException(String str) {
        super(str);
    }

    public SearchConfigException(Throwable e) {
        super(e);
    }

    public SearchConfigException(String str, Throwable e) {
        super(str, e);
    }
}
