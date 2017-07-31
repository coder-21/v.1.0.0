/*
 * @author Suvarna
 * Copyright@ Findgoose
 */
package com.FG.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_contactstable")
public class UserContactsInfo {

	public UserContactsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserContactsInfo(long contact_id,String user_email, String contact_name, String contact_email, String contact_number) {
		super();
		this.contact_id=contact_id;
		this.user_email = user_email;
		this.contact_name = contact_name;
		this.contact_email = contact_email;
		this.contact_number = contact_number;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long contact_id;
	public long getContact_id() {
		return contact_id;
	}

	public void setContact_id(long contact_id) {
		this.contact_id = contact_id;
	}

	private String user_email;
	private String contact_name;
	private String contact_email;
	private String contact_number;
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	
}
