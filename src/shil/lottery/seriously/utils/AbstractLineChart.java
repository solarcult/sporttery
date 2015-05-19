package shil.lottery.seriously.utils;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;

public abstract class AbstractLineChart {

	public AbstractLineChart(String name,String xAxisLabel,String yAxisLabel) {
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
		JFreeChart jFreeChart = ChartFactory.createLineChart(name, xAxisLabel, yAxisLabel , getXYDataset());
		ChartFrame chartFrame = new ChartFrame("測試結果展示LineChart圖(為啥是繁體?)", jFreeChart);
		CategoryPlot categoryplot = (CategoryPlot) jFreeChart.getPlot();
        // Y轴  
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
		setNumberAxis(numberaxis);  
		// x轴  
		CategoryAxis domainAxis = (CategoryAxis) categoryplot.getDomainAxis();  
		setDomainAxis(domainAxis);
		
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	private static void setNumberAxis(NumberAxis numberaxis) {
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// 是否显示零点
		numberaxis.setAutoRangeIncludesZero(true);
		numberaxis.setAutoTickUnitSelection(false);
		// 解决Y轴标题中文乱码
		numberaxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 14));
	}
	
	private static void setDomainAxis(CategoryAxis domainAxis) {
		// 解决x轴坐标上中文乱码
		domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
		// 解决x轴标题中文乱码
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 14));
		// 用于显示X轴刻度
		domainAxis.setTickMarksVisible(true);
	}

	public abstract CategoryDataset getXYDataset();
}
