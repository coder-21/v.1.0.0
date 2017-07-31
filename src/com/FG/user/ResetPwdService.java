package com.FG.user;

import com.FG.utils.PasswordResetUtil;
import com.FG.utils.AESencription.AESencrp;


/**
 * Reset Password services class
 * Author : Suvarna
 * Created Date : 03-11-2015
 */
public class ResetPwdService {

	public boolean authenticate(UserRegistrationInfo user, String currPwd) {
		if(user!=null && user.getUserDecrypted_password().equals(currPwd))
			 return true;
		
		 else
			 return false;
	}

	public boolean changePassword(UserRegistrationInfo user, String newPwd) {
		return new PasswordResetUtil().updateDBwithNewPwd(user, newPwd);
		
	}
	 public boolean checkWithPrevPwds(String user_password, String user_prevpassword, String newpassword) {
		   String newpasswordEncrypted = "";
		   try {
			   newpasswordEncrypted = AESencrp.encrypt(newpassword);
			 } catch (Exception e) {
				e.printStackTrace();
			 }
		   if(user_prevpassword==null && user_password.matches(newpasswordEncrypted))
			return false;
		   else if(user_password.equals(newpasswordEncrypted) || newpasswordEncrypted.equals(user_prevpassword))
			return false;
		   else return true;
		}

}
