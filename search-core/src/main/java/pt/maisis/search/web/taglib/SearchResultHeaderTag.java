package pt.maisis.search.web.taglib;

import pt.maisis.search.Result;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.web.SearchForm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import javax.servlet.jsp.JspException;

/**
 * @version 1.0
 */
public class SearchResultHeaderTag extends TagSupport {

    private int maximumDeep;
    private List rpmds;
    private int currentRowIndex;
    private int orderType;
    private Set resultOrderHash;

    public SearchResultHeaderTag() {
    }

    public int doStartTag() throws JspException {

        SearchResultTag parent = (SearchResultTag) findAncestorWithClass(this, SearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        Result result = parent.getResult();
        SearchForm form = parent.getSearchForm();

        this.orderType = form.getOrderType();
        this.resultOrderHash = indexNames(form.getResultOrder());
        this.rpmds = result.getResultMetaData();
        this.maximumDeep = 1;
        this.currentRowIndex = 0;
        calculateMaximumDeep();
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (!this.rpmds.isEmpty()) {
            this.currentRowIndex++;
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    private void calculateMaximumDeep() {
        int deep = 1;
        Iterator it = this.rpmds.iterator();
        while (it.hasNext()) {
            ResultParameterMetaData rpmd = (ResultParameterMetaData) it.next();

            int rpmdDeep = getDeep(deep, rpmd);
            if (rpmdDeep > this.maximumDeep) {
                this.maximumDeep = rpmdDeep;
            }
        }
    }

    private int getDeep(int deep, ResultParameterMetaData rpmd) {
        if (rpmd.isComposite()) {
            Iterator it = rpmd.getChildren().iterator();
            int maxDeep = deep;
            while (it.hasNext()) {
                int branchDeep = getDeep(deep + 1, (ResultParameterMetaData) it.next());
                if (branchDeep > maxDeep) {
                    maxDeep = branchDeep;
                }
            }
            deep = maxDeep;
        }
        return deep;
    }

    private Set indexNames(String[] resultOrder) {
        if (resultOrder == null) {
            return null;
        }
        Set set = new HashSet();
        set.addAll(Arrays.asList(resultOrder));
        return set;
    }

    public boolean isOrdered(final String name) {
        if (this.resultOrderHash == null) {
            return false;
        }
        return this.resultOrderHash.contains(name);
    }

    public int getOrderType() {
        return this.orderType;
    }

    public int getMaximumDeep() {
        return this.maximumDeep;
    }

    public List getResultMetaData() {
        return this.rpmds;
    }

    public void setResultMetaData(final List rpmds) {
        this.rpmds = rpmds;
    }

    public int getCurrentRowIndex() {
        return this.currentRowIndex;
    }
}
