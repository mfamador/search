package pt.maisis.search;

import java.util.List;
import java.util.ArrayList;

public class AjaxParameterMetaData {

    private List parameters = new ArrayList();

    public AjaxParameterMetaData() {
    }

    public List getParameters() {
        return this.parameters;
    }

    public String getParameter(final int index) {
        return (String) this.parameters.get(index);
    }

    public void addParameter(final String parameter) {
        this.parameters.add(parameter);
    }

    public void addParameterMetaData(final AjaxParameterMetaData parameter) {
        this.parameters.addAll(parameter.getParameters());
    }

    public String toString() {
        return new StringBuffer("AjaxParameterMetaData[").append("parameters=").append(this.parameters).append("]").toString();
    }
}
