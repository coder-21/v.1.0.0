/*Author : Diana P Lazar
Date created : 05/13/2016
Copyright@ FindGoose
*/
package com.FG.events;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_eventstable")
public class EventsEnrolled implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="eventId", nullable=false)
	private int eventId;
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getEventCreationDate() {
		return eventCreationDate;
	}
	public void setEventCreationDate(Date eventCreationDate) {
		this.eventCreationDate = eventCreationDate;
	}
	public String getEventURL() {
		return eventURL;
	}
	public void setEventURL(String eventURL) {
		this.eventURL = eventURL;
	}
	public String getEventCreatorEmail() {
		return eventCreatorEmail;
	}
	public void setEventCreatorEmail(String eventCreatorEmail) {
		this.eventCreatorEmail = eventCreatorEmail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column (name="eventName", nullable=false)
	private String eventName;
	private String eventLocation;
	private Date eventDate;
	private Date eventCreationDate;
	private String eventURL;
	private String eventCreatorEmail = "FindGoose";

	
	public EventsEnrolled() {
		// TODO Auto-generated constructor stub
	}
	
	public EventsEnrolled(String evntName,String evntLocation,Date eventDate, String evntURL,String evntCreator) {
		this.eventCreationDate = new Date();
		this.eventName = evntName;
		this.eventLocation = evntLocation;
		this.eventDate = eventDate;
		this.eventURL = evntURL;
		this.eventCreatorEmail = evntCreator;
	}
}
