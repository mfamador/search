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

import java.util.Locale;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Validator que converte uma string numa instância de 
 * <code>Number</code>.<br/>
 *
 * @version 1.0
 */
public class NumberConverter extends AbstractConverter {

    public NumberConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {
        try {
            String value = (String) result.getValue(container.getName());
            Number number = NumberFormat.getInstance(locale).parse(value);
            result.setValue(container.getName(), number);

            return true;

        } catch (ParseException e) {
            String key = getKey(container, locale, "validation.error.number");
            ValidationError error = new ValidationError(key, getLabel(container, locale));

            errors.add(error);
            return false;
        }
    }
}
