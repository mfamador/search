package pt.maisis.search.dao.db.util;

public abstract class Criteria {

    protected static final char COMMA = ',';
    protected static final char SPACE = ' ';
    protected static final char QUESTION_MARK = '?';
    protected static final char OPEN_BRACKET = '(';
    protected static final char CLOSE_BRACKET = ')';
    public static final String OR = "or";
    public static final String AND = "and";
    public static final String LIKE = "like";
    public static final String NOT_LIKE = "not like";
    public static final String NOT_IN = "not in";
    public static final String IN = "in";

    public abstract void write(StringBuffer sb);
}
