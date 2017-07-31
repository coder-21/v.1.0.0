package com.FG.Subscription;

/*Author : Diana P Lazar
Date created : 06/20/2016
Copyright@ FindGoose
*/

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="fg_subscribe")
public class SubscriptionInfo {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int subscribeid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="comment")
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "subscribedDate", nullable = false)
	private Date subscribed_Date;
	
	public SubscriptionInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public SubscriptionInfo(String name, String email, String comment, Date subscribedDate) {
		this.name = name;
		this.email = email;
		this.comment = comment;
		this.subscribed_Date = subscribedDate;
	}

	public int getSubscribeid() {
		return subscribeid;
	}

	public void setSubscribeid(int subscribeid) {
		this.subscribeid = subscribeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getSubscribed_Date() {
		return subscribed_Date;
	}

	public void setSubscribed_Date(Date subscribed_Date) {
		this.subscribed_Date = subscribed_Date;
	}
	


}
