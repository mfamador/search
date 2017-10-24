package pt.maisis.search.util;

import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static List asList(Object[] obj) {
        if (obj == null) {
            return null;
        }
        return Arrays.asList(obj);
    }
}
