package pt.maisis.search.format;

import junit.framework.TestCase;

import java.util.Date;
import java.text.DateFormat;

/**
 * Testes para a classe {@link DateFormatter}.
 *
 * @version 1.0
 */
public class DateFormatterTest extends TestCase {

    public void testDateFormatter() {
        DateFormat dateFormat;
        DateFormatter formatter = new DateFormatter();
        Date date = new Date();

        dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("short");
        dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("medium");
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("long");
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("full");
        dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("invalid pattern");
        dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));
    }
}
