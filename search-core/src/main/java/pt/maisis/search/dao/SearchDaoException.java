package pt.maisis.search.dao;

/**
 * Excepcão que é lancada quando ocorre um problema na camada DAO.
 *
 * @version 1.0
 */
public class SearchDaoException extends RuntimeException {

    public SearchDaoException() {
        super();
    }

    public SearchDaoException(final String str) {
        super(str);
    }

    public SearchDaoException(final Throwable e) {
        super(e);
    }

    public SearchDaoException(final String str, final Throwable e) {
        super(str, e);
    }
}
