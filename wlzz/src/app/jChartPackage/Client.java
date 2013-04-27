package jChartPackage;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import models.RootQuery;

import org.jfree.chart.JFreeChart;

public class Client {
	public static void main(String[] args){
		ChartI chart = new ChartImplement();
		ArrayList array = null;
		String userChartUrl = chart.getPieChartUrl(array);
	}
}
