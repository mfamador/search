package pt.maisis.search.util;

import pt.maisis.search.ParameterMetaData;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @version 1.0
 */
public class ParameterMetaDataUtils {

    public static String[] getNames(List params) {
        String[] names = new String[params.size()];
        int i = 0;
        Iterator it = params.iterator();
        while (it.hasNext()) {
            ParameterMetaData param = (ParameterMetaData) it.next();
            names[i++] = param.getName();
        }
        return names;
    }

    private ParameterMetaDataUtils() {
    }
}
