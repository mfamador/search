package pt.maisis.search.format;

import junit.framework.TestCase;

import java.util.Date;
import java.text.DateFormat;

/**
 * Testes para a classe {@link DateTimeFormatter}.
 *
 * @version 1.0
 */
public class DateTimeFormatterTest extends TestCase {

    public void testDateTimeFormatter() {
        DateFormat dateFormat;
        DateTimeFormatter formatter = new DateTimeFormatter();
        Date date = new Date();

        dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("short,medium");
        dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("invalid pattern");
        dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));
    }
}
