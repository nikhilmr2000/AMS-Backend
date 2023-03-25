package com.ams.AttendanceManagementSystem.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="AMS_Register")
public class Register {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="organization_name")
	private String orgName;
	
	@Column(name="username")
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name="secret_code")
	private String secretcode;
	
	@Column(name="access_code")
	private String accesscode;
	
	@JsonIgnore
	@OneToMany(mappedBy="university")
	private List<Classes> classes=new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="register")
	private List<ProfileStatus> profile=new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Teacher> teacher=new ArrayList<>();
	
	public Register() {
		super();
	}
	
	public Register(String orgName,String username,String secretcode,String accesscode) {
		this.orgName=orgName;
		this.username=username;
		this.secretcode=secretcode;
		this.accesscode=accesscode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecretcode() {
		return secretcode;
	}

	public void setSecretcode(String secretcode) {
		this.secretcode = secretcode;
	}

	public String getAccesscode() {
		return accesscode;
	}

	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}
	

	public List<ProfileStatus> getProfile() {
		return profile;
	}

	public void setProfile(List<ProfileStatus> profile) {
		this.profile = profile;
	}
	

	public List<Classes> getClasses() {
		return classes;
	}

	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}

	public List<Teacher> getTeacher() {
		return teacher;
	}

	public void setTeacher(List<Teacher> teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Register [id=" + id + ", orgName=" + orgName + ", username=" + username + ", secretcode=" + secretcode
				+ ", accesscode=" + accesscode + "]";
	}
	
	
	
}
