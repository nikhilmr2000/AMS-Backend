package com.ams.AttendanceManagementSystem.Entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="teacherimage")
public class TeacherImage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Lob
	@Column(name="t_imgurl")
	private byte[] teacherurl;
	
	
	@Column(name="teacher_id")
	private long teacherid;
	
	public TeacherImage() {
		super();
	}
	
	public TeacherImage(long id,String name,byte[] teacherurl,long teacherid) {
		this.id=id;
		this.name=name;
		this.teacherurl=teacherurl;
		this.teacherid=teacherid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) { 
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getTeacherurl() {
		return teacherurl;
	}

	public void setTeacherurl(byte[] teacherurl) {
		this.teacherurl = teacherurl;
	}

	public long getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(long teacherid) {
		this.teacherid = teacherid;
	}

	@Override
	public String toString() {
		return "TeacherImage [id=" + id + ", name=" + name + ", teacherurl=" + Arrays.toString(teacherurl)
				+ ", teacherid=" + teacherid + "]";
	}
	
}
