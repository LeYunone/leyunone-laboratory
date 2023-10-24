package com.leyunone.laboratory.core.swing.test;

import com.leyunone.laboratory.core.swing.bean.UserVO;
import com.leyunone.laboratory.core.swing.model.UserModel;

import javax.swing.*;
import java.awt.*;

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

    private final JTable table;

    public HomeView(){

        this.setSize(700,700);
        this.setLocationRelativeTo(null);
        JPanel searchForm = new JPanel();
        searchForm.setSize(500,200);
        JLabel searchLabel = new JLabel("姓名/编号/电话号码：");
        searchLabel.setBounds(40,20,60,25);
        searchForm.add(searchLabel);
        searchText.setBounds(40,90,20,25);
        searchForm.add(searchText);
        this.add(searchForm);

        //返回
        JButton ret = new JButton("返回");

        //提示
        JLabel dig = new JLabel("暂未选中数据");
        searchForm.add(dig);
        searchForm.add(ret);

        //表格
        UserModel userModel = new UserModel();
        table = new JTable(userModel);

        table.setSize(300,300);
        table.getSelectionModel().addListSelectionListener(
                e -> {
                    int row = table.getSelectedRow();
                    UserVO user = userModel.getUser(row);
                    dig.setText("当前选中的数据为： " + user.getName());
                });
        JScrollPane scrollPane = new JScrollPane(table);

        //操作按钮
        JPanel buttons = new JPanel();
        JButton addButton = new JButton("新增");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");
        addButton.addActionListener(e->{
            new UserTableEditView(userModel,null,this);
        });
        editButton.addActionListener(e->{
            new UserTableEditView(userModel,userModel.getUser(table.getSelectedRow()),this);
        });
        deleteButton.addActionListener(e->{

        });
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);

        this.add(buttons,BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(searchForm,BorderLayout.NORTH);
        this.setVisible(true);
    }

    public void reData(){
        table.updateUI();
    }
}
