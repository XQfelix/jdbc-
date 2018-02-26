package com.neu.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.neu.util.JdbcUtil;

public class StudentDao {
	//查询操作
	public List<Student> getStudent(){
		return JdbcUtil.executeQuery("select id,name,b_id,gerden from student", new RowMap<Student>() {

			@Override
			public Student rowMapping(ResultSet rs) {
				// TODO Auto-generated method stub
				Student student = new Student();
				//设定参数值
				try {
					student.setId(rs.getInt("id"));
					student.setName(rs.getString("name"));
					student.setBId(rs.getInt("b_id"));
					student.setGerden(rs.getInt("gerden"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return student;
			}
			
		}, null);
	}
	
	
	//插入操作
	public int add(Student student){
		int result = 0;
		result = JdbcUtil.executeUpdate("insert into student (name,b_id,gerden) values (?,?,?)", student.getName(),
				student.getBId(),student.getGerden());
		return result;
	}
	
	//修改操作
	public int update(Student student){
		int result = 0;
		result = JdbcUtil.executeUpdate("update student set name=?,b_id=?,gerden=? where id=?", student.getName(),
				student.getBId(),student.getGerden(),student.getId());
		return result;
	}
	
	//删除操作
	public int del(int id){
		int result = 0;
		result = JdbcUtil.executeUpdate("delete from student where id=?", id);
		return result;
	}
	
}
