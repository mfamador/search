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

import pt.maisis.search.SearchMetaData;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.util.ApplicationResources;
import pt.maisis.search.el.ExpressionEvaluator;

import java.util.Locale;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Validator...
 *
 * @version 1.0
 */
public class Validator {

    private static Log log = LogFactory.getLog(Validator.class);
    private static final Validator me = new Validator();

    public static Validator getInstance() {
        return me;
    }

    private Validator() {
    }

    /**
     * Validates the values in the given {@link ValidationResult} taking
     * in count the {@link SearchMetaData} and the <code>locale</code>.
     */
    public void validate(SearchMetaData smd,
            ValidationResult result,
            ValidationErrors errors,
            Locale locale) {

        List containers = new ArrayList();
        for (Iterator it = smd.getSearchParameters().iterator(); it.hasNext();) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            containers.add(spmd.getContainer());
        }

        containers = doPreConvertionValidations(result, errors, containers, locale);
        containers = doConvertions(result, errors, containers, locale);
        doPostConvertionValidations(result, errors, containers, locale);
    }

    /**
     * Handles required validations before doing any convertion.
     * The validations handled in this phase are:
     * <ul>
     *   <li><code>required</code></li>
     *   <li><code>minlength</code></li>
     *   <li><code>minlength</code></li>
     *   <li><code>regex</code></li>
     * </ul>
     */
    private List doPreConvertionValidations(ValidationResult result,
            ValidationErrors errors,
            List containers,
            Locale locale) {
        List valid = new ArrayList();
        for (Iterator it = containers.iterator(); it.hasNext();) {
            ContainerMetaData container = (ContainerMetaData) it.next();
            if (isPreConvertionValid(result, errors, container, locale)) {
                valid.add(container);
            }
        }
        return valid;
    }

    /**
     * Validates the value(s) for the given container.
     */
    private boolean isPreConvertionValid(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        // recursive call in case this is a composite container
        if (container.isComposite()) {
            boolean valid = true;
            for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
                ContainerMetaData child = (ContainerMetaData) it.next();
                if (!isPreConvertionValid(result, errors, child, locale)) {
                    valid = false;
                }
            }
            return valid;
        }

        Validatable validatable = container.getValidatable();

        // nothing to validate
        if (validatable == null) {
            return true;
        }

        // TODO: é preciso suportar arrays de strings!!!!
        // Neste momento a validacão é simplesmente ignorada.
        if (result.getValue(container.getName()) != null
                && result.getValue(container.getName()).getClass().isArray()) {
            return true;
        }

        String value = (String) result.getValue(container.getName());

        // required
        if (validatable.isRequired() && (value == null || "".equals(value))) {
            String key = getKey(container, locale, "validation.error.required");
            ValidationError error = new ValidationError(key, getLabel(container, locale));
            errors.add(error);
            return false;
        }

        // no need to continue
        if (value == null) {
            return true;
        }

        // minlength
        if (validatable.getMinLength() > 0 && value.length() < validatable.getMinLength()) {
            String key = getKey(container, locale, "validation.error.minlength");
            ValidationError error = new ValidationError(key, getLabel(container, locale));
            errors.add(error);
            return false;
        }

        // maxlength
        if (validatable.getMaxLength() > 0 && value.length() > validatable.getMaxLength()) {
            String key = getKey(container, locale, "validation.error.maxlength");
            ValidationError error = new ValidationError(key, getLabel(container, locale));
            errors.add(error);
            return false;
        }

        // regex
        if (validatable.getRegex() != null) {
            Pattern pattern = Pattern.compile(validatable.getRegex());
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                String key = getKey(container, locale, "validation.error.regex");
                ValidationError error = new ValidationError(key, getLabel(container, locale));
                errors.add(error);
                return false;
            }
        }
        return true;
    }

    // convertions
    private List doConvertions(ValidationResult result,
            ValidationErrors errors,
            List containers,
            Locale locale) {

        List valid = new ArrayList();

        for (Iterator it = containers.iterator(); it.hasNext();) {
            ContainerMetaData container = (ContainerMetaData) it.next();
            if (isConvertionValid(result, errors, container, locale)) {
                valid.add(container);
            }
        }
        return valid;
    }

    // convertions
    private boolean isConvertionValid(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        // recursive call in case this is a composite container
        if (container.isComposite()) {
            boolean valid = true;
            boolean allNull = true;
            for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
                ContainerMetaData child = (ContainerMetaData) it.next();
                if (result.getValue(child.getName()) != null) {
                    allNull = false;
                    if (!isConvertionValid(result, errors, child, locale)) {
                        valid = false;
                    }
                }
            }

            if (allNull) {
                return true;
            }

            if (valid) {
                Validatable validatable = container.getValidatable();

                Converter converter = validatable.getConverter();

                // TODO: quando nenhum converter for especificado para
                // um container composto, um array deve ser criado com
                // os valores dos campos agregados

                // e as validacoes das expressoes de cada um dos valores
                // agregados?

                return converter.convert(result, errors, container, locale);
            }

            return false;
        }

        Validatable validatable = container.getValidatable();

        // nothing to validate
        if (validatable == null) {
            return true;
        }

        Converter converter = validatable.getConverter();

        // TODO: é preciso suportar arrays de strings!!!!
        if (result.getValue(container.getName()) != null
                && result.getValue(container.getName()).getClass().isArray()) {
            return true;
        }

        String value = (String) result.getValue(container.getName());

        // nothing to convert
        if (converter == null || value == null) {
            return true;
        }

        return converter.convert(result, errors, container, locale);
    }

    private void doPostConvertionValidations(ValidationResult result,
            ValidationErrors errors,
            List containers,
            Locale locale) {

        for (Iterator it = containers.iterator(); it.hasNext();) {
            ContainerMetaData container = (ContainerMetaData) it.next();
            Validatable validatable = container.getValidatable();
            if (validatable == null) {
                continue;
            }
            if (result.getValue(container.getName()) != null) {
                isPostConvertionValid(result, errors, container, locale);
            }
        }
    }

    private boolean isPostConvertionValid(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        Validatable validatable = container.getValidatable();
        String expression = validatable.getExpression();

        if (expression != null) {

            Object o = ExpressionEvaluator.evaluate(result.getValues(), expression);

            if (o == null || !(o instanceof Boolean)) {
                String error = MessageResources.getInstance().getMessage("validation.error.invalidExpression", expression, o);
                log.error(error);
                return false;
            }

            Boolean expResult = (Boolean) o;
            if (expResult == Boolean.FALSE) {
                String key = getKey(container, locale, "validation.error.expression");

                ValidationError error = new ValidationError(key);
                errors.add(error);
                return false;
            }

        }
        return true;
    }

    private String getLabel(final ContainerMetaData container, final Locale locale) {
        ApplicationResources messages = ApplicationResources.getInstance(locale);
        return messages.getMessage(container.getLabel());
    }

    public String getKey(ContainerMetaData container,
            Locale locale,
            String key) {

        ApplicationResources messages = ApplicationResources.getInstance(locale);
        String search = container.getSearchParameterMetaData().getSearchMetaData().getName();
        String label = container.getLabel();

        return messages.getKey(search, label, key);
    }
}
