package com.ams.AttendanceManagementSystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="teacher")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="branch")
	private String branch;
	
	@Column(name="experience")
	private float experience;
	
	@Column(name="teacher_persist")
	private boolean persist;
	
	@ManyToOne()
	@JoinColumn(name="register")
	private Register user;
	
	@JsonIgnore
	@OneToOne(mappedBy="teacher")
	private Classes classes;
	
	
	public Teacher(long id,String name,String gender,String branch,float experience,boolean persist,Register user) {
		this.id=id; 
		this.name=name;
		this.gender=gender;
		this.branch=branch;
		this.experience=experience;
		this.persist=persist;
		this.user=user;
	}
	
	public Teacher() {
		super();
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public float getExperience() {
		return experience;
	}
	

	public void setExperience(float experience) {
		this.experience = experience;
	}
	

	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		this.persist = persist;
	}

	public Register getUser() {
		return user;
	}

	public void setUser(Register user) {
		this.user = user;
	}
	
	

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", gender=" + gender + ", branch=" + branch + ", experience="
				+ experience + ", user=" + user + "]";
	}
	
	
	
}
