package cn.itcast.heimatravel.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource dataSource;
    static {
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties pro=new Properties();
        try {
            pro.load(is);
           dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static DataSource getDataSource(){
        return dataSource;
    }

}
