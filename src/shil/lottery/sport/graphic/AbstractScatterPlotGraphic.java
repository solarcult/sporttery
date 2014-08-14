package shil.lottery.sport.graphic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public abstract class AbstractScatterPlotGraphic {
	
	public AbstractScatterPlotGraphic(String name,String xAxisLabel,String yAxisLabel)
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot(name, xAxisLabel, yAxisLabel , getDeltaCards());
		ChartFrame chartFrame = new ChartFrame("彩票的奇妙世界屋", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}

	public abstract XYDataset getDeltaCards();
}
