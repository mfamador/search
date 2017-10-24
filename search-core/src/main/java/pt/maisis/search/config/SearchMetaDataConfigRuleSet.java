package pt.maisis.search.config;

import pt.maisis.search.FieldMetaData;
import pt.maisis.search.AjaxParameterMetaData;
import pt.maisis.search.SearchMetaDataImpl;
import pt.maisis.search.SimpleContainerMetaData;
import pt.maisis.search.CompositeResultParameterMetaData;
import pt.maisis.search.CompositeContainerMetaData;
import pt.maisis.search.RequiredResultParameterMetaData;
import pt.maisis.search.SelectedResultParameterMetaDataOrder;
import pt.maisis.search.DynamicSearchParameters;
import pt.maisis.search.DynamicResultParameters;
import pt.maisis.search.DynamicRequiredResultParameters;
import pt.maisis.search.DynamicSelectedResultParameters;
import pt.maisis.search.SimpleResultParameterMetaData;
import pt.maisis.search.SearchParameterMetaDataImpl;
import pt.maisis.search.EventMetaDataImpl;
import pt.maisis.search.SqlFragmentParameterMetaData;
import pt.maisis.search.ValidatableContainer;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.validator.Converter;
import pt.maisis.search.value.SqlValue;
import pt.maisis.search.value.AjaxValue;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.CallMethodRule;
import org.apache.commons.digester.RuleSetBase;
import org.apache.commons.digester.BeanPropertySetterRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import pt.maisis.search.SearchMetaData;

/**
 * Conjunto de regras (Digester rules) necessárias para fazer o
 * parse dos ficheiros de configuracão relativos às pesquisas.
 *
 * @version 1.0
 */
public class SearchMetaDataConfigRuleSet extends RuleSetBase {

    private static Log log = LogFactory.getLog(SearchMetaDataConfigRuleSet.class);

    public void addRuleInstances(final Digester digester) {

        /*----- search metadata -----*/
        digester.addObjectCreate("*/search-metadata",
                SearchMetaDataImpl.class);
        digester.addBeanPropertySetter("*/search-metadata/name");
        digester.addBeanPropertySetter("*/search-metadata/label");
        digester.addBeanPropertySetter("*/search-metadata/description");
        digester.addBeanPropertySetter("*/search-metadata/search-engine-class",
                "searchEngineClass");
        digester.addBeanPropertySetter("*/search-metadata/data-source",
                "dataSource");
        digester.addBeanPropertySetter("*/search-metadata/table-source",
                "tableSource");
        digester.addBeanPropertySetter("*/search-metadata/sql-template",
                "sqlTemplate");

        digester.addCallMethod("*/search-metadata/properties/property",
                "addProperty", 2);
        digester.addCallParam("*/search-metadata/properties/property/name", 0);
        digester.addCallParam("*/search-metadata/properties/property/value", 1);

        /*----- search sql fragment -----*/
        digester.addObjectCreate("*/search-sql-fragment",
                SqlFragmentParameterMetaData.class);
        digester.addBeanPropertySetter("*/search-sql-fragment/name");
        digester.addBeanPropertySetter("*/search-sql-fragment/value");
        digester.addSetNext("*/search-sql-fragment",
                "setSqlFragmentParameter");

        /*----- search param -----*/
        digester.addObjectCreate("*/search-param",
                SearchParameterMetaDataImpl.class);
        digester.addBeanPropertySetter("*/search-param/name");
        digester.addBeanPropertySetter("*/search-param/field");
        digester.addBeanPropertySetter("*/search-param/required");
        digester.addBeanPropertySetter("*/search-param/operator");

        /*----- composite container -----*/
        digester.addObjectCreate("*/composite-container",
                CompositeContainerMetaData.class);
        digester.addBeanPropertySetter("*/composite-container/label");
        digester.addBeanPropertySetter("*/composite-container/label-orientation",
                "labelOrientation");
        digester.addCallMethod("*/composite-container/formatter",
                "setFormatter", 3);
        digester.addCallParam("*/composite-container/formatter/name", 0);
        digester.addCallParam("*/composite-container/formatter/pattern", 1);
        digester.addCallParam("*/composite-container/formatter/null-value", 2);

        /*----- fragment container -----*/
        digester.addObjectCreate("*/fragment-container",
                SimpleContainerMetaData.class);
        digester.addBeanPropertySetter("*/fragment-container/name");
        digester.addBeanPropertySetter("*/fragment-container/label");
        digester.addBeanPropertySetter("*/fragment-container/label-orientation",
                "labelOrientation");
        digester.addBeanPropertySetter("*/fragment-container/type");
        digester.addBeanPropertySetter("*/fragment-container/checked");
        digester.addBeanPropertySetter("*/fragment-container/disabled");
        digester.addBeanPropertySetter("*/fragment-container/readonly");
        digester.addBeanPropertySetter("*/fragment-container/size");
        digester.addBeanPropertySetter("*/fragment-container/input-size",
                "inputSize");

        /*----- container -----*/
        digester.addObjectCreate("*/container",
                SimpleContainerMetaData.class);
        digester.addBeanPropertySetter("*/container/label");
        digester.addBeanPropertySetter("*/container/label-orientation",
                "labelOrientation");
        digester.addBeanPropertySetter("*/container/type");
        digester.addBeanPropertySetter("*/container/checked");
        digester.addBeanPropertySetter("*/container/disabled");
        digester.addBeanPropertySetter("*/container/readonly");
        digester.addBeanPropertySetter("*/container/size");
        digester.addBeanPropertySetter("*/container/input-size",
                "inputSize");

        /*----- validation -----*/
        digester.addObjectCreate("*/validation",
                ValidatableContainer.class);

        digester.addRule("*/validation/converter",
                new ConverterValidatorRule());
        digester.addRule("*/validation/converter-properties/property",
                new ConverterProperties());
        digester.addCallParam("*/validation/converter-properties/property/name", 0);
        digester.addCallParam("*/validation/converter-properties/property/value", 1);

        digester.addBeanPropertySetter("*/validation/required");
        digester.addBeanPropertySetter("*/validation/min-length",
                "minLength");
        digester.addBeanPropertySetter("*/validation/max-length",
                "maxLength");
        digester.addBeanPropertySetter("*/validation/min-value",
                "minValue");
        digester.addBeanPropertySetter("*/validation/max-value",
                "maxValue");
        digester.addBeanPropertySetter("*/validation/regex");
        digester.addBeanPropertySetter("*/validation/expression");
        digester.addSetNext("*/validation",
                "setValidatable");

        /*----- event -----*/
        digester.addObjectCreate("*/event",
                EventMetaDataImpl.class);
        digester.addBeanPropertySetter("*/event/onclick",
                "onClick");
        digester.addBeanPropertySetter("*/event/ondblclick",
                "onDblClick");
        digester.addBeanPropertySetter("*/event/onmousedown",
                "onMouseDown");
        digester.addBeanPropertySetter("*/event/onmouseup",
                "onMouseUp");
        digester.addBeanPropertySetter("*/event/onmouseover",
                "onMouseOver");
        digester.addBeanPropertySetter("*/event/onmousemove",
                "onMouseMove");
        digester.addBeanPropertySetter("*/event/onmouseout",
                "onMouseOut");
        digester.addBeanPropertySetter("*/event/onfocus",
                "onFocus");
        digester.addBeanPropertySetter("*/event/onblur",
                "onBlur");
        digester.addBeanPropertySetter("*/event/onkeypress",
                "onKeyPress");
        digester.addBeanPropertySetter("*/event/onkeydown",
                "onKeyDown");
        digester.addBeanPropertySetter("*/event/onkeyup",
                "onKeyUp");
        digester.addBeanPropertySetter("*/event/onchange",
                "onChange");
        digester.addSetNext("*/event",
                "setEvent");

        /*----- default value -----*/

        // constant
        digester.addObjectCreate("*/default-value-constant",
                pt.maisis.search.value.ConstantValue.class);
        digester.addBeanPropertySetter("*/default-value-constant",
                "constant");
        digester.addSetNext("*/default-value-constant",
                "setDefaultValue");

        // expression
        digester.addObjectCreate("*/default-value-expression",
                pt.maisis.search.value.ExpressionValue.class);
        digester.addBeanPropertySetter("*/default-value-expression",
                "expression");
        digester.addSetNext("*/default-value-expression",
                "setDefaultValue");

        // constants
        digester.addObjectCreate("*/default-value-list/constants",
                pt.maisis.search.value.ConstantListValue.class);
        digester.addObjectCreate("*/default-value-list/constants/constant",
                pt.maisis.search.value.KeyValue.class);
        digester.addBeanPropertySetter("*/default-value-list/constants/constant/key");
        digester.addBeanPropertySetter("*/default-value-list/constants/constant/value");
        digester.addBeanPropertySetter("*/default-value-list/constants/constant/selected");
        digester.addSetNext("*/default-value-list/constants/constant",
                "addKeyValue");
        digester.addSetNext("*/default-value-list/constants",
                "setDefaultValue");

        // sql
        digester.addObjectCreate("*/default-value-list/sql",
                pt.maisis.search.value.SqlValue.class);
        digester.addBeanPropertySetter("*/default-value-list/sql/data-source",
                "dataSource");
        digester.addBeanPropertySetter("*/default-value-list/sql/statement",
                "statement");
        digester.addBeanPropertySetter("*/default-value-list/sql/column-mapping/key",
                "keyIndex");
        digester.addBeanPropertySetter("*/default-value-list/sql/column-mapping/value",
                "valueIndex");

        digester.addObjectCreate("*/default-value-list/sql/selected-keys",
                java.util.ArrayList.class);
        digester.addCallMethod("*/default-value-list/sql/selected-keys/key",
                "add",
                1);
        digester.addCallParam("*/default-value-list/sql/selected-keys/key",
                0);
        digester.addSetNext("*/default-value-list/sql/selected-keys",
                "setSelected");

        digester.addRule("*/default-value-list/sql",
                new SqlValueSetNextRule("setDefaultValue"));

        // ajax
        digester.addObjectCreate("*/ajax",
                pt.maisis.search.value.AjaxValue.class);
        digester.addBeanPropertySetter("*/ajax/sql/data-source",
                "dataSource");
        digester.addBeanPropertySetter("*/ajax/sql/statement",
                "statement");
        digester.addBeanPropertySetter("*/ajax/sql/column-mapping/key",
                "keyIndex");
        digester.addBeanPropertySetter("*/ajax/sql/column-mapping/value",
                "valueIndex");
        digester.addBeanPropertySetter("*/ajax/source",
                "source");

        digester.addObjectCreate("*/ajax/parameters",
                AjaxParameterMetaData.class);
        digester.addCallMethod("*/ajax/parameters/parameter",
                "addParameter",
                1);
        digester.addCallParam("*/ajax/parameters/parameter",
                0);
        digester.addSetNext("*/ajax/parameters",
                "setParameterMetaData");

        digester.addRule("*/ajax",
                new AjaxValueSetNextRule("setAjaxValue"));

        digester.addSetNext("*/fragment-container",
                "addContainer");
        digester.addSetNext("*/container",
                "setContainer");
        digester.addSetNext("*/composite-container",
                "setContainer");

        /*----- dynamic search params -----*/
        digester.addFactoryCreate("*/dynamic-search-params",
                new DynamicSearchParametersFactory());
        digester.addSetNext("*/dynamic-search-params",
                "addSearchParameters");

        /*----- search properties -----*/
        digester.addCallMethod("*/search-param/properties/property",
                "addProperty", 2);
        digester.addCallParam("*/search-param/properties/property/name", 0);
        digester.addCallParam("*/search-param/properties/property/value", 1);

        digester.addSetNext("*/search-param",
                "addSearchParameter");

        /*----- composite result param -----*/
        digester.addObjectCreate("*/composite-result-param",
                CompositeResultParameterMetaData.class);
        digester.addBeanPropertySetter("*/composite-result-param/name");
        digester.addBeanPropertySetter("*/composite-result-param/selectable");
        digester.addBeanPropertySetter("*/composite-result-param/search-label",
                "searchLabel");
        digester.addBeanPropertySetter("*/composite-result-param/result-label",
                "resultLabel");
        digester.addBeanPropertySetter("*/composite-result-param/align");
        digester.addBeanPropertySetter("*/composite-result-param/width");
        digester.addBeanPropertySetter("*/composite-result-param/height");
        digester.addBeanPropertySetter("*/composite-result-param/header-style",
                "headerStyle");
        digester.addBeanPropertySetter("*/composite-result-param/action");
        digester.addCallMethod("*/composite-result-param/properties/property",
                "addProperty", 2);
        digester.addCallParam("*/composite-result-param/properties/property/name", 0);
        digester.addCallParam("*/composite-result-param/properties/property/value", 1);

        /*----- result param -----*/
        digester.addObjectCreate("*/result-param",
                SimpleResultParameterMetaData.class);
        digester.addBeanPropertySetter("*/result-param/name");
        digester.addBeanPropertySetter("*/result-param/selectable");
        digester.addBeanPropertySetter("*/result-param/search-label",
                "searchLabel");
        digester.addBeanPropertySetter("*/result-param/result-label",
                "resultLabel");
        digester.addObjectCreate("*/result-param/fields",
                FieldMetaData.class);
        digester.addCallMethod("*/result-param/fields/field",
                "addField",
                1);
        digester.addCallParam("*/result-param/fields/field",
                0);
        digester.addSetNext("*/result-param/fields",
                "setFieldMetaData");
        digester.addBeanPropertySetter("*/result-param/align");
        digester.addBeanPropertySetter("*/result-param/action");
        digester.addBeanPropertySetter("*/result-param/width");
        digester.addBeanPropertySetter("*/result-param/height");
        digester.addBeanPropertySetter("*/result-param/header-style",
                "headerStyle");
        digester.addBeanPropertySetter("*/result-param/value-style",
                "valueStyle");
        digester.addCallMethod("*/result-param/formatter",
                "setFormatter", 3);
        digester.addCallParam("*/result-param/formatter/name", 0);
        digester.addCallParam("*/result-param/formatter/pattern", 1);
        digester.addCallParam("*/result-param/formatter/null-value", 2);
        digester.addCallMethod("*/result-param/exporter-formatter",
                "setExporterFormatter", 3);
        digester.addCallParam("*/result-param/exporter-formatter/name", 0);
        digester.addCallParam("*/result-param/exporter-formatter/pattern", 1);
        digester.addCallParam("*/result-param/exporter-formatter/null-value", 2);

        /*----- result properties -----*/
        digester.addCallMethod("*/result-param/properties/property",
                "addProperty", 2);
        digester.addCallParam("*/result-param/properties/property/name", 0);
        digester.addCallParam("*/result-param/properties/property/value", 1);

        digester.addSetNext("*/result-param",
                "addResultParameter");

        digester.addSetNext("*/composite-result-param",
                "addResultParameter");

        /*----- dynamic result params -----*/
        digester.addFactoryCreate("*/dynamic-result-params",
                new DynamicResultParametersFactory());
        digester.addSetNext("*/dynamic-result-params",
                "addResultParameters");

        /*----- required results -----*/
        digester.addObjectCreate("*/required-result-param",
                RequiredResultParameterMetaData.class);
        digester.addBeanPropertySetter("*/required-result-param/name");
        digester.addObjectCreate("*/required-result-param/fields",
                FieldMetaData.class);
        digester.addCallMethod("*/required-result-param/fields/field",
                "addField",
                1);
        digester.addCallParam("*/required-result-param/fields/field",
                0);
        digester.addSetNext("*/required-result-param/fields",
                "setFieldMetaData");

        digester.addCallMethod("*/required-result-param/properties/property",
                "addProperty", 2);
        digester.addCallParam("*/required-result-param/properties/property/name", 0);
        digester.addCallParam("*/required-result-param/properties/property/value", 1);

        digester.addBeanPropertySetter("*/required-result-param/depends");
        digester.addBeanPropertySetter("*/required-result-param/unless");

        digester.addSetNext("*/required-result-param",
                "addRequiredResultParameter");

        /*----- dynamic required result params -----*/
        digester.addFactoryCreate("*/dynamic-required-result-params",
                new DynamicRequiredResultParametersFactory());
        digester.addSetNext("*/dynamic-required-result-params",
                "addRequiredResultParameters");

        /*----- selected result param -----*/
        digester.addCallMethod("*/selected-result-param",
                "addSelectedResultParameter", 0);

        /*----- dynamic selected result params -----*/
        digester.addFactoryCreate("*/dynamic-selected-result-params",
                new DynamicSelectedResultParametersFactory());
        digester.addSetNext("*/dynamic-selected-result-params",
                "addSelectedResultParameters");

        /*----- selected result param order -----*/
        digester.addObjectCreate("*/selected-result-param-order",
                SelectedResultParameterMetaDataOrder.class);
        digester.addBeanPropertySetter("*/selected-result-param-order/name");
        digester.addBeanPropertySetter("*/selected-result-param-order/order-type",
                "orderTypeAsString");
        digester.addSetNext("*/selected-result-param-order",
                "addSelectedResultParameterOrder");

        /*----- init -----*/
        digester.addCallMethod("*/search-metadata", "init");
    }
}

/**
 * Factory responsável por criar uma instância de
 * {@link DynamicSearchParameters}, tendo em conta o atributo
 * <code>class</code> definido no ficheiro de configuracão.
 * O Objecto no topo da stack deve ser uma instância de
 * <code>List</code>.
 */
final class DynamicSearchParametersFactory
        extends AbstractObjectCreationFactory {

    private static Log log = LogFactory.getLog(DynamicSearchParametersFactory.class);

    public Object createObject(final Attributes attributes) {
        final String className = attributes.getValue("class");
        if (className != null) {
            try {
                final DynamicSearchParameters params = (DynamicSearchParameters) Class.forName(className).newInstance();
                return params.getSearchParameters();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return null;
    }
}

/**
 * Factory responsável por criar uma instância de
 * {@link DynamicResultParameters}, tendo em conta o atributo
 * <code>class</code> definido no ficheiro de configuracão.
 * O Objecto no topo da stack deve ser uma instância de
 * <code>List</code>.
 */
final class DynamicResultParametersFactory
        extends AbstractObjectCreationFactory {

    private static Log log = LogFactory.getLog(DynamicResultParametersFactory.class);

    public Object createObject(final Attributes attributes) {
        final String className = attributes.getValue("class");
        if (className != null) {
            try {
                final DynamicResultParameters params =
                        (DynamicResultParameters) Class.forName(className).newInstance();
                return params.getResultParameters();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return null;
    }
}

/**
 * Factory responsável por criar uma instância de
 * {@link DynamicRequiredResultParameters}, tendo em conta o
 * atributo <code>class</code> definido no ficheiro de configuracão.
 * O Objecto no topo da stack deve ser uma instância de
 * <code>List</code>.
 */
final class DynamicRequiredResultParametersFactory
        extends AbstractObjectCreationFactory {

    private static Log log = LogFactory.getLog(DynamicRequiredResultParametersFactory.class);

    public Object createObject(final Attributes attributes) {
        final String className = attributes.getValue("class");
        if (className != null) {
            try {
                final DynamicRequiredResultParameters params =
                        (DynamicRequiredResultParameters) Class.forName(className).newInstance();
                return params.getRequiredResultParameters();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return null;
    }
}

/**
 * Factory responsável por criar uma instância de
 * {@link DynamicRequiredResultParameters}, tendo em conta o
 * atributo <code>class</code> definido no ficheiro de configuracão.
 * O Objecto no topo da stack deve ser uma instância de
 * <code>List</code>.
 */
final class DynamicSelectedResultParametersFactory
        extends AbstractObjectCreationFactory {

    private static Log log = LogFactory.getLog(DynamicSelectedResultParametersFactory.class);

    public Object createObject(final Attributes attributes) {
        final String className = attributes.getValue("class");
        if (className != null) {
            try {
                final DynamicSelectedResultParameters params =
                        (DynamicSelectedResultParameters) Class.forName(className).newInstance();
                return params.getSelectedResultParameters();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return null;
    }
}

/**
 * 
 */
final class ConverterValidatorRule
        extends BeanPropertySetterRule {

    private static Log log = LogFactory.getLog(ConverterValidatorRule.class);

    public ConverterValidatorRule() {
        super();
    }

    public ConverterValidatorRule(String propertyName) {
        super(propertyName);
    }

    @Override
    public void end(String namespace, String name) throws Exception {
        SearchConfig config = SearchConfig.getInstance();
        String clazz = config.getSearchConverter(bodyText);
        if (clazz == null) {
            String error = MessageResources.getInstance().getMessage("converter.unknown", bodyText);
            throw new SearchConfigException(error);
        }
        try {
            Object o = Class.forName(clazz).newInstance();
            if (o instanceof Converter) {
                ValidatableContainer top = (ValidatableContainer) digester.peek();
                Converter converter = (Converter) o;
                top.setConverter(converter);
                return;
            }

        } catch (Exception e) {
            log.error(e);
        }
        String error = MessageResources.getInstance().getMessage("converter.invalid", bodyText);
        throw new SearchConfigException(error);
    }
}

final class ConverterProperties
        extends CallMethodRule {

    public ConverterProperties() {
        super("addProperty", 2);
    }

    @Override
    public void end() throws Exception {
        ValidatableContainer top = (ValidatableContainer) digester.peek();
        Converter converter = top.getConverter();
        Object[] params = (Object[]) digester.peekParams();
        if (params != null) {
            converter.addProperty((String) params[0], (String) params[1]);
        }
    }
}

final class SqlValueSetNextRule extends SetNextRule {

    public SqlValueSetNextRule(String methodName) {
        super(methodName);
    }

    @Override
    public void end() throws Exception {
        SqlValue value = (SqlValue) digester.peek();
        if (value.getDataSource() == null) {
            SearchMetaData smd = (SearchMetaData) digester.peek(3);
            value.setDataSource(smd.getDataSource());
        }
        super.end();
    }
}

final class AjaxValueSetNextRule extends SetNextRule {

    public AjaxValueSetNextRule(String methodName) {
        super(methodName);
    }

    @Override
    public void end() throws Exception {
        AjaxValue value = (AjaxValue) digester.peek();
        if (value.getDataSource() == null) {
            SearchMetaData smd = (SearchMetaData) digester.peek(3);
            value.setDataSource(smd.getDataSource());
        }
        super.end();
    }
}
