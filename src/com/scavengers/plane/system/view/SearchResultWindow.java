package com.scavengers.plane.system.view;

import com.scavengers.plane.system.dao.OrderSheetsDao;
import com.scavengers.plane.system.dao.PlanesDao;
import com.scavengers.plane.system.dao.UsersDao;
import com.scavengers.plane.system.entity.Plane;
import com.scavengers.plane.system.entity.Users;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 机票查询结果界面
 */

public class SearchResultWindow extends JFrame implements ActionListener {
    JButton btn_buy, btn_select_time;
    JTable table_plane;
    Object[] cols_plane = {"航班号", "航空公司", "机型", "出发城市", "目的城市", "里程", "日期", "出发时间", "头等舱", "商务舱", "经济舱"};
    Object[][] rows_plane;
    JComboBox<String> com_seatclass;
    ArrayList<Plane> planeList;
    String flightID, departureTime, starttime, usertel, s, fromCity, toCity, departure, departureDate;
    int member;
    boolean isSelect = false;

    public SearchResultWindow(String s, String startplace, String endplace, String departure, int people, String tel, String departureDate) {
        setTitle(s);
        usertel = tel;
        fromCity = startplace;
        toCity = endplace;
        departureTime = departureDate;
        //设置图标
        ImageIcon icon = new ImageIcon("picture/logo1.png");
        setIconImage(icon.getImage());

        btn_buy = new JButton("购买");
        btn_select_time = new JButton("按时间排序");
        btn_select_time.addActionListener(this);
        btn_buy.addActionListener(this);
        JPanel jp_btn = new JPanel();
        jp_btn.add(btn_buy);
        jp_btn.add(btn_select_time);
        member = people;

        //生成筛选的列车的表格
        PlanesDao planesDao = new PlanesDao();
        planeList = planesDao.planeTicketsQuery(startplace, endplace, departureDate, people, isSelect);
        rows_plane = new Object[planeList.size()][cols_plane.length];
        int j = 0;
        while (j < planeList.size()) {
            rows_plane[j][0] = planeList.get(j).getFlightID();
            rows_plane[j][1] = planeList.get(j).getAirCompany();
            rows_plane[j][2] = planeList.get(j).getModel();
            rows_plane[j][3] = planeList.get(j).getStartPlace();
            rows_plane[j][4] = planeList.get(j).getEndplace();
            rows_plane[j][5] = planeList.get(j).getMile();
            rows_plane[j][6] = planeList.get(j).getStratDate();
            rows_plane[j][7] = planeList.get(j).getStarttime();
            rows_plane[j][8] = planeList.get(j).getHigh();
            rows_plane[j][9] = planeList.get(j).getMiddle();
            rows_plane[j][10] = planeList.get(j).getLow();
            j++;
        }
        table_plane = new JTable(rows_plane, cols_plane);
        //为表格添加监视器
        table_plane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table_plane.getSelectedRow();
                Object[] value = new Object[cols_plane.length];
                for (int i = 0; i < cols_plane.length; i++) {
                    value[i] = table_plane.getModel().getValueAt(row, i);
                }
                flightID = value[0].toString();
                departureTime = value[6].toString();
            }
        });

        JPanel jp_tableOfplane = new JPanel();
        jp_tableOfplane.setBorder(new TitledBorder("航班详情"));
        jp_tableOfplane.setLayout(new BorderLayout());
        jp_tableOfplane.add(new JScrollPane(table_plane), BorderLayout.CENTER);


        add(jp_tableOfplane, BorderLayout.CENTER);
        add(jp_btn, BorderLayout.SOUTH);

        setVisible(true);
        setSize(1400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        int num = 1;  //统计人数
        if (e.getSource() == btn_buy) {
            isSelect = false;
            if (flightID == null) {
                JOptionPane.showMessageDialog(this, "请先选择要购买的票！", "提醒", JOptionPane.WARNING_MESSAGE);
            } else if (haveBought() && member <= 1) {
                JOptionPane.showMessageDialog(this, "您已买过该日期该车次的票！", "提醒", JOptionPane.WARNING_MESSAGE);
            } else {
                for (; num <= member; num++) {
                    //登记好信息之后进行选座处理
                    Object[] obj2 = {"头等舱", "商务舱", "经济舱"};
                    Object[] obj3 = {"A(靠窗)", "B(中间)", "C(过道)", "D(过道)", "F(靠窗)"};
                    Object[] obj4 = {"男", "女"};
                    String identityID = JOptionPane.showInputDialog(null, "输入乘客的身份证号:");
                    String name = JOptionPane.showInputDialog(null, "输入乘客的姓名:");
                    String gender = (String) JOptionPane.showInputDialog
                            (this, "请选择性别:\n", "性别", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), obj4, "男");
                    String pwd = JOptionPane.showInputDialog(null, "输入乘客的账号密码:");
                    String tel = JOptionPane.showInputDialog(null, "输入乘客的电话:");
                    String birthday = JOptionPane.showInputDialog(null, "输入乘客的出生日期:");
                    String grade = (String) JOptionPane.showInputDialog
                            (this, "请选择座位类型:\n", "座位类型", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), obj2, "商务座");
                    String seat = (String) JOptionPane.showInputDialog
                            (this, "请选择座位:\n", "座位", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""), obj3, "A");
                    //先查看此乘客是否存在
                    UsersDao usersDao = new UsersDao();
                    Users user = new Users();
                    if (usersDao.userExisted(tel)) {
                        user = usersDao.userQueryByTel(tel);
                        String userid = user.getUserid();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                        int line = (int) (Math.random() * 200 + 1);
                        int gate = (int) (Math.random() * 50 + 1);
                        if (!Objects.equals(grade, "")) {
                            OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
                            int k = orderSheetsDao.addOrderSheet(userid, flightID, grade, seat, departureTime, line, gate);
                            JOptionPane.showMessageDialog(this, df.format(new Date()) + "\n 购买 航班号：" + flightID + "," + grade + +line + "-" + seat + " 成功！", "提醒", JOptionPane.DEFAULT_OPTION);
                        }
                        //账户不存在
                    } else {
                        if (usersDao.register(tel, pwd, name, gender, birthday, identityID)) {
                            JOptionPane.showMessageDialog(null, "乘客信息注册成功!");
                            user = usersDao.userQueryByTel(tel);
                            String userid = user.getUserid();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                            int line = (int) (Math.random() * 200 + 1);
                            int gate = (int) (Math.random() * 50 + 1);
                            if (!Objects.equals(grade, "")) {
                                OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
                                int k = orderSheetsDao.addOrderSheet(userid, flightID, grade, seat, departureTime, line, gate);
                                JOptionPane.showMessageDialog(this, df.format(new Date()) + "\n 购买 航班号：" + flightID + "," + grade + +line + "-" + seat + " 成功！", "提醒", JOptionPane.DEFAULT_OPTION);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "乘客信息注册失败!");
                        }
                    }
                }
            }
        }
        //进行排序
        if (e.getSource() == btn_select_time) {
            isSelect = true;
            PlanesDao planesDao = new PlanesDao();
            ArrayList<Plane> planesList = planesDao.planeTicketsQuery(fromCity, toCity, departureTime, member, isSelect);
            rows_plane = new Object[planesList.size()][cols_plane.length];
            int j = 0;
            for (Plane plane : planesList) {
                rows_plane[j][0] = planesList.get(j).getFlightID();
                rows_plane[j][1] = planesList.get(j).getAirCompany();
                rows_plane[j][2] = planesList.get(j).getModel();
                rows_plane[j][3] = planesList.get(j).getStartPlace();
                rows_plane[j][4] = planesList.get(j).getEndplace();
                rows_plane[j][5] = planesList.get(j).getMile();
                rows_plane[j][6] = planesList.get(j).getStratDate();
                rows_plane[j][7] = planesList.get(j).getStarttime();
                rows_plane[j][8] = planesList.get(j).getHigh();
                rows_plane[j][9] = planesList.get(j).getMiddle();
                rows_plane[j][10] = planesList.get(j).getLow();
                j++;
            }
            table_plane.setModel(new DefaultTableModel(rows_plane, cols_plane));
        }
    }

    public boolean haveBought() {
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        UsersDao usersDao = new UsersDao();
        Users auser = new Users();
        auser = usersDao.userQueryByTel(usertel);
        String userid = auser.getUserid();
        return orderSheetsDao.haveBought(userid, flightID, starttime);
    }

}

