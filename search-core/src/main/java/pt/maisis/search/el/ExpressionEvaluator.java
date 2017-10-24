package pt.maisis.search.el;

import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

import java.util.Map;

/**
 * EL...
 *
 * @version 1.0
 */
public class ExpressionEvaluator {

    private ExpressionEvaluator() {
    }

    public static Object evaluate(Map context, String expression) {

        JexlContext jc = JexlHelper.createContext();
        jc.setVars(context);

        Object o = null;
        try {
            Expression e = ExpressionFactory.createExpression(expression);
            return e.evaluate(jc);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
