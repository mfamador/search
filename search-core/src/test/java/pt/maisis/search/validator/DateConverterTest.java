package pt.maisis.search.validator;

import java.util.Date;
import java.util.Locale;
import junit.framework.TestCase;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.SimpleContainerMetaData;

public class DateConverterTest extends TestCase {
    
    public DateConverterTest(String testName) {
        super(testName);
    }            

    public void testConvert() {
        ValidationResult vresult = new ValidationResult();
        vresult.setValue("date", "2007-12-12");
        
        ValidationErrors verrors = new ValidationErrors();
        
        SimpleContainerMetaData container = new SimpleContainerMetaData();
        container.setName("date");
        
        DateConverter converter = new DateConverter();
        converter.addProperty("default-pattern", "yyyy-MM-dd");
        
        boolean expResult = false;
        boolean result = converter.convert(vresult, verrors, container, Locale.getDefault());
        
        //assertEquals(expResult, result);
    }
}
