package pt.maisis.search.validator;

public interface Validatable {

    boolean isRequired();

    int getMinLength();

    int getMaxLength();

    double getMinValue();

    double getMaxValue();

    String getRegex();

    String getExpression();

    Converter getConverter();
}
