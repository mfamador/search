package pt.maisis.search.format;

import junit.framework.TestCase;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Testes para a classe {@link CustomDateFormatter}.
 *
 * @version 1.0
 */
public class CustomDateFormatterTest extends TestCase {

    public void testCustomDateFormatter() {
        DateFormat dateFormat;
        CustomDateFormatter formatter = new CustomDateFormatter();
        Date date = new Date();

        assertNull(formatter.format(null));

        //        assertNull(formatter.format(date));

        //        assertNull(formatter.format("invalid date"));

        formatter.setPattern("yyy-MM-dd");
        dateFormat = new SimpleDateFormat("yyy-MM-dd");
        assertEquals((String)dateFormat.format(date), (String)formatter.format(date));
    }
}
