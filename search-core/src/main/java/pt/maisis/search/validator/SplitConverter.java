/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
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
