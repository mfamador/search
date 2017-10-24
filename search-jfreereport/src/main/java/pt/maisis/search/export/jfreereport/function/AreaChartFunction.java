package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;


public class AreaChartFunction extends AbstractCategoryBasedChartFunction {

    protected JFreeChart createChart() {
        return ChartFactory
            .createAreaChart(getTitle(),
                             getDomainAxisLabel(),
                             getRangeAxisLabel(),
                             getDataset(),
                             getPlotOrientation(), 
                             isShowLegend(),
                             false,
                             false);
    }
}
