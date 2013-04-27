package jChartPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

public class Pie3DDemo { 
	
	private DefaultPieDataset getDataset() { 
	
		String[] section = new String[] { "Jan","Feb","Mar","Apr","May","Jun", "Jul","Aug","Sep","Oct","Nov","Dec" }; 
		
		double[] data = new double[section.length]; 
		 for (int i = 0; i < data.length; i++) { 
		     data[i] = 10 + (Math.random() * 10); 
		} 
		
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for (int i = 0; i < data.length; i++) { 
		       dataset.setValue(section[i], data[i]); 
		} 
		return dataset; 
	} 
	
	public String getChartViewer(HttpServletRequest request, HttpServletResponse response) { 
		 DefaultPieDataset dataset = getDataset(); 
		
		 JFreeChart chart = ChartFactory.createPieChart( 
		  "Pie3D Chart Demo",  // chart title 
		  dataset,             // data 
		  true,                // include legend 
		  true, 
		  false 
		 ); 
		chart.setBackgroundPaint(Color.cyan); 
		PiePlot plot = (PiePlot) chart.getPlot(); 
		plot.setNoDataMessage("No data available"); 
		plot.setURLGenerator(new StandardPieURLGenerator("Bar3DDemo.jsp","section")); 
		plot.setLabelGenerator(null); 
		ChartRenderingInfo info = null; 
		HttpSession session = request.getSession(); 
		try {  
			 response.setContentType("text/html"); 
			 info = new ChartRenderingInfo(new StandardEntityCollection()); 
			 BufferedImage chartImage = chart.createBufferedImage(640, 400, info); 
			 session.setAttribute("chartImage", chartImage); 
			 PrintWriter writer = new PrintWriter(response.getWriter()); 
			 ChartUtilities.writeImageMap(writer, "imageMap", info, false); 
			 writer.flush(); 
			} catch (Exception e) { } 
		
		String pathInfo = "http://"; 
		pathInfo += request.getServerName(); 
		int port = request.getServerPort(); 
		pathInfo += ":"+String.valueOf(port); 
		pathInfo += request.getContextPath(); 
		String chartViewer = pathInfo + "/servlet/ChartViewer"; 
		return chartViewer; 
	} 
}