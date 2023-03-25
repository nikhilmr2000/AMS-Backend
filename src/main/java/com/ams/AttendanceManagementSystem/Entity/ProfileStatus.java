package com.ams.AttendanceManagementSystem.Entity;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity() 
@Table(name="profile_status")
public class ProfileStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="roll_no")
	private String roll_no;
	
	@Column(name="standard")
	private String standard;
	
	@Column(name="section")
	private String section;
	
	@Column(name="persist")
	private boolean persist;
	
	@JoinColumn(name="register")
	@ManyToOne()
	private Register register;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name="profile_class")
	private Classes classes;
	
	
	public ProfileStatus(){
		super();
	}
	
	public ProfileStatus(String name,String gender,String roll_no,String standard,String section,boolean persist,Register register){
		this.name=name;
		this.gender=gender;
		this.roll_no=roll_no;
		this.standard=standard;
		this.section=section;
		this.persist=persist;
		this.register=register;
		
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

	public String getRoll_no() {
		return roll_no;
	}

	public void setRoll_no(String roll_no) {
		this.roll_no = roll_no;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		this.persist = persist;
	}
	
	
	

//	public Image getImage() {
//		return image;
//	}
//
//	public void setImage(Image image) {
//		this.image = image;
//	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	@Override
	public String toString() {
		return "ProfileStatus [id=" + id + ", name=" + name + ", persist=" + persist + "]";
	}
	
	
}
