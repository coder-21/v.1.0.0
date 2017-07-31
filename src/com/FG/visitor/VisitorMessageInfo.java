/**
 * Author:Suvarna
 * Date:Oct 3 2016
 */
package com.FG.visitor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_visitormessage")
public class VisitorMessageInfo {

	public VisitorMessageInfo(int message_id, String visitor_name, String visitor_email, String visitor_message) {
		super();
		this.message_id = message_id;
		this.visitor_name = visitor_name;
		this.visitor_email = visitor_email;
		this.visitor_message = visitor_message;
	}
	public VisitorMessageInfo() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int message_id;
	private String visitor_name;
	private String visitor_email;
	private String visitor_message;
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public String getVisitor_name() {
		return visitor_name;
	}
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}
	public String getVisitor_email() {
		return visitor_email;
	}
	public void setVisitor_email(String visitor_email) {
		this.visitor_email = visitor_email;
	}
	public String getVisitor_message() {
		return visitor_message;
	}
	public void setVisitor_message(String visitor_message) {
		this.visitor_message = visitor_message;
	}
	
}
