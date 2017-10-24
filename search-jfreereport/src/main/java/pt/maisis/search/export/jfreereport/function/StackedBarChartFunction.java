package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;


public class StackedBarChartFunction extends AbstractCategoryBasedChartFunction {

    protected JFreeChart createChart() {
        return ChartFactory
            .createStackedBarChart(getTitle(),
                                   getDomainAxisLabel(),
                                   getRangeAxisLabel(),
                                   getDataset(),
                                   getPlotOrientation(), 
                                   isShowLegend(),
                                   false,
                                   false);
    }
}
