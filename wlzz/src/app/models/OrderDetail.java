package models;

import javax.persistence.*;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity(name = "order_detail")
public class OrderDetail extends GenericModel implements Comparable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;
    @Required
    @Column(name = "order_form_id")
    public int orderFormId;
    @Required
    @Column(name = "station_id")
    public int stationId;
    @Required
    @Column(name = "user_id")
    public int userId;
    @Required
    @Column(name = "next_station_id")
    public int nextStationId;
    @Required
    @Column(name = "update_time")
    public long updateTime;
    @Column(name = "description")
    @MaxSize(100)
    public String description;
    @Required(message = "")
    @Column(name = "is_finish", nullable = false)
    public int isFinish;
    @Transient
    public User user;
    @Transient
    public UserInfo userInfo;
    @Transient
    public Station station;
    @Transient
    public Station nextStation;
    @Transient
    public String time;

    public OrderDetail() {
    }

    public OrderDetail(@Required int orderFormId,
            @Required int stationId, @Required int userId, @Required int nextStationId,
            @Required long updateTime) {
        this.orderFormId = orderFormId;
        this.stationId = stationId;
        this.userId = userId;
        this.nextStationId = nextStationId;
        this.updateTime = updateTime;
    }

    public OrderDetail(@Required int orderFormId,
            @Required int stationId, @Required int userId, @Required int nextStationId,
            @Required long updateTime, String description) {
        this(orderFormId, stationId, userId, nextStationId, updateTime);
        this.description = description;
    }

    public static void delete(@Required int id) {
        UserInfo uif = UserInfo.findById(id);
        uif.delete();
    }

    public OrderDetail update(@Required int orderFormId,
            @Required int stationId, @Required int userId, @Required int nextStationId,
            @Required long updateTime, String description) {
        this.orderFormId = orderFormId;
        this.stationId = stationId;
        this.userId = userId;
        this.nextStationId = nextStationId;
        this.updateTime = updateTime;
        this.description = description;
        return save();
    }

    public int compareTo(Object o) {
        if (this.updateTime > ((OrderDetail) o).updateTime) {
            return 1;
        }
        if (this.updateTime < ((OrderDetail) o).updateTime) {
            return -1;
        }
        return 0;
    }
}
