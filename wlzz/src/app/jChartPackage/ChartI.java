package jChartPackage;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import models.OrderForm;

import org.jfree.chart.JFreeChart;



public interface ChartI {
	
	 String getPieChartUrl(List<OrderForm> array);
	
	 String getBarChartUrl(List<OrderForm> array);
	
	 String getLineChartUrl(List<OrderForm> array);
	
}
