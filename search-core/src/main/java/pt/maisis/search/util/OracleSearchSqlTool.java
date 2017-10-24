package pt.maisis.search.util;

import java.sql.Date;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.SearchParameter;

/**
 *
 * @author dconceicao
 */
public class OracleSearchSqlTool extends SearchSqlTool {

    private static Log log = LogFactory.getLog(OracleSearchSqlTool.class);

    public OracleSearchSqlTool() {
    }

    public String getCriteria(final SearchParameter param) {
        StringBuffer sb = new StringBuffer();

        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(param.getOperator()).append(" (");
            sb.append(' ').append(param.getField()).append(",");
        } else {
            sb.append(param.getField());
            sb.append(' ').append(param.getOperator());
        }


        if (param.getValue() instanceof Timestamp) {
            sb.append(' ').append("to_date(" + getQuestionMark(param) + ", 'yyyy-mm-dd hh24:mi:ss')");
        } else if (param.getValue() instanceof Date) {
            sb.append(' ').append("to_date(" + getQuestionMark(param) + ", 'yyyy-mm-dd hh24:mi:ss')");
        } else if (param.getValue() instanceof Timestamp[]) {
            Timestamp[] array = (Timestamp[]) param.getValue();
            sb.append("(");
            for (int i = 0; i < array.length; i++) {
                sb.append(' ').append("to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
                if (i < ( array.length - 1 )) {
                    sb.append(",");
                }
            }
            sb.append(")");
        } else {
            sb.append(' ').append(getQuestionMark(param));
        }

        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(")");
        }
        return sb.toString();
    }
}
