package controllers;

import java.util.ArrayList; 
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class JChart {
	public static JFreeChart createChart(ArrayList array,String chartType){
		JFreeChart chart=null;
		Object[] list =array.toArray();
		try{
			if(chartType.equals("Pie")){
				
				DefaultPieDataset dataset = new DefaultPieDataset();
				for(int i=0;i<list.length;i++){
				dataset.setValue((String)list[i], Double.parseDouble((String)list[i]));
				}
				chart = ChartFactory.createPieChart("Pie Chart", dataset, false, false, false);
				
			}
			else if(chartType.equals("Bar")){
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
				for(int i=0;i<list.length;i+=2){
					dataset.addValue(Double.parseDouble((String)list[i]), (String)list[i], null);
					}
				chart = ChartFactory.createBarChart("Bar Chart", 
						"Category", "Value", 
						dataset, PlotOrientation.VERTICAL,
						true, true, false);
			}
		}catch(Exception e){System.err.println(e.toString());}
		
		return chart;
		
	}
}
