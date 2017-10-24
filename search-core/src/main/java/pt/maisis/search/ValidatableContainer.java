package pt.maisis.search;

import pt.maisis.search.validator.Validatable;
import pt.maisis.search.validator.Converter;

public class ValidatableContainer implements Validatable {

    private boolean required;
    private int minLength;
    private int maxLength;
    private double minValue;
    private double maxValue;
    private String regex;
    private String expression;
    private Converter converter;

    public ValidatableContainer() {
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public void setMinLength(final int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(final double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(final double maxValue) {
        this.maxValue = maxValue;
    }

    public void setRegex(final String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public Converter getConverter() {
        return this.converter;
    }

    public void setConverter(final Converter converter) {
        this.converter = converter;
    }

    public String toString() {
        return new StringBuffer("ValidatableContainer[").append("required=").append(this.required).append(", minLength=").append(this.minLength).append(", maxLength=").append(this.maxLength).append(", minValue=").append(this.minValue).append(", maxValue=").append(this.maxValue).append(", regex=").append(this.regex).append(", expression=").append(this.expression).append(", converter=").append(this.converter == null ? null : this.converter.getClass()).append("]").toString();
    }
}
