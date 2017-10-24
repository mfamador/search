package pt.maisis.search;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa o resultado, paginável, de uma pesquisa.
 *
 * @see pt.maisis.search.SearchResult
 * @see pt.maisis.search.Pageable
 * 
 * @version 1.0
 */
public class PageableSearchResult implements Pageable, SearchResult, Serializable {

    public static final PageableSearchResult EMPTY_RESULT_SET = new PageableSearchResult(null, false, 0, 0, 0);
    private final SearchResult sr;
    private final boolean hasNext;
    private final int start;
    private final int total;
    private final int count;

    public PageableSearchResult(final SearchResult sr, final boolean hasNext,
            final int start, final int count) {
        this(sr, hasNext, start, count, 0);
    }

    public PageableSearchResult(final SearchResult sr, final boolean hasNext,
            final int start, final int count,
            final int total) {
        this.sr = sr;
        this.hasNext = hasNext;
        this.start = start;
        this.total = total;
        this.count = count;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public boolean hasPrevious() {
        return this.start > 0;
    }

    public int getStart() {
        return this.start;
    }

    public int getCount() {
        return this.count;
    }

    public int getEnd() {
        return this.start + getResultCount();
    }

    public int getTotal() {
        return this.total;
    }

    public int getTotalPages() {
        if (this.count == 0) {
            return 0;
        }
        return this.total / this.count + Math.min(this.total % this.count, 1);
    }

    public int getCurrentPage() {
        return this.start / this.count;
    }

    public int getStartOfNextPage() {
        return this.start + this.count;
    }

    public int getStartOfPreviousPage() {
        return Math.max(this.start - this.count, 0);
    }

    public List getElements() {
        return (this.sr == null) ? new ArrayList(0) : this.sr.getElements();
    }

    public List getNames() {
        return (this.sr == null) ? new ArrayList(0) : this.sr.getNames();
    }

    public SearchResultElement getElement(int index) {
        return (this.sr == null) ? null : this.sr.getElement(index);
    }

    public int getResultCount() {
        return (this.sr == null) ? 0 : this.sr.getResultCount();
    }

    public boolean isEmpty() {
        return getResultCount() == 0;
    }

    public boolean contains(String name) {
        return (this.sr == null) ? false : this.sr.contains(name);
    }

    public int getIndex(String name) {
        return (this.sr == null) ? -1 : this.sr.getIndex(name);
    }

    public String getName(final int index) {
        return (this.sr == null) ? null : this.sr.getName(index);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("SearchResult");
        if (this.sr == null) {
            sb.append("[]");
        } else {
            sb.append(this.sr.toString());
        }
        return sb.toString();
    }
}
