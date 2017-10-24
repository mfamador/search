package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;


/**
 * Interface que deve ser implementado em situacões em que 
 * é necessário customizar um dado gráfico de uma forma que
 * não é possível fazê-lo actualmente utilizando as funcões
 * de geracão de gráficos.
 *
 * @version 1.0
 */
public interface ChartCustomizer {

    public void customize(JFreeChart chart);
}
