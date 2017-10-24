package pt.maisis.search;

import java.io.Serializable;

/**
 * Classe que representa um campo de retorno necessário no 
 * resultado de uma dada pesquisa.
 *
 * @version 1.0
 */
public class RequiredResultParameterMetaData
        extends DynamicParameterMetaDataImpl
        implements Serializable {

    private FieldMetaData field;
    private String depends;
    private String unless;

    public RequiredResultParameterMetaData() {
    }

    public void setFieldMetaData(final FieldMetaData field) {
        this.field = field;
    }

    public FieldMetaData getFieldMetaData() {
        return this.field;
    }

    public String getDepends() {
        return this.depends;
    }

    public void setDepends(final String depends) {
        this.depends = depends;
    }

    public String getUnless() {
        return this.unless;
    }

    public void setUnless(final String unless) {
        this.unless = unless;
    }

    public String toString() {
        return new StringBuffer("RequiredResultParameterMetaData[").append("name=").append(super.name).append(", field=").append(this.field).append(", depends=").append(this.depends).append(", unless=").append(this.unless).append("]").toString();
    }
}
