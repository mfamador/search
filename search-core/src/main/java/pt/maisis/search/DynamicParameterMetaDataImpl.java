/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @version 1.0
 */
public class DynamicParameterMetaDataImpl
        extends ParameterMetaDataImpl
        implements DynamicParameterMetaData {

    /** Map vazio a ser utilizado quando nenhuma propriedade dinamica e 
    declarada no descriptor xml da pesquisa. */
    private static final Map NO_PROPERTIES = new HashMap(0);
    private Map properties;

    public DynamicParameterMetaDataImpl() {
    }

    public Map getProperties() {
        return (this.properties == null)
                ? NO_PROPERTIES
                : this.properties;
    }

    public String getProperty(final String name) {
        return (this.properties == null)
                ? null
                : (String) this.properties.get(name);
    }

    public void addProperty(final String name, final String value) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }
        properties.put(name, value);
    }
}
