package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class MyNews {
	/**
	 * 添加新闻
	 */
	public void inserNews(String title,String content,String newsPath,String createTime){
		String sql="insert into mynews(title,content,htmlpath,createtime) values('"+title+"','"+content+"','"+newsPath+"','"+createTime+"')";
	
		try {
			DbUtils.execute(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	/**
	 * 查询新闻
	 */
	public List<Map> queryNews() {
		String sql="select * from mynews";
		return DbUtils.query(sql);
	}
	/**
	 * 查询最后一次的id
	 */
	public String queryLastId() {
		String sql="SELECT LAST_INSERT_ID() as id";
		return DbUtils.query(sql).get(0).get("id").toString();
	}
}
