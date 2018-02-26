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

	
	//��������
	public static Connection getConnection(){
		Connection con = null;		
		try {
			//��������
			Class.forName(driver);
			//��������
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
	
	//�ر���Դ��
	public static void close(Connection con,PreparedStatement pstmt,ResultSet rs){
		
		try {
			//����ж����������������Ϊ��,��ر�
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
	
	
	
	//����/ɾ��/�޸ķ�װ
	public static int executeUpdate(String sql,Object...params){
		int result = 0;
		//��������
		Connection con = getConnection();
		//���������,����SQL���
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			//�ж��Ƿ������,���������Ϊ�յĻ�,ʹ��ѭ��ע�����
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
	
	//��ѯ��װ,����һ�����ͷ���
	public static<T> List<T> executeQuery(String sql,RowMap<T> rowmap,Object...params){
		//����һ�����Ͻ���
		List<T> list = new ArrayList<T>();
		//��������
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//�������
			pstmt = con.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			//����
			while(rs.next()){
				//ͨ��RowMap�ӿڴ���һ��t �����ն�ȡ��ֵ
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
