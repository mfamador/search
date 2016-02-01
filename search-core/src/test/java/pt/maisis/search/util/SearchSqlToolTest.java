package pt.maisis.search.util;

import pt.maisis.search.util.SearchSqlTool;
import pt.maisis.search.OrderParameter;
import pt.maisis.search.SearchParameter;
import pt.maisis.search.ResultParameter;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class SearchSqlToolTest extends TestCase {
    
    public void testQuestionMark() {
        SearchSqlTool tool = new SearchSqlTool();

        SearchParameter param;

        param = new SearchParameter
            ("name", "FIELD", "=", "", null);  // mmm, null??
        assertEquals("?", tool.getQuestionMark(param));

        param = new SearchParameter
            ("name", "FIELD", "=", "", "1");
        assertEquals("?", tool.getQuestionMark(param));

        param = new SearchParameter
            ("name", "FIELD", "IN", "", new String[] {"1", "2"});
        assertEquals("(?,?)", tool.getQuestionMark(param));

        param = new SearchParameter
            ("name", "FIELD", "BETWEEN", "", new String[] {"1", "2"});
        assertEquals("? and ?", tool.getQuestionMark(param));
    }

    public void testCsv() {
        SearchSqlTool tool = new SearchSqlTool();

        List list = new ArrayList();
        list.add(new ResultParameter("name", Arrays.asList(new String[]
            {"FIELD_1"})));
        list.add(new ResultParameter("name", Arrays.asList(new String[] 
            {"FIELD_2", "FIELD_3"})));

        assertEquals("FIELD_1,FIELD_2,FIELD_3", tool.getCsv(list));        

        list = new ArrayList();
        list.add(new OrderParameter
                 ("name", 
                  Arrays.asList(new String[] {"FIELD_1"}), OrderParameter.ASCENDING));
        list.add(new OrderParameter
                 ("name", 
                  Arrays.asList(new String[] {"FIELD_2", "FIELD_3"}), OrderParameter.ASCENDING));

        assertEquals("FIELD_1 asc,FIELD_2 asc,FIELD_3 asc", tool.getCsv(list));

    }

    public void testCriteria() {
        SearchSqlTool tool = new SearchSqlTool();

        SearchParameter param;

        param = new SearchParameter
            ("name", "FIELD", "=","",  "1");
        assertEquals("FIELD = ?", tool.getCriteria(param));

        param = new SearchParameter
            ("name", "FIELD", "BETWEEN","",  new String[] {"1", "2"});
        assertEquals("FIELD BETWEEN ? and ?", tool.getCriteria(param));

        param = new SearchParameter
            ("name", "FIELD", "IN","",  new String[] {"1", "2"});
        assertEquals("FIELD IN (?,?)", tool.getCriteria(param));
    }

    public void testCriterias() {
        SearchSqlTool tool = new SearchSqlTool();

        List list = new ArrayList();
        list.add(new SearchParameter
                 ("name", "FIELD_1", "=","",  "1"));
        list.add(new SearchParameter
                 ("name", "FIELD_2", "BETWEEN","",  new String[] {"1", "2"}));

        assertEquals("FIELD_1 = ? and FIELD_2 BETWEEN ? and ?", 
                     tool.getCriterias(list));
    }
}
