package com.neu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neu.bean.RowMap;

public class JdbcUtil {
	
	static final String driver = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf8&useSSL=false";
	static final String user = "root";
	static final String password = "123456";

	
	//构建连接
	public static Connection getConnection(){
		Connection con = null;		
		try {
			//加载驱动
			Class.forName(driver);
			//建立连接
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return con;		
	}
	
	//关闭资源流
	public static void close(Connection con,PreparedStatement pstmt,ResultSet rs){
		
		try {
			//添加判断如果参数流参数不为空,则关闭
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//增加/删除/修改封装
	public static int executeUpdate(String sql,Object...params){
		int result = 0;
		//建立连接
		Connection con = getConnection();
		//创建命令窗口,输入SQL语句
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			//判断是否传入参数,如果参数不为空的话,使用循环注入参数
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);	
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(con, pstmt, null);
		}
		
		
		return result;
	}
	
	//查询封装,定义一个泛型方法
	public static<T> List<T> executeQuery(String sql,RowMap<T> rowmap,Object...params){
		//创建一个集合接收
		List<T> list = new ArrayList<T>();
		//建立连接
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//传入参数
			pstmt = con.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			//读出
			while(rs.next()){
				//通过RowMap接口创建一个t 来接收读取的值
				T t = rowmap.rowMapping(rs);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(con, pstmt, rs);
		}
		
		
		return list;
	}
	
	
}
