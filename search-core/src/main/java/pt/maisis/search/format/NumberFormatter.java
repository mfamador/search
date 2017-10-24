package pt.maisis.search.format;

import java.util.Locale;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Formatador que permite formatar um dado valor numérico tendo 
 * em conta a <i>pattern</i> especificada no descriptor da
 * pesquisa.<br/>
 * 
 * Exemplos:
 * <table border="1">
 *   <tr>
 *     <th>Valor</th>
 *     <th>pattern</th>
 *     <th>Resultado</th>
 *   </tr>
 *   <tr>
 *     <td>123456.789</td>
 *     <td>###,###.###</td>
 *     <td>123,456.789</td>
 *   </tr>
 *   <tr>
 *     <td>123456.789</td>
 *     <td>###.##</td>
 *     <td>123456.79</td>
 *   </tr>
 *   <tr>
 *     <td>123.78</td>
 *     <td>000000.000</td>
 *     <td>000123.780</td>
 *   </tr>
 * </table>
 * <br/>
 * Para mais informacão consulte a documentacão java da classe 
 * java.text.NumberFormat e java.text.DecimalFormat.
 */
public class NumberFormatter extends AbstractFormatter {

    public NumberFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        if (obj == null || !(obj instanceof Number)) {
            return super.nullValue;
        }

        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        if (super.pattern != null) {
            ((DecimalFormat) formatter).applyPattern(pattern);
        }
        return formatter.format(obj);
    }
}
