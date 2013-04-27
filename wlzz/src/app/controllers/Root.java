package controllers;

import jChartPackage.ChartI; 
import jChartPackage.ChartImplement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import models.OrderForm;
import models.RootQuery;
import models.Station;

import org.jfree.util.Log;
import org.scauhci.enumvalue.RoleNameEnum;

import play.mvc.Controller;

public class Root extends Controller {

    //关于建站；
    public static void createStation() {
        Stations.index();
    }

    public static void allStation() {
        Station.all();
    }

    public static void orderCount() {
         List<Station> allStation = Station.findAll();
        render(allStation);
    }

    //根据时间段返回订单数量
    public static void orderNumCount(String bTime, String aTime, String stationId, String proxy) {
        int count = 0;
        if (proxy.equals("create")) {
            //count = RootQuery.countCreateNum(bTime, aTime, stationId);
        } else {
            count = RootQuery.countCrossByNum(bTime, aTime, stationId);
        }
        render(count);//"orderCount",count);
    }
    //返回统计图

    public static void chartOrder(String firstTime, String lastTime, String chartType, String dataType, String stationId, String proxy) {//传入时间区间；yyyy-MM格式

        Log.info("the front has sent message to me .");

        String userChartUrl = null;
        List array = null;
        array = RootQuery.countCrossBy(firstTime, lastTime, dataType, stationId,proxy);

        ChartI chart = new ChartImplement();
        if (chartType.equals("Pie")) {
            userChartUrl = chart.getPieChartUrl(array);
        } else if (chartType.equals("Bar")) {
            userChartUrl = chart.getBarChartUrl(array);
        } else if (chartType.equals("Line")) {
            userChartUrl = chart.getLineChartUrl(array);
        }
        renderText("/" + userChartUrl);
    }
    //根据id返回某条订单的信息----willing；

    public static void orderOneInfo(String orderId, String message) {
        int id = Integer.parseInt(orderId);
        OrderForms.show(id, message);
    }
    //返回某时间段的所有订单信息----willing；

    public static void orderInfoS(String firstTime, String lastTime) {
        List<OrderForm> orderForms = RootQuery.getSeriesInfo(firstTime, lastTime);
        render(orderForms);
    }
}
/*
$.post(
hosturl,
{"firstTime":B,"lastTime":A,"chartType":type,"dataType":dataType,"stationId":account，"proxy":countProxy},
function(mgs){
alert(mgs);
detailDiv = document.getElementById("soga");
detailDiv.src = mgs;
}, "text");
function callNum(B,A,account,countProxy){
var bTime = B;
var aTime = A;
var stationId = account;
var hosturl = "@{Root.orderNumCount}";
$.post(
hosturl,
{"bTime":B,"aTime":A,"stationId":stationId,"proxy",countProxy},
function(mgs){
alert(mgs);
detailDiv = document.getElementById("sogaData");
detailDiv.innerHTML = mgs;
}, "text");
} 
 */
