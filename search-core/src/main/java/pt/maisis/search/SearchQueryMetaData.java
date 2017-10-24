package pt.maisis.search;

import java.util.List;

public class SearchQueryMetaData {

    private final SearchQuery query;
    private final SearchMetaData smd;
    private final List rpmds;
    private final List rrpmds;

    public SearchQueryMetaData(final SearchQuery query,
            final SearchMetaData smd,
            final List rpmds,
            final List rrpmds) {
        this.query = query;
        this.smd = smd;
        this.rpmds = rpmds;
        this.rrpmds = rrpmds;
    }

    public SearchQuery getQuery() {
        return this.query;
    }

    public SearchMetaData getSearchMetaData() {
        return this.smd;
    }

    public List getResultMetaData() {
        return this.rpmds;
    }

    public List getRequiredResultMetaData() {
        return this.rrpmds;
    }

    public void setResultRecordLimit(final int resultRecordLimit) {
        if (query != null) {
            query.setResultRecordLimit(resultRecordLimit);
        }
    }

    public void setQueryTimeout(final int queryTimeout) {
        if (query != null) {
            query.setQueryTimeout(queryTimeout);
        }
    }
}
