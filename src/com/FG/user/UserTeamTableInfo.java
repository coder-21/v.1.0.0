package com.FG.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fg_userteamtable")
public class UserTeamTableInfo {

	
	public UserTeamTableInfo() {
		// TODO Auto-generated constructor stub
	}
	public UserTeamTableInfo(String user_email, String user_fullname, String team_members, String team_fullname,
			String requests_sent, String requests_received, int members_count) {
		
		this.user_email = user_email;
		this.user_fullname = user_fullname;
		this.team_members = team_members;
		this.team_fullname = team_fullname;
		this.requests_sent = requests_sent;
		this.requests_received = requests_received;
		this.members_count = members_count;
	}
	@Id
	private String user_email;
	private String user_fullname;
	private String team_members;
	private String team_fullname;
	private String requests_sent;
	private String requests_received;
	private int members_count;
	
	public String getuser_email() {
		return user_email;
	}
	public void setuser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_fullname() {
		return user_fullname;
	}
	public void setUser_fullname(String user_fullname) {
		this.user_fullname = user_fullname;
	}
	public String getTeam_members() {
		return team_members;
	}
	public void setTeam_members(String team_members) {
		this.team_members = team_members;
	}
	public String getTeam_fullname() {
		return team_fullname;
	}
	public void setTeam_fullname(String team_fullname) {
		this.team_fullname = team_fullname;
	}
	public String getRequests_sent() {
		return requests_sent;
	}
	public void setRequests_sent(String requests_sent) {
		this.requests_sent = requests_sent;
	}
	public String getRequests_received() {
		return requests_received;
	}
	public void setRequests_received(String requests_received) {
		this.requests_received = requests_received;
	}
	public int getMembers_count() {
		return members_count;
	}
	public void setMembers_count(int members_count) {
		this.members_count = members_count;
	}
	
}
