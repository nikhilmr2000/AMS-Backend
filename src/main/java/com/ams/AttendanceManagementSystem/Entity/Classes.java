package com.ams.AttendanceManagementSystem.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="class")
public class Classes {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="classname")
	private String classname;
	
	@Column(name="section")
	private String section;
	
	@Column(name="teacher_code")
	private String teachercode;
	
	@Column(name="allocation")
	private String allocation;
	
	@ManyToOne()
	@JoinColumn(name="university")
	private Register university;
	
	@OneToOne()
	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	
	@OneToMany(mappedBy="classes")
	private List<ProfileStatus> profile=new ArrayList<>();
	
	public Classes() {
		super();
	}
	
	public Classes(long id,String classname,String section,String teachercode,String allocation,Teacher teacher) {
		this.id=id;
		this.classname=classname;
		this.section=section;
		this.teachercode=teachercode;
		this.allocation=allocation;
		this.teacher=teacher;
	}
	
	//Getters and Setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getTeachercode() {
		return teachercode;
	}

	public void setTeachercode(String teachercode) {
		this.teachercode = teachercode;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public List<ProfileStatus> getProfile() {
		return profile;
	}

	public void setProfile(List<ProfileStatus> profile) {
		this.profile = profile;
	}
	
	

	public Register getUniversity() {
		return university;
	}

	public void setUniversity(Register university) {
		this.university = university;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", classname=" + classname + ", section=" + section + ", teacher=" + teacher + "]";
	}
	
	
	
	
}
