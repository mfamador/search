package pt.maisis.search.export.jfreereport;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public final class JFreeReportConfig {

    // colocar no ficheiro de configuracao
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir"); 

    private static final JFreeReportConfig me = new JFreeReportConfig();

    private boolean formatValues; 


    private JFreeReportConfig() {
        InputStream input = null;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            input = cl.getResourceAsStream("search-jfreereport.properties");
            
            if (input != null) {
                props.load(input);
                String propFormatValues = props.getProperty("format.values");
                this.formatValues = 
                    (propFormatValues == null) 
                    ? false
                    : Boolean.valueOf(propFormatValues).booleanValue();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch(IOException e) {
                    // nada a fazer
                }
            }
        }        
    }

    public static JFreeReportConfig getInstance() {
        return me;
    }

    public String getTmpDir() {
        return TMP_DIR;
    }

    public boolean isFormatValues() {
        return this.formatValues;
    }
}
