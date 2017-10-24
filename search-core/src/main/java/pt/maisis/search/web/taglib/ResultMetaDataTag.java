package pt.maisis.search.web.taglib;

import pt.maisis.search.SearchMetaData;
import pt.maisis.search.web.SearchForm;

import java.util.Iterator;

public class ResultMetaDataTag extends ResultMetaDataBaseTag {

    public static final String ATTRIBUTE_NAME = "resultParam";

    public String getAttributeName() {
        return ATTRIBUTE_NAME;
    }

    public Iterator getResultParamsIterator() {
        SearchMetaData smd = getSearchMetaData();
        SearchForm form = getSearchForm();

        if (smd == null || form == null) {
            return null;
        }

        String[] resultParams = form.getResultParams();
        return smd.getResultParameters(resultParams).iterator();
    }
}
