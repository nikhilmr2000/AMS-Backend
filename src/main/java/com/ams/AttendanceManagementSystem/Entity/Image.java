package com.ams.AttendanceManagementSystem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="image")
public class Image {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Lob
	@Column(name="imageurl")
	private byte[] imageurl;
	
	@Column(name="profileid")
	private long profileid;
							
	
	public Image() {
		super();
	}
	
	public Image(String name,byte[] imageurl,long profileid) {
		this.name=name;
		this.imageurl=imageurl;
		this.profileid=profileid;
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
	
						
	public long getProfileid() {
		return profileid;
	}

	public void setProfileid(long profileid) {
		this.profileid = profileid;
	}

	public byte[] getImageurl() {
		return imageurl;
	}

	public void setImageurl(byte[] imageurl) {
		this.imageurl = imageurl;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", image=" + imageurl + "]";
	}
	
	

}
