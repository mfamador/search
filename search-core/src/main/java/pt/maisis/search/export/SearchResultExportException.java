package pt.maisis.search.export;

public class SearchResultExportException extends Exception {

    public SearchResultExportException() {
        super();
    }

    public SearchResultExportException(final String message) {
        super(message);
    }

    public SearchResultExportException(final Throwable e) {
        super(e);
    }
}
