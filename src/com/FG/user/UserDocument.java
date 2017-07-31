/*Author : Diana P Lazar
Date created : 07/29/2016
Copyright@ FindGoose
*/
package com.FG.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.io.IOUtils;//com.amazonaws.util.IOUtils;

@Entity
@Table(name="fg_document")
public class UserDocument {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="doc_Id", nullable=false)
	private int userDocumentId;
	private String userDocumentOwneremail;
	private String sharedWithIds;
	private String userDocumentName;
	private String sharedUserDocument_Comment;
	
	@Column (name="userDocument", nullable=false)
	private byte[] userDocument;
	private String userDocumentType;
	public String getUserDocumentType() {
		return userDocumentType;
	}

	public void setUserDocumentType(String userDocumentType) {
		this.userDocumentType = userDocumentType;
	}

	public UserDocument() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDocument(String ownerEmail, String userDocumentName,Date userDocument_CreatedDate, InputStream inputStream, String documentType){
		this.userDocumentName = userDocumentName;
		this.userDocumentOwneremail = ownerEmail;
		this.sharedWithIds ="";
		this.sharedUserDocument_Comment = "";
		this.userDocumentType =documentType;
		try {
			this.userDocument = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUserDocumentName() {
		return userDocumentName;
	}
	public void setUserDocumentName(String userDocumentName) {
		this.userDocumentName = userDocumentName;
	}
	public int getUserDocumentId() {
		return userDocumentId;
	}
	public void setUserDocumentId(int userDocumentId) {
		this.userDocumentId = userDocumentId;
	}
	public String getUserDocumentOwneremail() {
		return userDocumentOwneremail;
	}
	public void setUserDocumentOwneremail(String userDocumentOwneremail) {
		this.userDocumentOwneremail = userDocumentOwneremail;
	}
	public String getSharedWithIds() {
		return sharedWithIds;
	}
	public void setSharedWithIds(String sharedWithIds) {
		this.sharedWithIds = sharedWithIds;
	}
//	public String getUserDocument_Name() {
//		return userDocumentName;
//	}
//	public void setUserDocument_Name(String userDocumentName) {
//		this.userDocumentName = userDocumentName;
//	}
	public byte[] getUserDocument() {
		return userDocument;
	}
	public void setUserDocument(byte[] userDocument) {
		this.userDocument = userDocument;
	}
	public String getSharedUserDocument_Comment() {
		return sharedUserDocument_Comment;
	}
	public void setSharedUserDocument_Comment(String sharedUserDocument_Comment) {
		this.sharedUserDocument_Comment = sharedUserDocument_Comment;
	}

}

