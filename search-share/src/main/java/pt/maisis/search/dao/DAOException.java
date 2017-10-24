package pt.maisis.search.dao;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String msg, Exception e) {
        super(msg, e);
    }

    public DAOException(String msg) {
        super(msg, null);
    }
}
