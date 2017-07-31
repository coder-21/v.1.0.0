/*Author : Diana P Lazar
Date created : 01/12/2016
Copyright@ FindGoose
*/
package com.FG.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_listtable")
public class UserList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserList() {
		// TODO Auto-generated constructor stub
	}

	public UserList(String listid, String companydocId, String useremail,String sharedWithMembers, String sharedListComment, String userListName) {
		// TODO Auto-generated constructor stub
		list_id = listid;
		company_docId = companydocId;
		user_email = useremail;
		list_sharedWith = sharedWithMembers;
		sharedList_Comment = sharedListComment;
		list_name = userListName;
	}

	public String getList_id() {
		return list_id;
	}
	public String getList_sharedWith() {
		return list_sharedWith;
	}

	public void setList_sharedWith(String list_sharedWith) {
		this.list_sharedWith = list_sharedWith;
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	public String getCompany_docId() {
		return company_docId;
	}
	public void setCompany_docId(String company_docId) {
		this.company_docId = company_docId;
	}

	@Id 
    @Column (name="list_id", nullable=false)
	private String list_id;
	private String company_docId; // a string with names of all companies in teh list seperated by semi-colon ";"
	private String list_sharedWith;
	private String sharedList_Comment;
	private String list_name;
	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public String getSharedList_Comment() {
		return sharedList_Comment;
	}

	public void setSharedList_Comment(String sharedListComment) {
		this.sharedList_Comment = sharedListComment;
	}

	private String user_email;
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
}
