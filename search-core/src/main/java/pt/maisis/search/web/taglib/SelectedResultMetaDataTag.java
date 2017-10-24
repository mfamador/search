package pt.maisis.search.web.taglib;

import pt.maisis.search.SearchMetaData;
import pt.maisis.search.web.SearchForm;

import java.util.Iterator;

public class SelectedResultMetaDataTag extends ResultMetaDataBaseTag {

    public static final String ATTRIBUTE_NAME = "selectedResultParam";

    public SelectedResultMetaDataTag() {
    }

    public String getAttributeName() {
        return ATTRIBUTE_NAME;
    }

    public Iterator getResultParamsIterator() {
        SearchMetaData smd = getSearchMetaData();
        SearchForm form = getSearchForm();

        if (smd == null || form == null) {
            return null;
        }

        String[] resultParams = form.getSelectedResultParams();

        return smd.getResultParameters(resultParams).iterator();
    }
}
