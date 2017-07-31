/*Author : Diana P Lazar
Date created : Nov/2015
Copyright@ FindGoose
 */

package com.FG.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.FG.utils.AESencription.AESencrp;

@Entity
@Table(name="fg_usertable")
public class UserRegistrationInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@Id @GeneratedValue(strategy=GenerationType.AUTO)
//	private long user_id;
	private String user_firstname;
	private String user_lastname;
	
	@Id
	private String user_email;
	private String user_password;
	private String user_company;
	private String user_emailtype;
	private String user_industries;
	private String user_prevpassword;
	private String user_type;
	private String user_NewsletterDecision = "yes";
	private String user_CookieToken="";

	public UserRegistrationInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUser_CookieToken() {
		return user_CookieToken;
	}

	public void setUser_CookieToken(String user_CookieToken) {
		this.user_CookieToken = user_CookieToken;
	}

	public String getUser_type() {
		return user_type;
	}

	public String getUser_NewsletterDecision() {
		return user_NewsletterDecision;
	}

	public void setUser_NewsletterDecision(String user_NewsletterDecision) {
		this.user_NewsletterDecision = user_NewsletterDecision;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date", nullable = false)
	private Date register_date;
	
	
	
	public String getUser_industries() {
		return user_industries;
	}

	public void setUser_industries(String user_industries) {
		this.user_industries = user_industries;
	}

	
	public String getUser_prevpassword() {
		return user_prevpassword;
	}

	public void setUser_prevpassword(String user_prevpassword) {
		this.user_prevpassword = user_prevpassword;
	}

	
	public Date getregister_date() {
		return register_date;
	}

	public void setregister_date(Date register_date) {
		this.register_date = register_date;
	}
	
	public UserRegistrationInfo(String fName, String lName, String email, 
			String password, String company, Date register_date, String usertype, String newsletterDecision, String userCookieToken, String user_industries) {
		this.user_firstname = fName;
		this.user_lastname = lName;
		this.user_email = email;
		if(user_email.contains("@gmail"))
		{
			setUser_RegisteredEmailType( "personal");
		}
		else setUser_RegisteredEmailType( "official");
		
		this.user_password = password;
		this.user_company = company;
		this.register_date = register_date;
		this.user_type = usertype;
		this.user_NewsletterDecision = newsletterDecision;
		this.user_CookieToken = userCookieToken;
		this.user_industries = user_industries;
	}
	public String getUser_RegisteredEmailType() {
		return user_emailtype;
	}
	public void setUser_RegisteredEmailType(String user_emailtype) {
		this.user_emailtype = user_emailtype;
	}
	public String getUser_company() {
		return user_company;
	}
	public void setUser_company(String user_company) {
		this.user_company = user_company;
	}
//	public long getUser_id() {
//		return user_id;
//	}
//	public void setUser_id(long user_id) {//not required since auto set with hibernate annotations
//		this.user_id = user_id;
//	}
	public String getUser_firstname() {
		return user_firstname;
	}
	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}
	public String getUser_lastname() {
		return user_lastname;
	}
	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUserDecrypted_password() {
		String decryptedPassword="";
		try {
		decryptedPassword = AESencrp.decrypt(getUser_password());
//		System.out.println("decryptedPassword : " + decryptedPassword);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		return decryptedPassword;
	}

	private static UserRegistrationInfo currentuser = null;
	public static void setCurrentUserFromSession(UserRegistrationInfo user){
		currentuser = user;
	}
	public static UserRegistrationInfo getCurrentuser() {
		return currentuser;
	}
}
