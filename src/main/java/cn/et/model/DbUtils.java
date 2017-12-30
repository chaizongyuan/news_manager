package cn.et.model;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * jdbc的封装类
 * @author Administrator
 *
 */
public class DbUtils {
    //读取资源文件
	static Properties p=new Properties();
	static{
		//当资源文件在src下时用 /jdbc.properties
		InputStream is=DbUtils.class.getResourceAsStream("/mysql.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取连接
	 */
	public static Connection getConnection(){
		String url=p.getProperty("url");
		String driverClass=p.getProperty("driverClass");
		String uname=p.getProperty("username");
		String password=p.getProperty("password");
		Connection con =null;
		try {
			Class.forName(driverClass);
			 con=DriverManager.getConnection(url, uname, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 查询方法
	 * @param sql
	 * @return
	 */
	public static List<Map> query(String sql){
		//获取连接
		Connection con=getConnection();
		PreparedStatement pst;
		List list=null;
		try {
			//预编译的sql语句
			pst = con.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			list=new ArrayList();
			//获取列名
			ResultSetMetaData rsd=rs.getMetaData();
			//获取列的个数
			int count=rsd.getColumnCount();
			while(rs.next()){
				Map map=new HashMap();
				for(int i=1;i<=count;i++){
					//列名
					String cName=rsd.getColumnLabel(i);
					//列值
					String cValue=rs.getString(i);
					map.put(cName, cValue);
				}
				//添加到集合中
				list.add(map);
			}
		rs.close();
		con.close();
		pst.close();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 增删改方法
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static int execute(String sql) throws SQLException{
			Connection conn=getConnection();
			PreparedStatement pst=conn.prepareStatement(sql);
			int i=pst.executeUpdate();
			pst.close();
			conn.close();
			return i;
	}
	
	
}
