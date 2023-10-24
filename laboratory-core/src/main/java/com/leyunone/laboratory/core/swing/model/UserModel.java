package com.leyunone.laboratory.core.swing.model;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.laboratory.core.swing.bean.UserVO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/10/24
 */
public class UserModel extends AbstractTableModel {

    private final String [] columns = new String[] {"编号","姓名","性别","年龄","电话"};
    private List<UserVO> values;
    public UserModel(){
        this.loadData();
    }

    private void loadData(){
        this.values = CollectionUtil.newArrayList(new UserVO("1","m","男","23","111"),new UserVO(
                "2","4","男","23","111"),new UserVO("3","mdddd","男","23","111")
                ,new UserVO("4","dfm","男","23","111"),new UserVO("5","vvvm","男","23","111"));
    }

    @Override
    public int getRowCount() {
        return this.values.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    public UserVO getUser(Integer index){
        return values.get(index);
    }

    public void addUser(UserVO userVO){
        this.values.add(userVO);
    }

    public void updateUser(UserVO userVO){
        for (UserVO value : values) {
            if(userVO.getId().equals(value.getId())){
                value = userVO;
            }
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserVO userVO = this.values.get(rowIndex);
        if(columnIndex==0){
            return userVO.getId();
        }else if(columnIndex == 1){
            return userVO.getName();
        }else if(columnIndex == 2){
            return userVO.getSex();
        }else if(columnIndex == 3){
            return userVO.getAge();
        }else if(columnIndex == 4){
            return userVO.getPhone();
        }
        return null;
    }
}
