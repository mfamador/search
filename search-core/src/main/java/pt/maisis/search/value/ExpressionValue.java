package pt.maisis.search.value;

import pt.maisis.search.interpreter.Interpreter;

import java.util.Map;

public class ExpressionValue implements Value {

    private String value;

    public ExpressionValue() {
    }

    public void setExpression(final String value) {
        this.value = value;
    }

    public Object getValue() {
        return Interpreter.eval(this.value);
    }

    public Object getValue(final Map context) {
        return Interpreter.eval(this.value, context);
    }
}
