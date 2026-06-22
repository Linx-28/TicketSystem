package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接工具类（基于原生 JDBC）
 * 读取 db.properties 获取连接信息
 */
public class DBUtil {

    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("Cannot find db.properties in classpath");
            }
            Properties props = new Properties();
            props.load(is);
            is.close();

            String driverClassName = props.getProperty("driverClassName");
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");

            // 加载 JDBC 驱动
            Class.forName(driverClassName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DBUtil", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
