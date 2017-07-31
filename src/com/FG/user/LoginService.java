package com.FG.user;

import java.security.SecureRandom;

public class LoginService {
public boolean authenticate(UserRegistrationInfo user,String email, String password) {
	 if(user!=null && user.getUser_email().equals(email) && user.getUserDecrypted_password().equals(password)){
		 return true;
	 }else{
		 return false;
	 }
}

public boolean authenticateWithTempPwd(UserRegistrationInfo user, String email, String tempPassword) {	
	 if(user!=null && user.getUser_email().equals(email) && user.getUser_password().equals(getFinEncrypPwd(tempPassword))){
		 return true;
	 }else{
		 return false;
	 }
}

private String getFinEncrypPwd(String tempPassword) {
	String finEncrypPwd = tempPassword.substring(3);
	return finEncrypPwd;
}

public static String processNewSessionToken() {
	SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[20];
    random.nextBytes(bytes);
    String token = bytes.toString();
	
	return token;
}

}
