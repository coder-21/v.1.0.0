/*
 * @author Suvarna
 * Date created :December 6th 2016
 * Copyright@ Findgoose
 */
package com.FG.user;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_taskstable")
public class TasksTableInfo {

	public TasksTableInfo(){
		
	}
	
	public TasksTableInfo(int task_id, String task_name, String desc, String status, String assignto,String createdby,
			Date task_date ) {
		
		this.task_id = task_id;
		this.task_name = task_name;
		this.task_desc = desc;
		this.task_status = status;
		this.task_assignto = assignto;
		this.task_createdby=createdby;
		this.task_date = task_date;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getDesc() {
		return task_desc;
	}
	public void setDesc(String desc) {
		this.task_desc = desc;
	}
	public String getStatus() {
		return task_status;
	}
	public void setStatus(String status) {
		this.task_status = status;
	}
	public String getAssignto() {
		return task_assignto;
	}
	public void setAssignto(String assignto) {
		this.task_assignto = assignto;
	}
	public String getCreatedby() {
		return task_createdby;
	}
	public void setCreatedby(String createdby) {
		this.task_createdby = createdby;
	}
	public Date getTask_date() {
		return task_date;
	}
	public void setTask_date(Date task_date) {
		this.task_date = task_date;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int task_id;
	private String task_name;
	private String task_desc;
	private String task_status;
	private String task_assignto;
	private String task_createdby;
	private Date task_date;
}
