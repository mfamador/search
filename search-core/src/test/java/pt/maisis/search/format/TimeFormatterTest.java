package pt.maisis.search.format;

import junit.framework.TestCase;

import java.util.Date;
import java.text.DateFormat;

/**
 * Testes para a classe {@link TimeFormatter}.
 *
 * @version 1.0
 */
public class TimeFormatterTest extends TestCase {

    public void testTimeFormatter() {
        DateFormat dateFormat;
        TimeFormatter formatter = new TimeFormatter();
        Date date = new Date();

        dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("short");
        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("medium");
        dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("long");
        dateFormat = DateFormat.getTimeInstance(DateFormat.LONG);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("full");
        dateFormat = DateFormat.getTimeInstance(DateFormat.FULL);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));

        formatter.setPattern("invalid pattern");
        dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));
    }
}
