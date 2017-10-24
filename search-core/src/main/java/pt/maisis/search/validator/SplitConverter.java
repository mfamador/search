package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.util.StringUtils;

import java.util.Locale;

/**
 * Converter que permite criar um array de tokens a partir de
 * uma string, tendo em conta um dado separador (por defeito
 * é a vírgula).
 *
 * <p>Se for pretendido um separador diferente (pode ser uma expressão 
 * regular), então é necessário especificar a propriedade 
 * <code>separator</code> no descriptor da pesquisa. 
 * Por exemplo:</p>
 *
 * <pre>
 * &lt;validation&gt;
 *   &lt;converter&gt;split&lt;/converter&gt;
 *   &lt;converter-properties&gt;
 *     &lt;property&gt;
 *       &lt;name&gt;separator&lt;/name&gt;
 *       &lt;value&gt;;&lt;/value&gt;
 *     &lt;/property&gt;
 *   &lt;/converter-properties&gt;
 * &lt;/validation&gt;
 * </pre>
 *
 * @version 1.0
 */
public class SplitConverter extends AbstractConverter {

    /**
     * Nome da propriedade de configuracão que permite definir
     * o separador. 
     */
    public static final String SEPARATOR = "separator";
    /**
     * Separador por defeito, caso não seja especificado um outro
     * no ficheiro de configuracão.
     */
    public static final String DEFAULT_SEPARATOR = ",";

    public SplitConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        String value = (String) result.getValue(container.getName());
        if (value == null) {
            return true;
        }

        String configuredSeparator = getProperty(SEPARATOR);
        String separator =
                (configuredSeparator == null)
                ? DEFAULT_SEPARATOR : configuredSeparator;

        result.setValue(container.getName(),
                StringUtils.split(value, separator));
        return true;
    }
}
