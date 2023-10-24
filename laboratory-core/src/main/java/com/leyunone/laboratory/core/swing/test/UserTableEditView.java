package com.leyunone.laboratory.core.swing.test;

import com.leyunone.laboratory.core.swing.bean.UserVO;
import com.leyunone.laboratory.core.swing.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/10/24
 */
public class UserTableEditView extends JDialog   {

    private JLabel nameLabel = new JLabel("姓名:");

    private JLabel sexLabel = new JLabel("性别:");

    private JLabel ageLabel = new JLabel("年龄:");

    private JLabel phoneLabel = new JLabel("号码:");

    public UserTableEditView(UserModel userModel, UserVO userVO,HomeView homeView) {
        this.setSize(500,500);
        this.setTitle("人员编辑");
        this.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel();
        formPanel.setSize(300,300);

        nameLabel.setSize(40,25);

        JTextField nameText = new JTextField(20);
        nameText.setSize(150,25);

        sexLabel.setSize(40,25);

        JTextField sexText = new JTextField(20);
        sexText.setSize(150,25);

        ageLabel.setSize(40,25);

        JTextField ageText = new JTextField(20);
        ageText.setSize(150,25);

        phoneLabel.setSize(40,25);

        JTextField phoneText = new JTextField(20);
        phoneText.setSize(150,25);

        formPanel.add(nameLabel);
        formPanel.add(nameText);
        formPanel.add(sexLabel);
        formPanel.add(sexText);
        formPanel.add(ageLabel);
        formPanel.add(ageText);
        formPanel.add(phoneLabel);
        formPanel.add(phoneText);
        this.add(formPanel, BorderLayout.CENTER);

        if(userVO==null) {
            userVO = new UserVO();
        }else {
            nameText.setText(userVO.getName());
            sexText.setText(userVO.getSex());
            ageText.setText(userVO.getAge());
            phoneText.setText(userVO.getPhone());
        }

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("登录");
        saveButton.setBounds(100, 90, 80, 25);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UserVO finalUserVO = userVO;
        saveButton.addActionListener(e -> {
            finalUserVO.setAge(ageText.getText());
            finalUserVO.setName(nameText.getText());
            finalUserVO.setPhone(phoneText.getText());
            finalUserVO.setSex(sexText.getText());
            if(StringUtils.isBlank(finalUserVO.getId())){
                //新增
                userModel.addUser(finalUserVO);
                this.setVisible(false);
                homeView.reData();
            }else{
                //更新
                userModel.updateUser(finalUserVO);
                this.setVisible(false);
                homeView.reData();
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e->{
            this.setVisible(false);
        });
        saveButton.setBounds(100, 110, 80, 25);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.setVisible(true);
    }
}
