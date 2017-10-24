package pt.maisis.search.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import java.util.Collection;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Classe que disponibiliza alguns métodos úteis para a manipulacão 
 * de strings.
 */
public class StringUtils {

    public static final String DEFAULT_SEPARATOR = ",";
    public static final String EMPTY_STRING = "";
    private static final String ACENTOS = "áÁàÀãÃâÂéÉêÊíÍóÓôÔúÚcÇ";
    private static final String TRADUCAO = "aAaAaAaAeEeEiIoOoOuUcC";

    public static boolean isEmpty(String str) {
        return org.apache.commons.lang.StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String join(Object array) {
        return join(array, DEFAULT_SEPARATOR);
    }

    public static String join(Object array, String separator) {
        if (array == null) {
            return null;
        }

        if (array instanceof Object[]) {
            return join((Object[]) array, separator);
        }
        if (array instanceof boolean[]) {
            return join((boolean[]) array, separator);
        }
        if (array instanceof byte[]) {
            return join((byte[]) array, separator);
        }
        if (array instanceof short[]) {
            return join((short[]) array, separator);
        }
        if (array instanceof int[]) {
            return join((int[]) array, separator);
        }
        if (array instanceof long[]) {
            return join((long[]) array, separator);
        }
        if (array instanceof float[]) {
            return join((float[]) array, separator);
        }
        if (array instanceof double[]) {
            return join((double[]) array, separator);
        }
        if (array instanceof Collection) {
            return join(((Collection) array).toArray(), separator);
        }
        if (array instanceof String) {
            return (String) array;
        }
        return array.toString();
    }

    public static String join(Object array, char separator) {
        return join(array, String.valueOf(separator));
    }

    public static String join(Object[] array, char separator) {
        return org.apache.commons.lang.StringUtils.join(array, separator);
    }

    public static String join(boolean[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(byte[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(short[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(int[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(long[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(float[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(double[] array, char separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(Object[] array, String separator) {
        return org.apache.commons.lang.StringUtils.join(array, separator);
    }

    public static String join(boolean[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(byte[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(short[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(int[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(long[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(float[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static String join(double[] array, String separator) {
        return join(ArrayUtils.toObject(array), separator);
    }

    public static Object split(String str, String separator, Class clazz) {
        if (str != null && !EMPTY_STRING.equals(str.trim())) {
            String[] strings = str.split(separator);
            return ConvertUtils.convert(strings, clazz);
        }
        return null;
    }

    public static Object split(String str, Class clazz) {
        return split(str, DEFAULT_SEPARATOR, clazz);
    }

    public static Object split(String str) {
        return split(str, DEFAULT_SEPARATOR, String.class);
    }

    public static Object split(String str, String separator) {
        return split(str, separator, String.class);
    }

    public static Object split(Object str, String separator) {
        if (str == null) {
            return str;
        }
        return split(str.toString(), separator, String.class);
    }

    public static char remove(char c) {
        if (c <= 127) {
            return c;
        }
        final int index = ACENTOS.indexOf(c);
        if (index == -1) {
            return c;
        }
        return TRADUCAO.charAt(index);
    }

    public static String remove(String str) {
        final CharacterIterator iterator = new StringCharacterIterator(str);

        StringBuffer buffer = new StringBuffer();

        for (char c = iterator.current();
                c != CharacterIterator.DONE;
                c = iterator.next()) {

            buffer.append(remove(c));
        }
        return buffer.toString();
    }

    public static boolean isControlChar(char c) {
        return Character.isIdentifierIgnorable(c);
    }

    public static String removeControlChars(String str) {
        final CharacterIterator iterator = new StringCharacterIterator(str);

        StringBuffer buffer = new StringBuffer();

        for (char c = iterator.current();
                c != CharacterIterator.DONE;
                c = iterator.next()) {

            if (!Character.isIdentifierIgnorable(c)) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static int count(String str, char token) {
        final CharacterIterator iterator = new StringCharacterIterator(str);
        int counter = 0;

        for (char c = iterator.current();
                c != CharacterIterator.DONE;
                c = iterator.next()) {

            if (token == c) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Retorna uma representacão em string do objecto.
     *
     * @param obj um objecto
     * @return null se o argumento for null, caso contrário retorna
     *  obj.toString().
     */
    public static String valueOf(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    public static String removeExtraSpacesAndNewLines(String str) {
        return str.replaceAll("\n", " ").replaceAll("\\s{2,}", " ");
    }
}
