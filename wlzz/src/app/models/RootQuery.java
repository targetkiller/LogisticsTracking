package models;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.scauhci.util.Dates;

public class RootQuery {

    //反馈订单信息
    public static OrderForm getOneInfo(String orderId) {//更据订单id返回某一条订单的信息；
        OrderForm orderForm = OrderForm.findById(orderId);
        return orderForm;
    }

    public static List<OrderForm> getSeriesInfo(String beforeTime, String afterTime) {//更据时间区间来返回相关订单的信息；
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Long startTime;
        Long endTime;
        List<OrderForm> orderFormList = null;
        try {
            startTime = SDF.parse(beforeTime).getTime();
            endTime = SDF.parse(afterTime).getTime();
            orderFormList = OrderForm.find("create_time > ? and create_time < ?", startTime, endTime).fetch();
        } catch (java.text.ParseException ex) {
            Logger.getLogger(RootQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderFormList;
    }

    //统计订单数---willing
    public static int countCreate(String bTime, String aTime, String stationId) {//返回时间段内所有订单数；
        int count = 0;
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Long startTime;
        Long endTime;
        try {
            startTime = SDF.parse(bTime).getTime();
            endTime = SDF.parse(aTime).getTime();
            count = (int) OrderForm.count("create_time > ? and create_time < ? and station_id = ?", startTime, endTime,Integer.parseInt(stationId));

        } catch (java.text.ParseException ex) {
            Logger.getLogger(RootQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    //统计订单数---danyan		
    public static int countCrossByNum(String bTime, String aTime, String stationId) {//返回时间段内所有订单数；
        int count = 0;
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        Long startTime;
        Long endTime;
        try {
            startTime = SDF.parse(bTime).getTime();
            endTime = SDF.parse(aTime).getTime();
            count = (int) OrderDetail.count("update_time > ? and update_time < ? and next_station_id = ?", startTime, endTime, Integer.parseInt(stationId));
           
        } catch (java.text.ParseException ex) {
            Logger.getLogger(RootQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    /**
     * 
     * @param beforeTime
     * @param afterTime
     * @param type
     * @param stationId
     * @return
     */
    public static ArrayList countCrossBy(String beforeTime, String afterTime, String type, String stationId, String proxy) {//更据时间返回对应区间内的订单总数；
        //type代表查询数据的时间类型如按月分或按照年份；
        ArrayList arrayList = new ArrayList();
        if (type.equals("year")) {
            int startTime = Integer.parseInt(beforeTime.substring(0, 4));
            int endTime = Integer.parseInt(afterTime.substring(0, 4));
            for (int i = startTime; i <= endTime; i++) {
                arrayList.add(i + "");
                if (proxy.equals("create")) {
                    arrayList.add(countCreate(i + "-" + "01" + "-" + "01", (i + 1) + "-" + "01" + "-" + "01", stationId));
                } else {
                    arrayList.add(countCrossByNum(i + "-" + "01" + "-" + "01", (i + 1) + "-" + "01" + "-" + "01", stationId));
                }
            }
        }
        if (type.equals("month")) {
            int startYear = Integer.parseInt(beforeTime.substring(0, 4));
            int endYear = Integer.parseInt(afterTime.substring(0, 4));
            int startMonth = Integer.parseInt(beforeTime.substring(5, 7));
            int endMonth = Integer.parseInt(afterTime.substring(5, 7));
            int monthLength = 12 - startMonth + 1 + endMonth + (endYear - startYear - 1) * 12;
            for (int i = 0; i < monthLength; i++) {
                arrayList.add(startYear + "-" + startMonth);
                if (proxy.equals("create")) {
                    arrayList.add(countCreate(startYear + "-" + startMonth + "01", startYear + "-" + (startMonth + 1) + "01", stationId));
                } else {
                    arrayList.add(countCrossByNum(startYear + "-" + startMonth + "01", startYear + "-" + (startMonth + 1) + "01", stationId));
                }
                startYear = startYear + (startMonth + 1) / 12;
                startMonth = (startMonth + 1) % 12;
            }
            if (type.equals("day")) {
                Date startDate = Dates.getDateByDateStr(beforeTime);
                Date endDate = Dates.getDateByDateStr(afterTime);
                int dayLength = Dates.daysBetweenDates(startDate, endDate) + 1;
                for (int i = 0; i < dayLength; i++) {
                    arrayList.add(Dates.getDateFormatStr(startDate, "yyyy-MM-dd"));
                    if (proxy.equals("create")) {
                        arrayList.add(countCreate(Dates.getDateFormatStr(startDate, "yyyy-MM-dd"), Dates.getDateFormatStr(Dates.getDateAfterDays(startDate, 1), "yyyy-MM-dd"), stationId));
                    } else {
                        arrayList.add(countCrossByNum(Dates.getDateFormatStr(startDate, "yyyy-MM-dd"), Dates.getDateFormatStr(Dates.getDateAfterDays(startDate, 1), "yyyy-MM-dd"), stationId));
                    }
                    startDate = Dates.getDateAfterDays(startDate, 1);
                }
            }
        }

        return arrayList;

    }

    /**
     * 返回所有工作站的信息列表；
     * @return List<Station> 工作站的信息列表
     */
    public static List<Station> getStationInfo() {
        List<Station> station_list = Station.findAll();
        return station_list;
    }

    /**
     * 更据工作站id返回某一工作站的信息；
     * @param stationId 工作站id
     * @return Station 工作站
     */
    public static Station getStationInfo(int stationId) {
        Station station = Station.findById(stationId);
        return station;
    }
}
