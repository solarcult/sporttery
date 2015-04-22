package shil.lottery.sport.graphic;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.xy.XYDataset;

public abstract class AbstractScatterPlotGraphic {
	
	public AbstractScatterPlotGraphic(String name,String xAxisLabel,String yAxisLabel)
	{
		//创建主题样式     
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");     
		//设置标题字体     
		standardChartTheme.setExtraLargeFont(new Font("微软雅黑",Font.BOLD,20));     
		//设置图例的字体     
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));     
		//设置轴向的字体     
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));     
		//应用主题样式     
		ChartFactory.setChartTheme(standardChartTheme); 
		JFreeChart jFreeChart = ChartFactory.createScatterPlot(name, xAxisLabel, yAxisLabel , getDeltaCards());
		ChartFrame chartFrame = new ChartFrame("彩票的奇妙世界屋", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}

	public abstract XYDataset getDeltaCards();
}
