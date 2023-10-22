package com.leyunone.laboratory.core.swing.test;

import com.leyunone.laboratory.core.swing.model.UserModel;

import javax.swing.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/10/23
 */
public class HomeView extends JFrame {

    //搜索
    private JTextField searchText = new JTextField(20);

    public HomeView(){

        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        JPanel searchForm = new JPanel();
        this.add(searchForm);
        JLabel searchLabel = new JLabel("姓名/编号/电话号码：");
        searchLabel.setBounds(40,20,60,25);
        searchForm.add(searchLabel);
        searchText.setBounds(40,90,120,25);
        searchForm.add(searchText);

        //表格
        JTable table = new JTable(new UserModel());
        table.setBounds(20,80,300,300);
        this.add(table);
        this.setVisible(true);
    }
}
