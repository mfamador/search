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
package pt.maisis.search.export.jfreereport;

import pt.maisis.search.ResultParameterMetaData;

import java.util.List;
import java.util.Iterator;


public class PageTool {
    private static final int A4_WIDTH  = 595;
    private static final int A4_HEIGHT = 842;

    private final int totalWidth;

    public PageTool(List rpmd) {
        int totalWidth = 0;
        Iterator it = rpmd.iterator();
        while (it.hasNext()) {
            totalWidth += ((ResultParameterMetaData) it.next()).getWidth();
        }
        this.totalWidth = totalWidth;
    }

    public int getWidth(int leftMargin, int rightMargin) {
        return getWidth(leftMargin + rightMargin);
    }

    public int getWidth(int margins) {
        int width = this.totalWidth + margins;
        /*
          Se o 'width' da pagina for inferior a largura da pagina A4
          (layout portrait) entao as medidas devem passar a ser as de
          uma pagina A4 portrait.

          A4 = 595 x 842 points

          Caso contrario a pagina ficara em landscape em que o comprimento
          da pagina e igual a soma dos comprimentos dos result params
          mais as margens.
        */
        return (width < A4_WIDTH) ? A4_WIDTH : width;
    }

    public int getHeight(int topMargin, int bottomMargin) {
        return getHeight(topMargin + bottomMargin);
    }

    public int getHeight(int margins) {
        int width = getWidth(margins);

        if (width == A4_WIDTH) {
            return A4_HEIGHT;
        }
        return (int) (width / 1.4141);
    }
}
