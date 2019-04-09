package com.student.rating.context;

import com.student.rating.entity.Student;

public class StudentContextHolder {

	private Student threadLocalScope = new Student();

	public Student getStudent(){
		return this.threadLocalScope;
	}

	public void setStudent(Student student){
		this.threadLocalScope = student;
	}

	public void destroy(){
		this.threadLocalScope = null;
	}
}
