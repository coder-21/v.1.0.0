/*
 * @author Suvarna
 * Date created :December 6th 2016
 * Copyright@ Findgoose
 */

package com.FG.user;

public class ImportContact {

	public ImportContact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImportContact(String contact_name, String contact_email, String contact_number) {
		super();
		this.contact_name = contact_name;
		this.contact_email = contact_email;
		this.contact_number = contact_number;
	}
	
	private String contact_name;
	private String contact_email;
	private String contact_number;
	
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
