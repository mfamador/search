package pt.maisis.search.dao.db.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CompositeCriteria extends Criteria {

    private final List criterias;
    private final String operator;

    public CompositeCriteria(final String operator) {
        this.criterias = new ArrayList();
        this.operator = operator;
    }

    public void addCriteria(final Criteria criteria) {
        this.criterias.add(criteria);
    }

    public int size() {
        return this.criterias.size();
    }

    public void write(StringBuffer sb) {
        sb.append(OPEN_BRACKET);
        Iterator i = this.criterias.iterator();
        while (i.hasNext()) {
            Criteria criteria = (Criteria) i.next();
            criteria.write(sb);
            if (i.hasNext()) {
                sb.append(SPACE).append(operator).append(SPACE);
            }
        }
        sb.append(CLOSE_BRACKET);
    }
}
