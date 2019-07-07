package com.CouponSystem.springboot.myCouponSystem.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "images")
public class Image implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private long imageID;

	@Column(name = "image_name", nullable = false)
	private String name;

	@Column(name = "type", nullable = false)
	private String type;

	@Lob
	@Column(name = "image")
	private byte[] image;

	public Image() {
	}

	public Image(String name, String type, byte[] image) {
		this.name = name;
		this.type = type;
		this.image = image;
	}

	public long getImageID() {
		return this.imageID;
	}

	public void setImageID(long imageID) {
		this.imageID = imageID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
