package com.scavengers.plane.system.view;

import com.scavengers.plane.system.dao.OrderSheetsDao;
import com.scavengers.plane.system.entity.TicketOrder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 订单界面
 */

public class OrderSheetWindow extends JFrame implements ActionListener {
    private JButton btn_endorse, btn_refund;
    private JTable orderSheetTable;
    private Object[] cols_orderSheet = {"订单编号", "姓名", "身份证号", "航班号",
            "起点", "终点", "出发时间", "座位号", "舱位", "登机口"};
    private Object[][] rows_orderSheet;
    //    private ArrayList<OrderSheets> orderSheets;
    private String userId, buyTime;
    private String ordersID, startPlace, endPlace;

    public OrderSheetWindow(String s, String userId) {
        setTitle(s);
        this.userId = userId;
        //设置图标
        ImageIcon icon = new ImageIcon("picture/logo1.png");
        setIconImage(icon.getImage());

        //功能按钮
        btn_refund = new JButton("退票");
        btn_refund.addActionListener(this);
        JPanel jp_btn = new JPanel();
        jp_btn.add(btn_refund);

        //初始化订单表格（更新）
        orderSheetTable = new JTable(rows_orderSheet, cols_orderSheet);
        orderSheetTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                getSelectedInfo();//获得鼠标选中的信息
            }
        });
        this.updateOrderSheetTable();//初始化获得订单所有信息

        JPanel jp_table = new JPanel();
        jp_table.setBorder(new TitledBorder("订单详情"));
        jp_table.setLayout(new BorderLayout());
        jp_table.add(new JScrollPane(orderSheetTable), BorderLayout.CENTER);


        this.add(jp_table, BorderLayout.CENTER);
        this.add(jp_btn, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(1800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        //退票事件
        if (e.getSource() == btn_refund) {
            if (ordersID == null) {
                JOptionPane.showMessageDialog(this, "请先选择要退的票", "提醒", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int r = JOptionPane.showConfirmDialog(this, "确认退票？", "提醒", JOptionPane.OK_CANCEL_OPTION);
            if (r == 0) {
                refundTicket();
                this.updateOrderSheetTable();//更新表格
            }
            return;
        }

    }

    //获得鼠标选中的信息
    private void getSelectedInfo() {
        int row = orderSheetTable.getSelectedRow();
        Object no = orderSheetTable.getModel().getValueAt(row, 0);
        Object type = orderSheetTable.getModel().getValueAt(row, 3);
        Object start = orderSheetTable.getModel().getValueAt(row, 4);
        Object end = orderSheetTable.getModel().getValueAt(row, 5);
        ordersID = (String) no;
        startPlace = (String) start;
        endPlace = (String) end;
        String tmp = (String) type;
    }

    //更新订单表格
    private void updateOrderSheetTable() {
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        ArrayList<TicketOrder> orderSheets = orderSheetsDao.orderSheetsQueryById(userId);
        rows_orderSheet = new Object[orderSheets.size()][cols_orderSheet.length];
        int k = 0;
        for (TicketOrder orderSheet : orderSheets) {
            rows_orderSheet[k][0] = orderSheet.getOrderID();
            rows_orderSheet[k][1] = orderSheet.getUsername();
            rows_orderSheet[k][2] = orderSheet.getUserid();
            rows_orderSheet[k][3] = orderSheet.getflightID();
            rows_orderSheet[k][4] = orderSheet.getStartplace();
            rows_orderSheet[k][5] = orderSheet.getEndplace();
            rows_orderSheet[k][6] = orderSheet.getStarttime();
            rows_orderSheet[k][7] = orderSheet.getSeatno();
            rows_orderSheet[k][8] = orderSheet.getGrade();
            rows_orderSheet[k][9] = orderSheet.getGate();
            k++;
        }
        orderSheetTable.setModel(new DefaultTableModel(rows_orderSheet, cols_orderSheet));
    }

    //退票
    private void refundTicket() {
        OrderSheetsDao orderSheetsDao = new OrderSheetsDao();
        int k = orderSheetsDao.deleteOrderSheet(ordersID);
        if (k == 1) {
            JOptionPane.showMessageDialog(this, "退票成功", "提醒", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "退票失败", "提醒", JOptionPane.PLAIN_MESSAGE);
        }
    }

}

