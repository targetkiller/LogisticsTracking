package jChartPackage;

import java.awt.Color; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import models.OrderForm;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;



public class ChartImplement implements ChartI {

	@Override
	public String getPieChartUrl(List array)  {
		
		JFreeChart chart=null;
		Object[] list =array.toArray();
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(int i=0;i<list.length;i+=2){
			dataset.setValue(list[i].toString(), Double.parseDouble(list[i+1].toString()));
		}
		chart = ChartFactory.createPieChart("Pie Chart", dataset, false, false, false);
		BufferedImage bf = chart.createBufferedImage(800, 400);
		String target = "cacheForChart" + File.separator + System.currentTimeMillis() + ".png" ;
		try {
			ImageIO.write(bf, "PNG", new File(target));
		}catch (IOException e) {
			
		}
		
		File file = new File(target);
		if(file.exists()) {
			return target;
		}
		return null;
	}

	@Override
	public String getLineChartUrl(List array) {
		JFreeChart chart=null;
		Object[] list =array.toArray();
		CategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i=0;i<list.length;i+=2){
			
			((DefaultCategoryDataset) dataset).addValue(Double.parseDouble(list[i+1].toString()),"tongji",list[i].toString());
		}
		chart = ChartFactory.createLineChart("订单统计曲线","TYPE", "单位:万",dataset,PlotOrientation.VERTICAL, false, true, false);
		//chart = ChartFactory.createPieChart("Pie Chart", dataset, false, false, false);
		CategoryPlot plot=chart.getCategoryPlot(); 
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLUE);//纵坐标格线颜色
        plot.setDomainGridlinePaint(Color.BLACK);//横坐标格线颜色
        plot.setDomainGridlinesVisible(true);//显示横坐标格线
        plot.setRangeGridlinesVisible(true);//显示纵坐标格线
                    
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        //DecimalFormat decimalformat1 = new DecimalFormat("##.##");//数据点显示数据值的格式
        renderer.setBaseItemLabelsVisible(true);//基本项标签显示
                 //上面这几句就决定了数据点按照设定的格式显示数据值  
        renderer.setSeriesShapesFilled(1, true);//在数据点显示实心的小图标
        renderer.setSeriesShapesVisible(1, true);//设置显示小图标
        BufferedImage bf = chart.createBufferedImage(800, 400);
        
        
        String target = "cacheForChart" + File.separator + System.currentTimeMillis() + ".png" ;
		try {
			ImageIO.write(bf, "PNG", new File(target));
		}catch (IOException e) {
			
		}
		
		File file = new File(target);
		if(file.exists()) {
			return target;
		}
		return null;
	}

	@Override
	public String getBarChartUrl(List<OrderForm> array) {
		JFreeChart chart=null;
		Object[] list =array.toArray();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<list.length;i+=2){
			dataset.addValue(Double.parseDouble(list[i+1].toString()), list[i].toString(), list[i].toString());
			}
		chart = ChartFactory.createBarChart("Bar Chart", 
				"Category", "Value", 
				dataset, PlotOrientation.VERTICAL,
				true, true, false);
		BufferedImage bf = chart.createBufferedImage(800, 400);
		
		String target = "cacheForChart" + File.separator + System.currentTimeMillis() + ".png" ;
		try {
			ImageIO.write(bf, "PNG", new File(target));
		}catch (IOException e) {
			
		}
		
		File file = new File(target);
		if(file.exists()) {
			return target;
		}
		return null;
	}

	


}
