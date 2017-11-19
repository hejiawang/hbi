package com.wang.hbi.generate.jdbc;

import com.mysql.jdbc.StringUtils;
import com.wang.hbi.generate.beans.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库链接工具
 *
 * @Author HeJiawang
 * @Date 2017/11/19 21:22
 */
@Component
public class JdbcConnection {

    /**
     * url
     */
    @Value("${database.url}")
    private String url;

    /**
     * driver
     */
    @Value("${database.driver}")
    private String driver;

    /**
     * userName
     */
    @Value("${database.username}")
    private String userName;

    /**
     * password
     */
    @Value("${database.password}")
    private String password;

    /**
     * 创建数据库连接对象
     */
    private Connection connnection = null;

    /**
     * 创建PreparedStatement对象
     */
    private PreparedStatement preparedStatement = null;

    /**
     * 创建CallableStatement对象
     */
    private CallableStatement callableStatement = null;

    /**
     * 创建结果集对象
     */
    private ResultSet rs = null;

    /**
     * 获取数据库链接
     * @return 数据库链接
     */
    public Connection getConnection() {
        try {
            Class.forName(driver);
            connnection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return connnection;
    }

    /**
     * 根据表明查找元数据
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<Property> findMetaDatas(String tableName) throws SQLException {
        String sql = "select * from " + tableName;

        ResultSetMetaData rsmd;
        String primaryKeyColumn = null;

        Connection conn = getConnection();

        {
            rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
            if (rs.next()) {
                primaryKeyColumn = rs.getString(4);
            }
        }

        {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            rsmd = rs.getMetaData();
        }

        int colunmcount = rsmd.getColumnCount();
        List<Property> pList = new ArrayList<>();
        for (int i = 0; i < colunmcount; i++) {
            Property pro = new Property();
            String name = rsmd.getColumnName(i + 1);
            String type = rsmd.getColumnClassName(i + 1);
            String t = rsmd.getColumnTypeName(i + 1).toUpperCase();
            if (!StringUtils.isNullOrEmpty(primaryKeyColumn) && primaryKeyColumn.equals(name)) {
                pro.setIsId(true);
            }
            pro.setColumnName(name);

            String propertyName = name.toLowerCase();
            if (propertyName.indexOf("_") > 0) {
                String[] nameArr = propertyName.split("_");
                propertyName = "";
                for (int j = 0; j < nameArr.length; j++) {
                    String temp = nameArr[j];
                    if (j > 0) {
                        temp = nameArr[j].substring(0, 1).toUpperCase() + nameArr[j].substring(1);
                    }
                    propertyName += temp;
                }
            }
            pro.setPropertyName(propertyName);

            pro.setColumnType(type.substring(type.lastIndexOf(".") + 1));
            if(t.startsWith("INT")){
                pro.setType(t.startsWith("INT") ? "INTEGER" : t);
            } else if(t.startsWith("BOOL")){
                pro.setType(t.startsWith("BOOL") ? "BOOLEAN" : t);
            } else if(t.startsWith("TEXT")){
                pro.setType(t.startsWith("TEXT") ? "VARCHAR" : t);
            } else if(t.startsWith("MONEY")){
                pro.setType(t.startsWith("MONEY") ? "NUMERIC" : t);
            } else {
                pro.setType(t);
            }
            pList.add(pro);
        }

        closeAll();
        return pList;
    }

    /**
     * 关闭所有资源
     */
    private void closeAll() {
        this.closeRs();
        this.closePreparedStatement();
        this.closeCallableStatement();
        this.closeConnnection();
    }

    /**
     * 关闭结果集对象
     */
    private void closeRs(){
        if( null == rs ) return;

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 关闭PreparedStatement对象
     */
    private void closePreparedStatement(){
        if( null == preparedStatement ) return;

        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 关闭CallableStatement 对象
     */
    private void closeCallableStatement(){
        if( null == callableStatement ) return;

        try {
            callableStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 关闭Connection 对象
     */
    private void closeConnnection(){
        if( null == connnection ) return;

        try {
            connnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
