package com.scavengers.plane.system.dao;

import com.scavengers.plane.system.entity.Plane;
import com.scavengers.plane.system.utils.SQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 飞机Dao
 */
public class PlanesDao {

    //搜索除了符合始发地和目的地的航班以及日期
    public ArrayList<Plane> planeTicketsQuery(String startplace, String endplace, String departureDate, int people, boolean isSelect) {
        String mysql;
        if (!isSelect) {
            mysql = "select * from Flight , airCompany,Cabin where fromCity='" + startplace +
                    "' and toCity='" + endplace + "' and departureDate='" + departureDate + "' " +
                    "and airCompany.companyID = Flight.companyID and Cabin.flightID = Flight.flightID";
        } else {
            mysql = "select * from Flight , airCompany,Cabin where fromCity='" + startplace +
                    "' and toCity='" + endplace + "' and departureDate='" + departureDate + "' " +
                    "and airCompany.companyID = Flight.companyID and Cabin.flightID = Flight.flightID order by departureDate,departureTime";
        }
        System.out.println(mysql);
        return getPlanes(mysql, people);
    }

    //获取列车信息数组列表
    private ArrayList<Plane> getPlanes(String mysql, int people) {
        ArrayList<Plane> planes = new ArrayList<>();
        ArrayList<Plane> res = new ArrayList<>();
        try {
            ResultSet rs = SQLHelper.executeQuery(mysql);
            while (rs.next()) {
                Plane plane = new Plane();
                if (rs.getString(13).equals("经济舱")) {
                    if (rs.getInt(15) >= people) {
                        plane.setLow(rs.getString(16) + "(剩余" + rs.getInt(15) + ")");
                    } else {
                        plane.setLow("席位不足!");
                    }
                }
                if (rs.getString(13).equals("商务舱")) {
                    if (rs.getInt(15) >= people) {
                        plane.setMiddle(rs.getString(16) + "(剩余" + rs.getInt(15) + ")");
                    } else {
                        plane.setMiddle("席位不足!");
                    }
                }
                if (rs.getString(13).equals("头等舱")) {
                    if (rs.getInt(15) >= people) {
                        plane.setHigh(rs.getString(16) + "(剩余" + rs.getInt(15) + ")");
                    } else {
                        plane.setHigh("席位不足!");
                    }
                }
                plane.setFlightID(rs.getString(1));//航班号
                plane.setAirCompany(rs.getString(10));//航空公司
                plane.setModel(rs.getString(3));
                plane.setStartplace(rs.getString(4));
                plane.setEndplace(rs.getString(5));
                plane.setStarttime(rs.getString(8));
                plane.setStratDate(rs.getString(7));
                plane.setMile(rs.getInt(6));
                planes.add(plane);
            }
            SQLHelper.closeConnection();
        } catch (SQLException e) {
            System.out.println("获取列车数组列表方法中报错");
        }
        for (int i = 0; i < planes.size(); i++) {
            if (i % 3 == 0) {
                Plane plane = new Plane();
                plane = planes.get(i);
                plane.setMiddle(planes.get(i + 2).getMiddle());
                plane.setLow(planes.get(i + 1).getLow());
                res.add(plane);
            }
        }
        return res;
    }
}
