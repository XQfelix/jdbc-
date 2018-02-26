package com.neu.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.neu.util.JdbcUtil;

public class StudentDao {
	//��ѯ����
	public List<Student> getStudent(){
		return JdbcUtil.executeQuery("select id,name,b_id,gerden from student", new RowMap<Student>() {

			@Override
			public Student rowMapping(ResultSet rs) {
				// TODO Auto-generated method stub
				Student student = new Student();
				//�趨����ֵ
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
	
	
	//�������
	public int add(Student student){
		int result = 0;
		result = JdbcUtil.executeUpdate("insert into student (name,b_id,gerden) values (?,?,?)", student.getName(),
				student.getBId(),student.getGerden());
		return result;
	}
	
	//�޸Ĳ���
	public int update(Student student){
		int result = 0;
		result = JdbcUtil.executeUpdate("update student set name=?,b_id=?,gerden=? where id=?", student.getName(),
				student.getBId(),student.getGerden(),student.getId());
		return result;
	}
	
	//ɾ������
	public int del(int id){
		int result = 0;
		result = JdbcUtil.executeUpdate("delete from student where id=?", id);
		return result;
	}
	
}
